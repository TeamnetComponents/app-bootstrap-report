package ro.teamnet.bootstrap.reports.domain.resolve;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
import ro.teamnet.bootstrap.reports.domain.Reportable;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A handler to resolve {@code @RequestMapping} annotated methods' {@link org.springframework.core.MethodParameter}s
 * related to {@link ro.teamnet.bootstrap.reports.domain.Report} interface.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-04
 */
public class ReportableArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Minimum JSON content data size, in bytes.
     */
    private static final int JSON_BODY_CONTENT_SIZE = 32768; // Allocate 32KB for a request's JSON body content - this is waaaay more than enough

    /**
     * Expected charset encoding for JSON data.
     */
    private static final String JSON_BODY_CONTENT_EXPECTED_CHARSET = "UTF-8";

    private static final String JSON_REPORT_METADATA_DEFAULT_LOOKUP_KEY = "metadata";

    private static final String JSON_REPORT_FILTERS_DEFAULT_LOOKUP_KEY = "filters";

    private static final String JSON_REPORT_SORT_DEFAULT_LOOKUP_KEY = "sort";
    private static final String JSON_REPORT_SORT_ORDERS_DEFAULT_LOOKUP_KEY = "orders";
    private static final String JSON_REPORT_SORT_ORDER_DIRECTION_DEFAULT_LOOKUP_KEY = "direction";
    private static final String JSON_REPORT_SORT_ORDER_PROPERTY_DEFAULT_LOOKUP_KEY = "property";
    private static final String JSON_REPORT_SORT_ORDER_NULL_HANDLING_DEFAULT_LOOKUP_KEY = "nullHandling";

    /**
     * Sole constructor. Constructors a <em>resolver</em> which can resolve
     * {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}, {@link ro.teamnet.bootstrap.extend.Filters}
     * and {@link org.springframework.data.domain.Sort} in order to package them all under a
     * {@link ro.teamnet.bootstrap.reports.domain.Reportable} instance.
     */
    public ReportableArgumentResolver() {
    }

    /**
     * A method which tries to read an {@link java.io.InputStream}'s bytes into a {@link org.json.JSONObject}, using a
     * fixed size buffer and a default {@link java.nio.charset.Charset}. The buffer is static in order to prevent
     * continuous data sending, which would open possibilities for <em>denial-of-service</em> attacks.
     *
     * @param requestBodyAsInputStream The input stream containing data.
     * @return A {@code JSONObject} instance.
     * @throws JSONException If construction of the JSON Object from the given JSON failed.
     * @see #JSON_BODY_CONTENT_SIZE Buffer's default size
     * @see #JSON_BODY_CONTENT_EXPECTED_CHARSET Charset to be used
     */
    private static JSONObject createJsonObjectStaticBuffer(InputStream requestBodyAsInputStream) throws JSONException, IOException {
        // A holder
        JSONObject jsonObject = null;
        try (ReadableByteChannel channel = Channels.newChannel(requestBodyAsInputStream)) {
            ByteBuffer buffer = ByteBuffer.allocate(JSON_BODY_CONTENT_SIZE);
            channel.read(buffer);
            String jsonString = new String(buffer.array(), Charset.forName(JSON_BODY_CONTENT_EXPECTED_CHARSET));
            jsonObject = new JSONObject(jsonString);
        }

        return jsonObject;
    }

    /**
     * Method to test if a given {@link org.springframework.core.MethodParameter} can be resolved into an
     * {@link ro.teamnet.bootstrap.reports.domain.Report}, using this resolver.
     *
     * @param parameter The parameter to be resolved.
     * @return {@code true} if it can be resolved, {@code false} otherwise.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Reportable.class);
    }

    /**
     * Handles requests for {@link ro.teamnet.bootstrap.reports.domain.Reportable} (equivalent of a report's
     * domain entity). A {@code Reportable} must have {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata} and,
     * optionally, filtering and sorting options.
     * <p/>
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public Reportable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Report report = new Report();
        // A mapper to be used to easily instantiate POJOs, where possible
        ObjectMapper objectMapper = new ObjectMapper();
        ServletInputStream requestBodyAsInputStream =
                ((HttpServletRequest) webRequest.getNativeRequest()).getInputStream();
        JSONObject jsonContentObject;
        try {
            // We need a raw JSONObject for Filters and Sort
            jsonContentObject = createJsonObjectStaticBuffer(requestBodyAsInputStream);

            // Metadata
            String metadataAsJsonString = jsonContentObject.getString(JSON_REPORT_METADATA_DEFAULT_LOOKUP_KEY);
            ReportMetadata metadata = objectMapper.readValue(metadataAsJsonString, objectMapper.constructType(ReportMetadata.class));
            report.setMetadata(metadata);
        } catch (JsonParseException | JsonMappingException | JSONException e) {
            // FUTURE Log this exception
            return null; // Metadata is mandatory, therefore we return null
        }
        try { // Filters
            String filtersAsJsonString = jsonContentObject.getString(JSON_REPORT_FILTERS_DEFAULT_LOOKUP_KEY);
            Filters filters = objectMapper.readValue(filtersAsJsonString, objectMapper.constructType(Filters.class));
            report.setFilters(filters);
        } catch (JSONException | JsonParseException | JsonMappingException e) {
            // FUTURE Just log this
            // We "swallow" these since Filters JSON data might not be present
        }
        try { // Sort options
            JSONObject sortOptionsJSONObject = jsonContentObject.getJSONObject(JSON_REPORT_SORT_DEFAULT_LOOKUP_KEY);
            JSONArray sortOrdersArray = sortOptionsJSONObject.getJSONArray(JSON_REPORT_SORT_ORDERS_DEFAULT_LOOKUP_KEY);
            List<Sort.Order> sortOrders = new ArrayList<>();
            for (int i = 0; i < sortOrdersArray.length(); i++) {
                JSONObject sortOrderJsonObject = sortOrdersArray.getJSONObject(i);
                Sort.Order sortOrder = new Sort.Order(
                        // Direction
                        sortOrderJsonObject.getString(JSON_REPORT_SORT_ORDER_DIRECTION_DEFAULT_LOOKUP_KEY) != null ?
                                Sort.Direction.valueOf(sortOrderJsonObject.getString(JSON_REPORT_SORT_ORDER_DIRECTION_DEFAULT_LOOKUP_KEY).toUpperCase()) :
                                null,
                        // Property
                        sortOrderJsonObject.getString(JSON_REPORT_SORT_ORDER_PROPERTY_DEFAULT_LOOKUP_KEY),
                        // NullHandling
                        sortOrderJsonObject.getString(JSON_REPORT_SORT_ORDER_NULL_HANDLING_DEFAULT_LOOKUP_KEY) != null ?
                                Sort.NullHandling.valueOf(sortOrderJsonObject.getString(JSON_REPORT_SORT_ORDER_NULL_HANDLING_DEFAULT_LOOKUP_KEY)) :
                                null);
                sortOrders.add(sortOrder);
            }
            // Create
            Sort sortOptions = new Sort(sortOrders);
            report.setSort(sortOptions);
        } catch (JSONException e) {
            // FUTURE Just log this
            // We "swallow" these since Sort JSON data might not be present
        }

        return report;
    }
}
