package ro.teamnet.bootstrap.reports.service;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
import ro.teamnet.bootstrap.reports.domain.Reportable;
import ro.teamnet.bootstrap.reports.exception.ReportsException;
import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * Interface for {@link org.springframework.stereotype.Service Service} implementations that deal with report generation
 * and exporting.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-02-27
 */
public interface ReportsService<T extends Serializable, ID extends Serializable> extends AbstractService<T, ID> {

    /**
     * Exports a report in a given {@link ro.teamnet.solutions.reportinator.export.jasper.type.ExportType type}, using metadata
     * and data from a {@link ro.teamnet.bootstrap.reports.domain.Report} and writes its contents to an
     * {@link java.io.OutputStream}.
     *
     * @param Reportable       The report metadata and data.
     * @param exportType       The export type (i.e. <em>.PDF, .HTML, .XLS</em>)
     * @param intoOutputStream An {@code OutputStream} to write the report results into.
     */
    void exportFrom(Reportable Reportable, ExportType exportType, OutputStream intoOutputStream) throws ReportsException;

    /**
     * Exports a report in a given {@link ro.teamnet.solutions.reportinator.export.jasper.type.ExportType type}, using
     * metadata from a {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata} and data
     * {@link ro.teamnet.bootstrap.extend.Filters filters} and {@link org.springframework.data.domain.Sort sort}
     * information; writes its contents to an {@link java.io.OutputStream}.
     *
     * @param metadata         The report metadata.
     * @param exportType       The export type (i.e. <em>.PDF, .HTML, .XLS</em>)
     * @param filters          Filters for the data.
     * @param sortOptions      Sorting options (information) for the data.
     * @param intoOutputStream An {@code OutputStream} to write the report results into.
     */
    void exportFrom(ReportMetadata metadata, ExportType exportType, Filters filters, Sort sortOptions, OutputStream intoOutputStream) throws ReportsException;
}
