package ro.teamnet.bootstrap.reports.domain;

import java.io.Serializable;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/4/2015
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
