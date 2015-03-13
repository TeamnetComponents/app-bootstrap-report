package ro.teamnet.bootstrap.reports.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportableArgumentResolver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    public HandlerMethodArgumentResolver reportableArgumentResolver(){
        return new ReportableArgumentResolver();
    }

    @Inject
    private RequestMappingHandlerAdapter adapter;


    @PostConstruct
    public void prioritizeCustomArgumentMethodHandlers() {
        adapter.getCustomArgumentResolvers().add(reportableArgumentResolver());
    }


}
