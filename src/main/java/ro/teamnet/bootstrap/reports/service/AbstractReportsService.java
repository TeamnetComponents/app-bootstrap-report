package ro.teamnet.bootstrap.reports.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
import ro.teamnet.bootstrap.reports.web.rest.dto.ReportDto;
import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;
import ro.teamnet.solutions.reportinator.convert.jasper.BeanCollectionJasperDataSourceConverter;
import ro.teamnet.solutions.reportinator.export.JasperReportExporter;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * An abstract (template) class, which offers {@link ro.teamnet.bootstrap.reports.service.ReportsService} functionality.
 * This class must be extended by any {@link org.springframework.stereotype.Service @Service} that intends to offer support
 * for report generation for the managed entity types.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-02-27
 */
public abstract class AbstractReportsService<T extends Serializable, ID extends Serializable>
        extends AbstractServiceImpl<T, ID> implements ReportsService, AbstractService<T, ID> {

    /**
     * Sole constructor. Receives an {@link ro.teamnet.bootstrap.extend.AppRepository} concrete implementation, usually
     * through <em>Dependency Injection</em>, and uses this {@link org.springframework.stereotype.Repository @Repository} instance
     * to pull report data from the persistence layer.
     *
     * @param repository A {@code Repository} to pull required entity data from.
     */
    public AbstractReportsService(AppRepository<T, ID> repository) {
        super(repository);
    }

    /**
     * {@inheritDoc}
     */
    public void exportFrom(ReportDto reportDto, ExportType exportType, OutputStream intoOutputStream) {
        this.exportFrom(reportDto.toEntity(), exportType, intoOutputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exportFrom(Report report, ExportType exportType, OutputStream intoOutputStream) {
        this.exportFrom(report.getMetadata(), exportType, report.getFilters(), report.getSort(), intoOutputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exportFrom(ReportMetadata metadata, ExportType exportType, Filters filters, Sort sortOptions, OutputStream intoOutputStream) {
        // Obtain entity collection
        List<T> entityCollection = (filters == null || sortOptions == null) ?
                super.getRepository().findAll() :
                super.getRepository().findAll(filters, sortOptions);
        // A data source converter
        DataSourceConverter<Collection<T>, JRDataSource> dataSourceConverter =
                new BeanCollectionJasperDataSourceConverter<T>(metadata.getFieldMetadata());
        // Obtain data source from converter
        JRDataSource dataSource = dataSourceConverter.convert(entityCollection);
        // Create a generator using metadata and above data source
        ReportGenerator<JasperPrint> reportGenerator = JasperReportGenerator.builder()
                .withTitle(metadata.getTitle())
                .withDatasource(dataSource)
                .withTableColumnsMetadata(metadata.getFieldsAndTableColumnMetadata())
                .withParameters(metadata.getExtraParametersMap())
                .build();
        // Export
        JasperReportExporter.export(reportGenerator, intoOutputStream, exportType);
    }
}
