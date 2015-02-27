package ro.teamnet.bootstrap.service;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import ro.teamnet.bootstrap.domain.ReportMetadata;
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

    public <T> OutputStream reportFrom(Collection<T> beanOrMapCollection, ReportMetadata reportMetadata, OutputStream reportOutputStream) {
        // Stub code below
        ReportGenerator<JasperPrint> reportGenerator =
                this.reportBuilder.withTitle(reportMetadata.getTitle())
                        .withDatasource(new BeanCollectionJasperDataSourceConverter<T>(reportMetadata.getFieldMetadata()).convert(beanOrMapCollection))
                        .withTableColumnsMetadata(reportMetadata.getFieldsAndTableColumnMetadata())
                        .build();

        JasperReportExporter.export(reportGenerator, reportOutputStream, ExportType.PDF);

        return reportOutputStream;
    }
}
