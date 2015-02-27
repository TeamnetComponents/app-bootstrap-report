package ro.teamnet.bootstrap.service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ReportMetadata;
import ro.teamnet.solutions.reportinator.convert.DataSourceConverter;
import ro.teamnet.solutions.reportinator.convert.jasper.BeanCollectionJasperDataSourceConverter;
import ro.teamnet.solutions.reportinator.export.JasperReportExporter;
import ro.teamnet.solutions.reportinator.export.jasper.type.ExportType;
import ro.teamnet.solutions.reportinator.generation.JasperReportGenerator;
import ro.teamnet.solutions.reportinator.generation.ReportGenerator;

import javax.inject.Inject;
import java.io.OutputStream;
import java.util.Collection;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
@Service
public class ReportsServiceImpl implements ReportsService {

    @Inject
    private JasperReportGenerator.Builder reportBuilder;

    public <T> OutputStream reportFrom(Collection<T> dataSourceAsCollection, ReportMetadata reportMetadata, OutputStream reportOutputStream) {
        // WARNING: Stub code below
        // A converter
        DataSourceConverter<Collection<T>, JRDataSource> dataSourceConverter = new BeanCollectionJasperDataSourceConverter<T>(reportMetadata.getFieldMetadata());
        JRDataSource datasource = dataSourceConverter.convert(dataSourceAsCollection);
        ReportGenerator<JasperPrint> reportGenerator =
                this.reportBuilder.withTitle(reportMetadata.getTitle())
                        .withDatasource(datasource)
                        .withTableColumnsMetadata(reportMetadata.getFieldsAndTableColumnMetadata())
                        .build();
        JasperReportExporter.export(reportGenerator, reportOutputStream, ExportType.PDF);

        return reportOutputStream;
    }
}
