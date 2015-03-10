package ro.teamnet.bootstrap.reports.domain.resolve;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.reports.domain.Report;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * A handler to resolve {@link org.springframework.core.MethodParameter}s related to
 * {@link ro.teamnet.bootstrap.reports.domain.Report}.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
public class ReportArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * Sole constructor. Constructors a <em>resolver</em> which can resolve
     * {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}, {@link ro.teamnet.bootstrap.extend.Filters}
     * and {@link org.springframework.data.domain.Sort} in order to package them all under a
     * {@link ro.teamnet.bootstrap.reports.domain.Report}.
     */
    public ReportArgumentResolver() {
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
        return parameter.getParameterType().equals(Report.class);
    }

    /**
     * Handles requests for {@link ro.teamnet.bootstrap.reports.domain.Report} (equivalent of a report's
     * domain entity).
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public Report resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                     NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Report report = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            report = objectMapper.readValue(request.getInputStream(), Report.class);
        } catch (IOException e) {
            // FUTURE Log this exception
            return null;
        }

        return report;
    }
}
