package ro.teamnet.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
@Configuration
public class ReportsConfiguration {

    /**
     * TODO Doc
     *
     * @return
     */
    @Bean
    @Scope("prototype")
    public JasperReportGenerator.Builder reportBuilder() {

        return JasperReportGenerator.builder();
    }
}
