package ro.teamnet.bootstrap.reports.service;

import org.junit.After;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.*;

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

    private List<Employee> employeeList = new ArrayList<Employee>();

    private static final File REPORT_FILE = new File("testReport.pdf");
    private OutputStream out;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockEmployeeRepository.findAll((Filters) isNull(), (Sort) isNull())).thenReturn(Collections.<Employee>emptyList());
    }

    @Test(expected = ReportsException.class)
    public void shouldPassIfRepositoryReturnsEmptyCollection() throws Exception {
        out =  new FileOutputStream(REPORT_FILE);
        ReportMetadata metadata = new ReportMetadata();
        metadata.setTitle("A test title.");
        Map<String, String> fieldTableColumnsMeta = new HashMap<String, String>();
        fieldTableColumnsMeta.put("col01", "Column one");
        metadata.setFieldsAndTableColumnMetadata(fieldTableColumnsMeta);
        ReportsService employeeReportsService = new EmployeeServiceImpl(mockEmployeeRepository);
        Filters filters = null; // Explicitly
        Sort sort = null;       // set to 'null'
        // Should throw an exception because an empty collection cannot be used to export a report from
        employeeReportsService.exportFrom(metadata, ExportType.PDF, filters, sort, out);
    }

    @Test
    public void shouldPassIfReportMetadataContainsNoExtraParameters() throws Exception {
        out =  new FileOutputStream(REPORT_FILE);
        // Override repository behaviour
        employeeList.add(new Employee("John", "Doe"));
        employeeList.add(new Employee("Jane", "Doe"));
        when(mockEmployeeRepository.findAll()).thenReturn(employeeList);
        ReportMetadata metadata = new ReportMetadata();
        metadata.setTitle("No extra parameters report.");
        Map<String, String> fieldTableColumnsMeta = new HashMap<String, String>();
        fieldTableColumnsMeta.put("firstName", "First name");
        fieldTableColumnsMeta.put("lastName", "Last name");
        metadata.setFieldsAndTableColumnMetadata(fieldTableColumnsMeta);
        ReportsService employeeReportsService = new EmployeeServiceImpl(mockEmployeeRepository);
        Filters filters = null; // Explicitly
        Sort sort = null;       // set to 'null'
        employeeReportsService.exportFrom(metadata, ExportType.PDF, filters, sort, out);
    }

    @After
    public void cleanUp() throws Exception {
        if(out != null) {
            out.close();
        }
        if(Files.exists(REPORT_FILE.toPath())) {
            REPORT_FILE.setWritable(true);
            REPORT_FILE.delete();
        }
    }
}
