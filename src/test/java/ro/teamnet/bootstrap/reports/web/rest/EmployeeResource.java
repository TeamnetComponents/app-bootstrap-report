package ro.teamnet.bootstrap.reports.web.rest;

import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.reports.service.EmployeeService;

import javax.inject.Inject;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
@RestController
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;
}
