package ro.teamnet.bootstrap.reports.service;

import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.reports.domain.Employee;
import ro.teamnet.bootstrap.reports.repository.EmployeeRepository;

import javax.inject.Inject;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
 */
@Service
public class EmployeeServiceImpl extends AbstractReportsService<Employee, Long> implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Inject
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;

    }
}
