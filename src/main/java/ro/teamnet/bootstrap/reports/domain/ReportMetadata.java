package ro.teamnet.bootstrap.reports.domain;

import java.util.Collection;
import java.util.Map;

/**
 * Metadata related to report generation.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
public class ReportMetadata {

    /**
     * The report's title.
     */
    private String title;

    /**
     * The field and column metadata information.
     */
    private final Map<String, String> fieldsAndTableColumnMetadata;

    /**
     * Extra parameters, to be used as {@link net.sf.jasperreports.engine.JRParameter JRParameter} values, at runtime.
     */
    private final Map<String, Object> parametersMap;

    public ReportMetadata(Map<String, String> fieldsAndTableColumnMetadata, Map<String, Object> parametersMap) {
        this.fieldsAndTableColumnMetadata = fieldsAndTableColumnMetadata;
        this.parametersMap = parametersMap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getFieldsAndTableColumnMetadata() {
        return fieldsAndTableColumnMetadata;
    }

    public Collection<String> getFieldMetadata() {
        return this.fieldsAndTableColumnMetadata.keySet();
    }

    public Collection<String> getTableColumnMetadata() {
        return this.fieldsAndTableColumnMetadata.values();
    }

    public Map<String, Object> getParametersMap() {
        return parametersMap;
    }
}
