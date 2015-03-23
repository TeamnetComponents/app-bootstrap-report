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
import ro.teamnet.bootstrap.web.rest.AbstractResource;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An abstract (template) RESTful controller class which offers report exporting functionality.
 * This class must be extended by any {@link org.springframework.web.bind.annotation.RestController @RestController} that intends to use
 * the contained functionality.
 * <p>
 * It makes use of the concept of a {@link ro.teamnet.bootstrap.reports.domain.Reportable}
 * (and {@link ro.teamnet.bootstrap.reports.domain.resolve.ReportableArgumentResolver ReportableArgumentResolver} respectively),
 * to pass report data to its handler methods.
 * </p>
 *
 * @author Bogdan.Iancu
 * @author Andrei.Marica
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-06
 */
public abstract class AbstractReportsResource<T extends Serializable, ID extends Serializable>
        extends AbstractResource<T, ID> {

    private final ReportsService<T, ID> reportsService;

    /**
     * @return The underlying {@link ro.teamnet.bootstrap.reports.service.ReportsService} instance.
     */
    public ReportsService<T, ID> getReportsService() {
        return reportsService;
    }

    /**
     * Sole constructor. Initializes an instance of this type with the given
     * {@link ro.teamnet.bootstrap.reports.service.ReportsService}, usually received through <em>Dependency Injection</em>.
     *
     * @param reportsService A service offering report generation functionality.
     */
    public AbstractReportsResource(ReportsService<T, ID> reportsService) {
        super(reportsService);
        this.reportsService = reportsService;
    }

    /**
     * A request handler which creates a report from a given {@link ro.teamnet.bootstrap.reports.domain.Reportable} instance
     * and writes the output as <em>.PDF</em> in the {@link javax.servlet.http.HttpServletResponse response}.
     *
     * @param reportable An instance received via request (in JSON format).
     * @param response The servlet response.
     */
    @RequestMapping(value = "/reports/pdf", method = RequestMethod.POST)
    public void exportToPdf(Reportable reportable, HttpServletResponse response) {
        try {
            if (reportable == null) {
                throw new ReportsException("Invalid or empty report JSON content");
            }
            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.pdf\"",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            reportsService.exportFrom(reportable, ExportType.PDF, response.getOutputStream());
        } catch (ReportsException | IOException | IllegalStateException e) {
            try {
                // FUTURE Log this error
                response.sendError(HttpStatus.BAD_REQUEST.value(), "An error has occurred while exporting the pdf file : " + e.getMessage());
            } catch (IOException | IllegalStateException e1) {
                throw new RuntimeException("A severe error has occurred while exporting the PDF file", e1);
            }
        }
    }

    /**
     * A request handler which creates a report from a given {@link ro.teamnet.bootstrap.reports.domain.Reportable} instance
     * and writes the output as <em>Excel (.XLS)</em> in the {@link javax.servlet.http.HttpServletResponse response}.
     *
     * @param reportable An instance received via request.
     * @param response The servlet response.
     */
    @RequestMapping(value = "/reports/xls", method = RequestMethod.POST)
    public void exportToXls(Reportable reportable, HttpServletResponse response) {
        try {
            if (reportable == null) {
                throw new ReportsException("Invalid or empty report JSON content");
            }
            response.reset();
            response.setContentType("application/vnd.ms-xls");
            response.setHeader("Content-Disposition", String.format("attachment; filename\"Report-%s.xls\"",
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            reportsService.exportFrom(reportable, ExportType.XLS, response.getOutputStream());
        } catch (ReportsException | IOException | IllegalStateException e) {
            try {
                // FUTURE Log this error
                response.sendError(HttpStatus.BAD_REQUEST.value(), "An error has occurred while exporting the XLS file : " + e.getMessage());
            } catch (IOException | IllegalStateException e1) {
                throw new RuntimeException("A severe error has occurred while exporting the XLS file", e1);
            }
        }
    }

    /**
     * An alternative request handler which creates a report from a given {@link ro.teamnet.bootstrap.reports.domain.Reportable} instance
     * and writes the output as <em>.PDF</em> in a {@link org.springframework.http.ResponseEntity} of {@link java.lang.Byte}.
     *
     * @param reportable An instance received via request (in JSON format).
     */
    @RequestMapping(value = "/reports/pdf/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToPdf(Reportable reportable) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(reportable, ExportType.PDF, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report-%s.pdf\"",
                new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    /**
     * An alternative request handler which creates a report from a given {@link ro.teamnet.bootstrap.reports.domain.Reportable} instance
     * and writes the output as <em>Excel (.XLS)</em> in a {@link org.springframework.http.ResponseEntity} of {@link java.lang.Byte}.
     *
     * @param reportable An instance received via request (in JSON format).
     */
    @RequestMapping(value = "/reports/xls/alternative", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> alternativeMethodToExportToXls(Reportable reportable) {
        //verification here? maybe to return some HttpStatus if there are problems?

        //opening the bytearrayoutputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //calling the service method
        reportsService.exportFrom(reportable, ExportType.XLS, byteArrayOutputStream);

        //setting the headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Context-Disposition", String.format("attachment; filename=\"Report-%s.xls\"",
                new SimpleDateFormat("yyyyMMdd").format(new Date())));
        httpHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-xls"));

        //returning the Response Entity
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

}
