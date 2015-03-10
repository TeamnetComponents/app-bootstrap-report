package ro.teamnet.bootstrap.reports.web.rest.dto;

import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 3/9/2015
 */
public class ReportDto {

    private Filters reportFilters;
    private ReportMetadata reportMetadata;
    private Sort reportSortOrders;

    public ReportDto() {
    }

    private ReportDto(Filters reportFilters, ReportMetadata reportMetadata, Sort reportSortOrders) {
        this.reportFilters = reportFilters;
        this.reportMetadata = reportMetadata;
        this.reportSortOrders = reportSortOrders;
    }

    /**
     * A factory method that creates an equivalent {@code DTO} from a {@link ro.teamnet.bootstrap.reports.domain.Report}
     * instance.
     *
     * @param report A proper {@code domain entity} instance.
     * @return A {@code DTO} equivalent of the entity.
     */
    public static ReportDto fromEntity(Report report) {
        if(report == null) {
            throw new IllegalArgumentException("Given report instance must not be null.");
        }

        return new ReportDto(report.getFilters(), report.getMetadata(), report.getSort());
    }

    /**
     * Creates a {@code domain entity} {@link ro.teamnet.bootstrap.reports.domain.Report} instance using this
     * {@code DTO} as the underlying support.
     *
     * @return An instance of the {@code domain entity}.
     */
    public Report toEntity() {
        Report report = new Report();
        report.setFilters(getReportFilters());
        report.setSort(getReportSortOrders());
        report.setMetadata(getReportMetadata());

        return report;
    }

    public Filters getReportFilters() {
        return reportFilters;
    }

    public void setReportFilters(Filters reportFilters) {
        this.reportFilters = reportFilters;
    }

    public ReportMetadata getReportMetadata() {
        return reportMetadata;
    }

    public void setReportMetadata(ReportMetadata reportMetadata) {
        this.reportMetadata = reportMetadata;
    }

    public Sort getReportSortOrders() {
        return reportSortOrders;
    }

    public void setReportSortOrders(Sort reportSortOrders) {
        this.reportSortOrders = reportSortOrders;
    }

}
