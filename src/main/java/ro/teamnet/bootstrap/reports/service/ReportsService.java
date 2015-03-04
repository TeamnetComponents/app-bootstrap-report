package ro.teamnet.bootstrap.reports.service;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * Interface for {@link org.springframework.stereotype.Service} implementations that deal with report generation
 * and exporting.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
public interface ReportsService<T> {

    OutputStream reportFrom(Report report, OutputStream reportOutputStream);

    OutputStream reportFrom(ReportMetadata metadata, Filters filters, Sort sortOptions, OutputStream reportOutputStream);

    /**
     * A method, to be implemented by concrete implementations of this class, which returns the corresponding
     * {@link org.springframework.stereotype.Repository @Repository} implementation, for current
     * {@link org.springframework.stereotype.Service @Service} implementation.
     *
     * @param <ID> The type of the {@code ID}entifier for the domain entity, handled by the service.
     * @return The specific repository instance this service uses for its domain entity management operations.
     */
    <ID extends Serializable> AppRepository<T, ID> getRepository();
}
