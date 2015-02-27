package ro.teamnet.bootstrap.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.service.ReportsService;

import javax.inject.Inject;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
@RestController
@RequestMapping("/reports")
public class ReportsResource {

    private final ReportsService reportsService;

    @Inject
    public ReportsResource(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    // Add useful methods here
}
