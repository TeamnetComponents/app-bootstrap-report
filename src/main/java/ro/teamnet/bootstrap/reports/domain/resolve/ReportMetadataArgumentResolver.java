package ro.teamnet.bootstrap.reports.domain.resolve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

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

    private static final String TITLE_REQUEST_PARAMETER_NAME = "reportsTitleMeta";
    private static final String FIELD_COLUMN_REQUEST_PARAMETER_NAME = "reportsFieldsColumnMeta";
    private static final String EXTRA_PARAMS_REQUEST_PARAMETER_NAME = "reportsExtraParams";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ReportMetadata.class) &&
                parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public ReportMetadata resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // A holder object
        ReportMetadata reportMetadata = new ReportMetadata();
        ObjectMapper objectMapper = new ObjectMapper();
        // Title
        String reportTitle = webRequest.getParameter(TITLE_REQUEST_PARAMETER_NAME);
        reportMetadata.setTitle(reportTitle);
        // Fields and column metadata
        String reportFieldsColumnsMeta = webRequest.getParameter(FIELD_COLUMN_REQUEST_PARAMETER_NAME);
//        Map<String, String> fieldsColumnMetaMap =
//                new JSONDeserializer<Map<String, String>>().deserialize(reportFieldsColumnsMeta, Map.class);
        Map<String, String> fieldsColumnMetaMap =
                objectMapper.<Map<String, String>>readValue(reportFieldsColumnsMeta, objectMapper.constructType(HashMap.class));
        // Extra parameters metadata
        String reportExtraParamsMeta = webRequest.getParameter(EXTRA_PARAMS_REQUEST_PARAMETER_NAME);
//        Map<String, Object> reportExtraParamMetasMap =
//                new JSONDeserializer<Map<String, Object>>().deserialize(reportExtraParamsMeta, Map.class);
        Map<String, Object> reportExtraParamMetasMap =
                objectMapper.<Map<String, Object>>readValue(reportExtraParamsMeta, objectMapper.constructType(HashMap.class));
        reportMetadata.setFieldsAndTableColumnMetadata(fieldsColumnMetaMap);
        reportMetadata.setExtraParametersMap(reportExtraParamMetasMap);

        return reportMetadata;
    }
}
