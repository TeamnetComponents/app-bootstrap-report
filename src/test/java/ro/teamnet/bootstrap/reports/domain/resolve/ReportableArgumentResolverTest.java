package ro.teamnet.bootstrap.reports.domain.resolve;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.web.context.request.NativeWebRequest;
import ro.teamnet.bootstrap.reports.config.ReportsTestConstants;
import ro.teamnet.bootstrap.reports.domain.Reportable;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests for {@link ro.teamnet.bootstrap.reports.domain.resolve.ReportableArgumentResolver}
 *
 * @author Bogdan.Stefan
 */
public class ReportableArgumentResolverTest {

    @Mock
    private MethodParameter mockMethodParameter;

    @Mock
    private HttpServletRequest mockServletRequest;

    @Mock
    private NativeWebRequest mockNativeWebRequest;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        initMocks(this);
        // Default supported parameter type(s)
        Class parameterClass = Reportable.class;
        when(mockMethodParameter.getParameterType()).thenReturn(parameterClass);

        InputStream dataSourceStream = new ByteArrayInputStream(ReportsTestConstants.REPORT_REQUEST_BODY_JSON.getBytes());
        DelegatingServletInputStream dsis = new DelegatingServletInputStream(dataSourceStream);
        when(mockServletRequest.getInputStream()).thenReturn(dsis);
        when(mockNativeWebRequest.getNativeRequest()).thenReturn(mockServletRequest);
    }

    @Test
    public void shouldPassIfParameterIsSupported() throws Exception {
        ReportableArgumentResolver rar = new ReportableArgumentResolver();
        assertTrue("Instances of the given type should be supported by the argument resolver.", rar.supportsParameter(mockMethodParameter));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldPassIfParameterIsNotSupported() throws Exception {
        Class unsupportedParameter = Object.class;
        when(mockMethodParameter.getParameterType()).thenReturn(unsupportedParameter);
        ReportableArgumentResolver rar = new ReportableArgumentResolver();
        assertFalse("Instances of the given type should not be supported by the argument resolver.", rar.supportsParameter(mockMethodParameter));
    }

    @Test
    public void shouldReturnAValidReportableInstance() throws Exception {
        ReportableArgumentResolver rar = new ReportableArgumentResolver();
        Reportable reportable = rar.resolveArgument(mockMethodParameter, null, mockNativeWebRequest, null);
        assertNotNull("Returned instance should not be null.", reportable);
        assertNotNull("Returned instance should have non-null metadata.", reportable.getMetadata());
        assertNotNull("Returned instance should have non-null filter information.", reportable.getFilters());
        assertNotNull("Returned instance should have non-null sort ordering.", reportable.getSort());
    }

    @Test
    public void shouldReturnAReportableInstanceHavingOnlyMetadata() throws Exception {
        InputStream is = new ByteArrayInputStream(ReportsTestConstants.REPORT_REQUEST_BODY_JSON_NO_FILTERS_AND_SORT.getBytes());
        DelegatingServletInputStream dsis = new DelegatingServletInputStream(is);
        when(mockServletRequest.getInputStream()).thenReturn(dsis);
        when(mockNativeWebRequest.getNativeRequest()).thenReturn(mockServletRequest);

        ReportableArgumentResolver rar = new ReportableArgumentResolver();
        Reportable reportable = rar.resolveArgument(mockMethodParameter, null, mockNativeWebRequest, null);
        assertNotNull("Returned instance should not be null.", reportable);
        assertNotNull("Returned instance should have non-null metadata.", reportable.getMetadata());
        assertNull("Returned instance should have null filter information.", reportable.getFilters());
        assertNull("Returned instance should have null sort ordering.", reportable.getSort());
    }
}
