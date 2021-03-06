package ro.teamnet.bootstrap.reports.domain;

import java.util.Collection;
import java.util.Map;

/**
 * Metadata related to report generation. This class holds various report characteristics (such as title, subtitle, field and
 * column metadata, extra parameters etc.). A report's {@code title} and {@code data source fields and columns metadata} are mandatory
 * fields, and thus should be present in the JSON body.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-02-27
 */
public class ReportMetadata {

    /**
     * The report's title.
     */
    private String title;

    /**
     * The field and column metadata information.
     */
    private Map<String, String> fieldsAndTableColumnMetadata;

    /**
     * Extra parameters, to be used as {@link net.sf.jasperreports.engine.JRParameter JRParameter} values, at runtime.
     */
    private Map<String, Object> extraParametersMap;

    public ReportMetadata() {
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

    public Map<String, Object> getExtraParametersMap() {
        return extraParametersMap;
    }

    public void setFieldsAndTableColumnMetadata(Map<String, String> fieldsAndTableColumnMetadata) {
        this.fieldsAndTableColumnMetadata = fieldsAndTableColumnMetadata;
    }

    /**
     *  Sets extra parameters for a report, to be used as {@link net.sf.jasperreports.engine.JRParameter JRParameter}
     *  values, at runtime.
     */
    public void setExtraParametersMap(Map<String, Object> extraParametersMap) {
        this.extraParametersMap = extraParametersMap;
    }
}
