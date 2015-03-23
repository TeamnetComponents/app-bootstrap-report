package ro.teamnet.bootstrap.reports.domain;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;

/**
 * An interface to be implemented by business entities which represent the logical equivalent of a report. A
 * {@code Reportable} contains {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata report metadata},
 * {@link ro.teamnet.bootstrap.extend.Filters filters} and {@link org.springframework.data.domain.Sort sort} information
 * to be used to create a report in different formats.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-11
 */
public interface Reportable {

    /**
     * Returns a report's {@link ro.teamnet.bootstrap.reports.domain.ReportMetadata metadata}.
     *
     * @return A {@code ReportMetadata} instance.
     */
    ReportMetadata getMetadata();

    /**
     * Returns the {@link ro.teamnet.bootstrap.extend.Filters filters} to be used to create the report.
     *
     * @return A {@code Filters} instance.
     */
    Filters getFilters();

    /**
     * Returns the {@link org.springframework.data.domain.Sort} options.
     *
     * @return A {@code Sort} instance.
     */
    Sort getSort();
}
