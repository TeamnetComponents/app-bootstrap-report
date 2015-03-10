package ro.teamnet.bootstrap.reports.web.rest;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Employee;
import ro.teamnet.bootstrap.reports.repository.EmployeeRepository;
import ro.teamnet.bootstrap.reports.service.EmployeeServiceImpl;
import ro.teamnet.bootstrap.reports.service.ReportsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ReportsControllerTest {

    private static final String REPORT_REQUEST_BODY_JSON = "{\n" +
            "\t\"metadata\" : {\n" +
            "\t\t\"title\" : \"Report title\",\n" +
            "\t\t\"fieldsAndTableColumnMetadata\" : {\n" +
            "\t\t\t\"firstName\" : \"Prenume\",\n" +
            "\t\t\t\"lastName\" : \"Nume\"\n" +
            "\t\t},\n" +
            "\t\t\"extraParametersMap\" : {\n" +
            "\t\t\t\"ReportinatorReportSubTitle\" : \"Subtitlu_Demo\"\n" +
            "\t\t}\n" +
            "\t},\n" +
            "\t\"filters\" : {\n" +
            "        \"filters\": [\n" +
            "            {\n" +
            "                \"property\" : \"myObjectProperty\",\n" +
            "                \"value\" : \"hello world\",\n" +
            "                \"type\" : \"EQUAL\",\n" +
            "                \"negation\" : false,\n" +
            "                \"caseSensitive\" : false\n" +
            "            },\n" +
            "            {\n" +
            "                \"property\" : \"myOtherObjectProperty\",\n" +
            "                \"value\" : \"hello JSON\",\n" +
            "                \"type\" : \"ENDS_WITH\",\n" +
            "                \"negation\" : false,\n" +
            "                \"caseSensitive\" : false\n" +
            "            }\n" +
            "        ]\n" +
            "\t},\n" +
            "    \"sort\": {\n" +
            "        \"orders\": [\n" +
            "            {\n" +
            "                \"direction\" : \"DESC\",\n" +
            "                \"property\" : \"myObjectProperty\",\n" +
            "                \"ignoreCase\" : false,\n" +
            "                \"nullHandling\" : \"NATIVE\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";

    private static final String REPORT_DTO_REQUEST_BODY_JSON = "{\n" +
            "\t\"reportMetadata\" : {\n" +
            "\t\t\"title\" : \"Report title\",\n" +
            "\t\t\"fieldsAndTableColumnMetadata\" : { \n" +
            "\t\t\t\"firstName\" : \"Prenume\",\n" +
            "\t\t\t\"lastName\" : \"Nume\"\n" +
            "\t\t},\n" +
            "\t\t\"extraParametersMap\" : {\n" +
            "\t\t\t\"ReportinatorReportSubTitle\" : \"Subtitlu_Demo\"\n" +
            "\t\t}\n" +
            "\t}" +
            "}";

    @Test
    public void testExportToPdf() throws Exception {
        List<Employee> employees = createEmployees();
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Sort sort = null; // We're interested only
        Filters filters = null; // in the report metadata and data
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees);  // Explicitly call method with this signature
        when(employeeRepository.findAll()).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportsController reportController = new ReportsController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportController)
//              .setCustomArgumentResolvers(
//                    new ReportArgumentResolver()
                .build();
        MvcResult result = mockMvc.perform(
                post("/reports/pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REPORT_REQUEST_BODY_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue("Got wrong HTTP Status in response.",
                result.getResponse().getStatus() == HttpStatus.OK.value());
        assertTrue("Wrong value for 'Content-Type' attribute.",
                result.getResponse().getContentType().equals("application/pdf"));
        assertNotNull(result.getResponse().getContentAsByteArray());
    }

    @Test
    public void testingAlternativeMethodToExportToPdf() throws Exception {
        List<Employee> employees = createEmployees();
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Sort sort = null; // We're interested only
        Filters filters = null; // in the report metadata and data
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees);  // Explicitly call method with this signature
        when(employeeRepository.findAll()).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportsController reportsController = new ReportsController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportsController)
//                .setCustomArgumentResolvers(
//                        new ReportArgumentResolver())
                .build();
        MvcResult result = mockMvc.perform(
                post("/reports/pdf/alternative")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REPORT_DTO_REQUEST_BODY_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue("Got wrong HTTP Status in response.",
                result.getResponse().getStatus() == HttpStatus.OK.value());
        assertTrue("Wrong value for 'Content-Type' attribute.",
                result.getResponse().getContentType().equals("application/pdf"));
        assertNotNull(result.getResponse().getContentAsByteArray());
    }

    @Test
    public void testingAlternativeMethodToExportToXls() throws Exception {
        List<Employee> employees = createEmployees();
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Sort sort = null;  // We're interested only
        Filters filters = null; // in the report metadata and data
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees); // Explicitly call method with this signature
        when(employeeRepository.findAll()).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportsController reportController = new ReportsController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportController)
//                .setCustomArgumentResolvers(
//                    new ReportArgumentResolver()
                .build();
        MvcResult result = mockMvc.perform(
                post("/reports/xls/alternative")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REPORT_DTO_REQUEST_BODY_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue("Got wrong HTTP Status in response.",
                result.getResponse().getStatus() == HttpStatus.OK.value());
        assertTrue("Wrong value for 'Content-Type' attribute.",
                result.getResponse().getContentType().equals("application/vnd.ms-xls"));
        assertNotNull(result.getResponse().getContentAsByteArray());
    }

    private static List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Sad",  "Panda"));
        employees.add(new Employee("Gigi", "Petrescu"));
        return employees;
    }
}
