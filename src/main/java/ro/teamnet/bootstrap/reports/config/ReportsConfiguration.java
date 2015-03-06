package ro.teamnet.bootstrap.reports.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ro.teamnet.bootstrap.extend.AppFilterHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.extend.AppSortHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportArgumentResolver;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportMetadataArgumentResolver;

import javax.inject.Inject;

/**
 * Spring configuration class related to reporting.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
@Configuration
@ComponentScan("ro.teamnet.bootstrap.reports")
public class ReportsConfiguration {

    @Bean
    public ReportMetadataArgumentResolver reportMetadataArgumentResolver() {
        return new ReportMetadataArgumentResolver();
    }

    @Bean
    @Inject
    public ReportArgumentResolver
    reportArgumentResolver(ReportMetadataArgumentResolver reportMetadataArgumentResolver,
                           AppFilterHandlerMethodArgumentResolver appFilterHandlerMethodArgumentResolver,
                           AppSortHandlerMethodArgumentResolver appSortHandlerMethodArgumentResolver) {

        return new ReportArgumentResolver(reportMetadataArgumentResolver,
                appFilterHandlerMethodArgumentResolver, appSortHandlerMethodArgumentResolver);
    }
}
