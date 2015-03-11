package ro.teamnet.bootstrap.reports.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.exception.ReportsException;
import ro.teamnet.bootstrap.reports.service.ReportsService;
import ro.teamnet.bootstrap.reports.web.rest.dto.ReportDto;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bogdan.Iancu
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-06
 */

@RestController
@RequestMapping("/reports")
public class ReportsController {

    // TODO will this be final ?
    private ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.POST)
    public void exportToPdf(@RequestBody Report report, HttpServletResponse response) {
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.pdf\"",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        try {
            reportsService.exportFrom(report, ExportType.PDF, response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException | IllegalStateException e) {
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occurred while exporting the pdf file");
            } catch (IOException | IllegalStateException e1) {
                throw new ReportsException("An error has occurred while exporting the pdf file", e1.getCause());
            }
        }
    }

    @RequestMapping(value = "/xls", method = RequestMethod.POST)
    public void exportToXls(@RequestBody Report report, HttpServletResponse response) {

        response.reset();
        response.setContentType("application/vnd.ms-xls");
        response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.xls\"",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        try {
            reportsService.exportFrom(report, ExportType.XLS, response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException | IllegalStateException e) {
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occurred while exporting the xls file");
            } catch (IOException | IllegalStateException e1) {
                throw new ReportsException("An error has occurred while exporting the xls file", e1.getCause());
            }
        }
    }

    @RequestMapping(value = "/pdf/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToPdf(@RequestBody ReportDto reportDto) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(reportDto, ExportType.PDF, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report-%s.pdf\"",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/xls/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToXls(@RequestBody ReportDto reportDto) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(reportDto, ExportType.XLS, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report.%s.xls\"",
                new SimpleDateFormat("yyyyMMdd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-xls"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

}
