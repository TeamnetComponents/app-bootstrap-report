package ro.teamnet.bootstrap.reports.domain;

import java.io.Serializable;

/**
 * <strong>ATTENTION:</strong> This class is a model that will be used ONLY in tests (along with
 * {@link ro.teamnet.bootstrap.reports.repository.EmployeeRepository EmployeeRepository},
 * {@link ro.teamnet.bootstrap.reports.service.EmployeeService EmployeeService} etc.)
 * and SHOULD NOT be counted upon on anything outside the {@code test} folder.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-04
 */
public class Employee implements Serializable {

    private final String firstName;
    private final String lastName;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
