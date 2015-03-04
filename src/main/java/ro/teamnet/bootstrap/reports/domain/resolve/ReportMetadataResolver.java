package ro.teamnet.bootstrap.reports.domain.resolve;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

/**
 * A handler to resolve {@link org.springframework.core.MethodParameter}s related to
 * {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
public class ReportMetadataResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ReportMetadata.class);
    }

    @Override
    public ReportMetadata resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                          NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // TODO Implement this
        return null;
    }
}
