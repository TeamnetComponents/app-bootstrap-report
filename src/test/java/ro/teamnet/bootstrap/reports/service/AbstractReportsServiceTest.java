package ro.teamnet.bootstrap.reports.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Employee;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
import ro.teamnet.bootstrap.reports.exception.ReportsException;
import ro.teamnet.bootstrap.reports.repository.EmployeeRepository;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link ro.teamnet.bootstrap.reports.service.AbstractReportsService}
 *
 * @author Bogdan.Stefan
 */
public class AbstractReportsServiceTest {


    @Mock
    private EmployeeRepository mockEmployeeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockEmployeeRepository.findAll((Filters) isNull(), (Sort) isNull())).thenReturn(Collections.<Employee>emptyList());
    }

    @Test(expected = ReportsException.class)
    public void shouldPassIfRepositoryReturnsEmptyCollection() throws Exception {
        ReportMetadata metadata = new ReportMetadata();
        metadata.setTitle("A test title.");
        Map<String, String> fieldTableColumnsMeta = new HashMap<String, String>();
        fieldTableColumnsMeta.put("col01", "Column one");
        metadata.setFieldsAndTableColumnMetadata(fieldTableColumnsMeta);
        ReportsService employeeReportsService = new EmployeeServiceImpl(mockEmployeeRepository);
        Filters filters = null; // Explicitly
        Sort sort = null;       // set to 'null'
        // Should throw an exception because an empty collection cannot be used to export a report from
        employeeReportsService.exportFrom(metadata, ExportType.PDF, filters, sort, new FileOutputStream("test.pdf"));
    }
}
