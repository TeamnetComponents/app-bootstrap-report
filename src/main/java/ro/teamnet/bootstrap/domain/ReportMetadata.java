package ro.teamnet.bootstrap.domain;

import java.util.Collection;
import java.util.Map;

/**
 * TODO Documentation
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2/27/2015
 */
public class ReportMetadata {

    private String title;

    private final Map<String, String> fieldsAndTableColumnMetadata;

    private final Collection<?> dataSource;

    public ReportMetadata(Collection<?> dataSource, Map<String, String> fieldsAndTableColumnMetadata) {
        this.dataSource = dataSource;
        this.fieldsAndTableColumnMetadata = fieldsAndTableColumnMetadata;
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

    public Collection<?> getDataSource() {
        return dataSource;
    }

    public Collection<String> getFieldMetadata() {
        return this.fieldsAndTableColumnMetadata.keySet();
    }

    public Collection<String> getTableColumnMetadata() {
        return this.fieldsAndTableColumnMetadata.values();
    }
}
