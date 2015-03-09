package ro.teamnet.bootstrap.reports.domain.resolve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * A handler to resolve {@link org.springframework.core.MethodParameter}s related to
 * {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
public class ReportMetadataArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String TITLE_REQUEST_PARAMETER_NAME = "reportTitle";
    public static final String FIELD_COLUMN_REQUEST_PARAMETER_NAME = "reportFieldsColumnMeta";
    public static final String EXTRA_PARAMS_REQUEST_PARAMETER_NAME = "reportExtraParams";

    /**
     * Method to test if a given {@link org.springframework.core.MethodParameter} can be resolved into an
     * {@link ro.teamnet.bootstrap.reports.domain.Report}, using this resolver.
     *
     * @param parameter The parameter to be resolved.
     * @return {@code true} if it can be resolved, {@code false} otherwise.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ReportMetadata.class) || parameter.getParameterType().equals(Report.class);
    }

    /**
     * Handles requests for {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}.
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public ReportMetadata resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        String readValue = objectMapper.readValue(request.getInputStream(), String.class);
        // A holder object
        ReportMetadata reportMetadata = new ReportMetadata();
        // Title
        String reportTitle = webRequest.getParameter(TITLE_REQUEST_PARAMETER_NAME);
        reportMetadata.setTitle(reportTitle);
        // Fields and column metadata
        String reportFieldsColumnsMeta = webRequest.getParameter(FIELD_COLUMN_REQUEST_PARAMETER_NAME);
        Map<String, String> fieldsColumnMetaMap =
                objectMapper.<Map<String, String>>readValue(reportFieldsColumnsMeta, objectMapper.constructType(HashMap.class));
        // Extra parameters metadata
        String reportExtraParamsMeta = webRequest.getParameter(EXTRA_PARAMS_REQUEST_PARAMETER_NAME);
        Map<String, Object> reportExtraParamMetasMap =
                objectMapper.<Map<String, Object>>readValue(reportExtraParamsMeta, objectMapper.constructType(HashMap.class));
        reportMetadata.setFieldsAndTableColumnMetadata(fieldsColumnMetaMap);
        reportMetadata.setExtraParametersMap(reportExtraParamMetasMap);

        return reportMetadata;
    }
}
