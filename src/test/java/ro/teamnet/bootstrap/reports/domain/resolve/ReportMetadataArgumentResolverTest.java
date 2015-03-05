package ro.teamnet.bootstrap.reports.domain.resolve;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.NativeWebRequest;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO Documentation (test description)
 *
 * @author Bogdan.Stefan
 */
public class ReportMetadataArgumentResolverTest {

    private static final String TITLE_REQUEST_PARAMETER_NAME = "reportsTitleMeta";
    private static final String FIELD_COLUMN_REQUEST_PARAMETER_NAME = "reportsFieldsColumnMeta";
    private static final String EXTRA_PARAMS_REQUEST_PARAMETER_NAME = "reportsExtraParams";

    private static final String REPORT_TITLE_REQUEST_PARAM_VALUE = "A test report title";
    private static final String REPORT_FIELD_COLUMN_JSON_REQUEST_PARAM_VALUE =
            "{" +
                    " \"col1\" : \"Column 1\", " +
                    " \"col2\" : \"Column 2\"" +
                    "}";
    private static final String REPORT_EXTRA_PARAMS_JSON_REQUEST_PARAM_VALUE =
            "{" +
                    " \"ReportinatorReportSubTitle\" : \"A test report subtitle\"" +
                    "}";
    private ReportMetadataArgumentResolver reportMetadataArgumentResolver;
    private NativeWebRequest mockRequest;
    private MethodParameter mockMParameter;

    @Before
    public void setUp() throws Exception {
        // Creates ArgumentResolver to be tested
        reportMetadataArgumentResolver = new ReportMetadataArgumentResolver();
        // Establishes needed Request information
        mockRequest = mock(NativeWebRequest.class);
        when(mockRequest.getParameter(TITLE_REQUEST_PARAMETER_NAME))
                .thenReturn(REPORT_TITLE_REQUEST_PARAM_VALUE);
        when(mockRequest.getParameter(FIELD_COLUMN_REQUEST_PARAMETER_NAME))
                .thenReturn(REPORT_FIELD_COLUMN_JSON_REQUEST_PARAM_VALUE);
        when(mockRequest.getParameter(EXTRA_PARAMS_REQUEST_PARAMETER_NAME))
                .thenReturn(REPORT_EXTRA_PARAMS_JSON_REQUEST_PARAM_VALUE);
        // Establishes MethodParameter
        mockMParameter = mock(MethodParameter.class);
        Class reportMetadataClass = ReportMetadata.class;
        Class requestBodyAnnotationClass = RequestBody.class;
        when(mockMParameter.getParameterType()).thenReturn(reportMetadataClass);
        when(mockMParameter.hasParameterAnnotation(requestBodyAnnotationClass)).thenReturn(true);
    }

    @Test
    public void testResolverSupportsReportDataParameter() {
        assertTrue(reportMetadataArgumentResolver.supportsParameter(mockMParameter));
    }

    @Test
    public void testArgumentResolverReturnsAReportMetadataInstance() throws Exception {
        ReportMetadata reportMetadata = reportMetadataArgumentResolver.resolveArgument(mockMParameter, null, mockRequest, null);
        assertNotNull(reportMetadata);
    }

    @Test
    public void testArgumentResolverReturnsAValidReportMetadataInstance() throws Exception {
        ReportMetadata reportMetadata = reportMetadataArgumentResolver.resolveArgument(mockMParameter, null, mockRequest, null);
        assertEquals("Resolved ReportMetadata instance title field is not valid.", reportMetadata.getTitle(), REPORT_TITLE_REQUEST_PARAM_VALUE);
    }
}
