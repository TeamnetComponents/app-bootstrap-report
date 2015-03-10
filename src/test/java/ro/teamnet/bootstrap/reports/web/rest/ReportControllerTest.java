package ro.teamnet.bootstrap.reports.web.rest;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.teamnet.bootstrap.extend.AppFilterHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.extend.AppSortHandlerMethodArgumentResolver;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Employee;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportArgumentResolver;
import ro.teamnet.bootstrap.reports.domain.resolve.ReportMetadataArgumentResolver;
import ro.teamnet.bootstrap.reports.repository.EmployeeRepository;
import ro.teamnet.bootstrap.reports.service.EmployeeServiceImpl;
import ro.teamnet.bootstrap.reports.service.ReportsService;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ReportControllerTest {

    private String requestBodyJson = "{\n" +
            "\t\"metadata\" : {\n" +
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
        Sort sort = null;
        Filters filters = null;
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportController reportController = new ReportController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportController).build();

        MvcResult result = mockMvc.perform(
                post("/reports/pdf")
                        .contentType(MediaType.APPLICATION_JSON)
 //                       .param("reportTitle" , "titlu Raport Demo")
                        .content(requestBodyJson))
                //.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        HttpServletResponse response = result.getResponse();

        FileOutputStream file = new FileOutputStream("testFile.pdf");
        file.write(result.getResponse().getContentAsByteArray());

        assertTrue(response.getContentType().equals("application/pdf"));

    }

    @Test
    public void testingAlternativeMethodToExportToPdf() throws Exception {
        List<Employee> employees = createEmployees();
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Sort sort = null;
        Filters filters = null;
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportController reportController = new ReportController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportController).setCustomArgumentResolvers(
                new ReportArgumentResolver(
                        new ReportMetadataArgumentResolver(),
                        new AppFilterHandlerMethodArgumentResolver(),
                        new AppSortHandlerMethodArgumentResolver())).build();

        String requestBodyJson = "{\n" +
                "\t\"metadata\" : {\n" +
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

        MvcResult result = mockMvc.perform(
                post("/reports/pdf/alternative")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        HttpServletResponse response = result.getResponse();

        assertTrue(response.getContentType().equals("application/pdf"));

        String content = result.getResponse().getContentAsString();
        System.out.println(response.getContentType());
        //System.out.println(content);
        FileOutputStream file = new FileOutputStream("testFile.pdf");
        file.write(content.getBytes());


    }

    @Test
    public void testingAlternativeMethodToExportToXls() throws Exception {
        List<Employee> employees = createEmployees();
        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
        Sort sort = null;
        Filters filters = null;
        when(employeeRepository.findAll(filters, sort)).thenReturn(employees);
        ReportsService employeeService = new EmployeeServiceImpl(employeeRepository);
        ReportController reportController = new ReportController(employeeService);
        MockMvc mockMvc = standaloneSetup(reportController)
//                .setCustomArgumentResolvers(
//                    new ReportArgumentResolver(
//                            new ReportMetadataArgumentResolver(),
//                            new AppFilterHandlerMethodArgumentResolver(),
//                            new AppSortHandlerMethodArgumentResolver()))
                .build();

        String requestBodyJson = "{\n" +
                "\t\"metadata\" : {\n" +
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

        MvcResult result = mockMvc.perform(
                post("/reports/xls/alternative")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        HttpServletResponse response = result.getResponse();

        assertTrue(response.getContentType().equals("application/vnd.ms-xls"));

        FileOutputStream fileOutputStream = new FileOutputStream("testFile.xls");


        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());


    }
    public List<Employee> createEmployees(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Sad",  "Panda"));
        employees.add(new Employee("Gigi", "Petrescu"));
        return employees;
    }
}