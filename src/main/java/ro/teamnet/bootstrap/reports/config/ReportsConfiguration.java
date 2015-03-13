package ro.teamnet.bootstrap.reports.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportableArgumentResolver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Spring configuration class related to reporting.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-02-27
 */
@Configuration
@ComponentScan("ro.teamnet.bootstrap.reports")
public class ReportsConfiguration {

    @Inject
    private RequestMappingHandlerAdapter adapter;

    /**
     * Instantiates a custom argument resolver (as a
     * {@link org.springframework.web.method.support.HandlerMethodArgumentResolver} implementation, to handle
     * {@link ro.teamnet.bootstrap.reports.domain.Reportable Reportable} request method parameters.
     *
     * @return An instance of {@code ReportableArgumentResolver} as a {@code HandlerMethodArgumentResolver}.
     * @see ro.teamnet.bootstrap.reports.domain.resolve.ReportableArgumentResolver
     */
    @Bean
    public HandlerMethodArgumentResolver reportableArgumentResolver() {
        return new ReportableArgumentResolver();
    }

    /**
     * Adds custom argument resolvers to Spring, after this configuration class is loaded.
     */
    @PostConstruct
    public void addCustomArgumentMethodHandlers() {
        this.adapter.getCustomArgumentResolvers().add(reportableArgumentResolver());
    }
}
