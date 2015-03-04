package ro.teamnet.bootstrap.reports.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.data.domain.Sort;
import ro.teamnet.bootstrap.extend.AppRepository;
import ro.teamnet.bootstrap.extend.Filters;
import ro.teamnet.bootstrap.reports.domain.Report;
import ro.teamnet.bootstrap.reports.domain.ReportMetadata;
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
 * This class must be extended by any {@link org.springframework.stereotype.Service} which intends to offer support
 * for report generation.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
public class AbstractReportsService<T extends Serializable, ID extends Serializable>
        extends AbstractServiceImpl<T, ID> implements ReportsService {

    public AbstractReportsService(AppRepository<T, ID> repository) {
        super(repository);
    }

    @Override
    public OutputStream exportFrom(Report report, OutputStream reportOutputStream) {
        return exportFrom(report.getMetadata(), report.getFilters(), report.getSort(), reportOutputStream);
    }

    @Override
    public OutputStream exportFrom(ReportMetadata metadata, Filters filters, Sort sortOptions, OutputStream reportOutputStream) {
        // Obtain entity collection
//        List<T> entityCollection = this.getRepository().<T>findAll(filters, sortOptions);
        List<T> entityCollection = getRepository().findAll(filters, sortOptions);
        // A converter
        DataSourceConverter<Collection<T>, JRDataSource> dataSourceConverter =
                new BeanCollectionJasperDataSourceConverter<T>(metadata.getFieldMetadata());
        // Obtain data source from converter
        JRDataSource dataSource = dataSourceConverter.convert(entityCollection);
        // Create a generator using metadata and above data source
        ReportGenerator<JasperPrint> reportGenerator = JasperReportGenerator.builder().withTitle(metadata.getTitle())
                .withDatasource(dataSource)
                .withTableColumnsMetadata(metadata.getFieldsAndTableColumnMetadata())
                .withParameters(metadata.getParametersMap())
                .build();
        // Export // TODO Maybe we should run this export, on another thread?
        JasperReportExporter.export(reportGenerator, reportOutputStream, ExportType.PDF);

        return reportOutputStream;
    }

//    /**
//     * Returns the corresponding {@link org.springframework.stereotype.Repository @Repository} implementation, for this
//     * {@link org.springframework.stereotype.Service @Service} implementation.
//     *
//     * @param <ID> The type of the {@code ID}entifier for the domain entity, handled by the service.
//     * @return The specific repository this service uses for its domain entity management operations.
//     */
//    public abstract <ID extends Serializable> AppRepository<T, ID> getRepository();
}
