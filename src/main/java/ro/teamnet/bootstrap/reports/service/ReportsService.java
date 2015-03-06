package ro.teamnet.bootstrap.reports.service;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import java.io.OutputStream;

/**
 * Interface for {@link org.springframework.stereotype.Service Service} implementations that deal with report generation
 * and exporting.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
public interface ReportsService {

    /**
     * TODO Doc
     *
     * @param report
     * @param exportType
     * @param intoOutputStream An {@code OutputStream} to write the report results into.
     * @return
     */
    void exportFrom(Report report, ExportType exportType, OutputStream intoOutputStream)  ;

    /**
     * TODO Doc
     *
     * @param metadata
     * @param exportType
     * @param filters
     * @param sortOptions
     * @param intoOutputStream An {@code OutputStream} to write the report results into.
     * @return
     */
    void exportFrom(ReportMetadata metadata, ExportType exportType, Filters filters, Sort sortOptions, OutputStream intoOutputStream);
}
