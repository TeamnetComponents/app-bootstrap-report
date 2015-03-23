package ro.teamnet.bootstrap.reports.config;

/**
 * Contains constants to be used in various tests.
 *
 * @author Bogdan.Stefan
 * @version 1.0 Date: 2015-03-12
 */
public class ReportsTestConstants {

    public static final String REPORT_REQUEST_BODY_JSON_NO_FILTERS_AND_SORT = "{\n" +
            "\t\"metadata\" : {\n" +
            "\t\t\"title\" : \"Report title\",\n" +
            "\t\t\"fieldsAndTableColumnMetadata\" : { \n" +
            "\t\t\t\"firstName\" : \"Prenume\",\n" +
            "\t\t\t\"lastName\" : \"Nume\"\n" +
            "\t\t},\n" +
            "\t\t\"extraParametersMap\" : {\n" +
            "\t\t\t\"ReportinatorReportSubTitle\" : \"Subtitlu_Demo\"\n" +
            "\t\t}\n" +
            "\t}" +
            "}";

    public static final String REPORT_REQUEST_MALFORMED_BODY_JSON = "{\n" +
            "\t\"metadataa\" : {\n" +
            "\t\t\"title\" : \"Report title\",\n" +
            "\t\t\"fieldsAndTableColumnMetadata\" : {\n" +
            "\t\t\t\"firstName\" : \"Prenume\",\n" +
            "\t\t\t\"lastName\" : \"Nume\"\n" +
            "\t\t},\n" +
            "\t\t\"extraParametersMap\" : {\n" +
            "\t\t\t\"ReportinatorReportSubTitle\" : \"Subtitlu_Demo\"\n" +
            "\t\t}\n" +
            "\t}" +
            "}";

    public static final String REPORT_REQUEST_EMPTY_BODY_JSON = "";

    public static final String REPORT_REQUEST_BODY_JSON = "{\n" +
                    "\t\"metadata\" : {\n" +
                    "\t\t\"title\" : \"Report title\",\n" +
                    "\t\t\"fieldsAndTableColumnMetadata\" : {\n" +
                    "\t\t\t\"firstName\" : \"Prenume\",\n" +
                    "\t\t\t\"lastName\" : \"Nume\"\n" +
                    "\t\t},\n" +
                    "\t\t\"extraParametersMap\" : {\n" +
                    "\t\t\t\"ReportinatorReportSubTitle\" : \"Subtitlu_Demo\"\n" +
                    "\t\t}\n" +
                    "\t},\n" +
                    "\t\"filters\" : {\n" +
                    "        \"filters\": [\n" +
                    "            {\n" +
                    "                \"property\" : \"myObjectProperty\",\n" +
                    "                \"value\" : \"hello world\",\n" +
                    "                \"type\" : \"EQUAL\",\n" +
//                  "                \"negation\" : false,\n" +
//                  "                \"caseSensitive\" : false\n" +
                    "            }\n" +
                    "        ]\n" +
                    "\t},\n" +
                    "    \"sort\": {\n" +
                    "        \"orders\": [\n" +
                    "            {\n" +
                    "                \"direction\" : \"DESC\",\n" +
                    "                \"property\" : \"myObjectProperty\",\n" +
//                  "                \"ignoreCase\" : false,\n" +
                    "                \"nullHandling\" : \"NATIVE\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
}
