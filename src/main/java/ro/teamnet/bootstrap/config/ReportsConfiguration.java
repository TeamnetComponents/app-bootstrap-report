package ro.teamnet.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
@Configuration
@ComponentScan("ro.teamnet.bootstrap.service")
public class ReportsConfiguration {

    /**
     * TODO Doc
     *
     * @return
     */
    @Bean
    public JasperReportGenerator.Builder reportBuilder() {

        return JasperReportGenerator.builder();
    }
}
