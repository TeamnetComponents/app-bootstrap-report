package ro.teamnet.bootstrap.reports.web.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.teamnet.bootstrap.reports.domain.Reportable;
import ro.teamnet.bootstrap.reports.exception.ReportsException;
import ro.teamnet.bootstrap.reports.service.ReportsService;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An abstract (template) RESTful controller class which offers report exporting functionality.
 * This class must be extended by any {@link org.springframework.web.bind.annotation.RestController} that intends to use
 * the contained functionality.
 *
 * @author Bogdan.Iancu
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-06
 */
public abstract class AbstractReportsResource {

    private final ReportsService reportsService;

    public AbstractReportsResource(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @RequestMapping(value = "/reports/pdf", method = RequestMethod.POST)
    public void exportToPdf(Reportable reportable, HttpServletResponse response) {
        try {
            if (reportable == null){
                throw new ReportsException("Invalid or empty report JSON content");
            }
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.pdf\"",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            reportsService.exportFrom(reportable, ExportType.PDF, response.getOutputStream());
            response.getOutputStream().close();
        } catch (ReportsException | IOException | IllegalStateException e) {
            try {
                // FUTURE Log this error
                response.sendError(HttpStatus.BAD_REQUEST.value(), "An error has occurred while exporting the pdf file : " + e.getMessage());
            } catch (IOException | IllegalStateException e1) {
                throw new RuntimeException("A severe error has occurred while exporting the PDF file", e1);
            }
        }
    }

    @RequestMapping(value = "/reports/xls", method = RequestMethod.POST)
    public void exportToXls(Reportable reportable, HttpServletResponse response) {
        try {
            if (reportable == null){
                throw new ReportsException("Invalid or empty report JSON content");
            }
            response.reset();
            response.setContentType("application/vnd.ms-xls");
            response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.xls\"",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            reportsService.exportFrom(reportable, ExportType.XLS, response.getOutputStream());
            response.getOutputStream().close();
        } catch (ReportsException | IOException | IllegalStateException e) {
            try {
                // FUTURE Log this error
                response.sendError(HttpStatus.BAD_REQUEST.value(), "An error has occurred while exporting the pdf file : " + e.getMessage());
            } catch (IOException | IllegalStateException e1) {
                throw new RuntimeException("A severe error has occurred while exporting the XLS file", e1);
            }
        }
    }

    @RequestMapping(value = "/reports/pdf/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToPdf(Reportable Reportable) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(Reportable, ExportType.PDF, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report-%s.pdf\"",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/reports/xls/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToXls(Reportable Reportable) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(Reportable, ExportType.XLS, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report.%s.xls\"",
                new SimpleDateFormat("yyyyMMdd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-xls"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

}