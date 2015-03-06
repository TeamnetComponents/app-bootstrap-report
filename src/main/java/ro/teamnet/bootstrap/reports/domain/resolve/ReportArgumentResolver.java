package ro.teamnet.bootstrap.reports.domain.resolve;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ro.teamnet.bootstrap.extend.AppFilterHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.extend.AppSortHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

/**
 * A handler to resolve {@link org.springframework.core.MethodParameter}s related to
 * {@link ro.teamnet.bootstrap.reports.domain.Report}.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
public class ReportArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     *  A resolver for {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}.
     */
    private final HandlerMethodArgumentResolver metadataResolver;

    /**
     * A resolver for {@link ro.teamnet.bootstrap.extend.Filters}.
     */
    private final HandlerMethodArgumentResolver filtersResolver;

    /**
     * A resolver for {@link org.springframework.data.domain.Sort}.
     */
    private final HandlerMethodArgumentResolver sortResolver;

    /**
     * Sole constructor. Constructors a <em>resolver</em> which accepts
     * {@link org.springframework.web.method.support.HandlerMethodArgumentResolver} implementations
     * each of which can in turn resolve {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata}, {@link ro.teamnet.bootstrap.extend.Filters}
     * and {@link org.springframework.data.domain.Sort} in order to package them all under a
     * {@link ro.teamnet.bootstrap.reports.domain.Report}.
     *
     * @param metadataResolver A resolver for {@code ReportMetadata}.
     * @param filtersResolver  A resolver for {@code Filters}.
     * @param sortResolver     A resolver for {@code Sort}.
     */
    public ReportArgumentResolver(ReportMetadataArgumentResolver metadataResolver,
                                  AppFilterHandlerMethodArgumentResolver filtersResolver,
                                  AppSortHandlerMethodArgumentResolver sortResolver) {
        this.metadataResolver = metadataResolver;
        this.filtersResolver = filtersResolver;
        this.sortResolver = sortResolver;
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
        return parameter.getParameterType().equals(Report.class) &&
                parameter.hasParameterAnnotation(RequestBody.class);
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
        Report report = new Report();
        if (this.metadataResolver.supportsParameter(parameter)) {
            ReportMetadata resolvedMetadata =
                    ReportMetadata.class.cast(this.metadataResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory));
            report.setMetadata(resolvedMetadata);
        }
        if (this.filtersResolver.supportsParameter(parameter)) {
            Filters resolvedFilters =
                    Filters.class.cast(this.filtersResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory));
            report.setFilters(resolvedFilters);
        }
        if (this.sortResolver.supportsParameter(parameter)) {
            Sort resolvedSort = Sort.class.cast(this.sortResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory));
            report.setSort(resolvedSort);
        }
        // Something went wrong ?
        if (report.getMetadata() == null || report.getFilters() == null || report.getSort() == null) {
            return null;
        }

        return report;
    }
}
