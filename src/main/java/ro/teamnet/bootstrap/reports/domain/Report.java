package ro.teamnet.bootstrap.reports.domain;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;

/**
 * A business entity which represents the logical equivalent of a report.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-04
 */
public class Report implements Reportable {

    /**
     * Filters, to be used to create the report.
     */
    private Filters filters;

    /**
     * A sort order, if required.
     */
    private Sort sort;

    /**
     * Report metadata (such as title, subtitle, extra parameters, etc.).
     */
    private ReportMetadata metadata;

    @Override
    public ReportMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Filters getFilters() {
        return filters;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public void setMetadata(ReportMetadata metadata) {
        this.metadata = metadata;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
