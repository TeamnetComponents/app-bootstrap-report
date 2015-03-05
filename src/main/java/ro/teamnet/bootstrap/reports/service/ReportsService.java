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
     * @param reportOutputStream
     * @return
     */
    void exportFrom(Report report, ExportType exportType, OutputStream reportOutputStream)  ;

    /**
     * TODO Doc
     *
     * @param metadata
     * @param exportType
     * @param filters
     * @param sortOptions
     * @param intoOutputStream
     * @return
     */
    void exportFrom(ReportMetadata metadata, ExportType exportType, Filters filters, Sort sortOptions, OutputStream intoOutputStream);

//    /**
//     * A method, to be implemented by concrete implementations of this class, which returns the corresponding
//     * {@link org.springframework.stereotype.Repository @Repository} implementation, for current
//     * {@link org.springframework.stereotype.Service @Service} implementation.
//     *
//     * @return The specific repository instance this service uses for its domain entity management operations.
//     */
//    AppRepository<T, ID> getRepository();
}
