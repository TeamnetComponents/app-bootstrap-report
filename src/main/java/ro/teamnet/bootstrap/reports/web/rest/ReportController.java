package ro.teamnet.bootstrap.reports.web.rest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.service.ReportsService;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bogdan.Iancu
 * @author Andrei.Marica
 * @version 1.0 Date: 06-Mar-15
 */

@RestController
@RequestMapping("/reports")
public class ReportController {

    //TODO will this be final ?
    private ReportsService reportsService;



    @Autowired
    public ReportController(ReportsService reportsService){
        this.reportsService = reportsService;
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.POST)
    public void exportToPdf(Report report, HttpServletResponse response){

//        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename\"Report%s.pdf\"",
                new SimpleDateFormat("dd-MM-yyyy").format(new Date())));

        try {
            reportsService.exportFrom(report, ExportType.PDF, response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occurred while exporting the pdf file");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }

    @RequestMapping(value = "/xls", method = RequestMethod.POST)
    public void exportToXls(Report report, HttpServletResponse response){

        response.reset();
        response.setContentType("application/vnd.ms-xls");
        response.setHeader("Content-Dosposition", String.format("attachment; filename\"Report%s.xls\"",
                new SimpleDateFormat("dd-MM-yyyy").format(new Date())));

        try {
            reportsService.exportFrom(report, ExportType.XLS, response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            try {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error has occured while exporting the xls file");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }



}
