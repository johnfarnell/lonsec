package au.com.lonsec.dao.output;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@Component
public class CsvMonthlyPerformanceProperties {

    @Value("#{'${csv.monthly.performance.columns}'.split(',')}")
    private List<String> columns;
    @Value("${csv.monthly.performance.fundname.col:FundName}")
    private String fundNameColumnName;
    @Value("${csv.monthly.performance.date.col:Date}")
    private String dateColumnName;
    @Value("${csv.monthly.performance.return.col:Return}")
    private String returnColumnName;
    @Value("${csv.monthly.performance.rank.col:Rank}")
    private String rankColumnName;
    @Value("${csv.monthly.performance.date.format:dd/MM/yyyy}")
    private SimpleDateFormat sdfDate;
    @Value("${csv.output.folder:./output/}")
    private String folder;
    @Value("${csv.monthly.performance.file-name:monthPerformance.csv}")
    private String monthPerformanceFileName;
    @Value("${csv.monthly.performance.decimal.format}")
    private DecimalFormat decimalFormat;

    public String[] getColumns() {

        return columns != null ? columns.toArray(new String[columns.size()]) : new String[]{};
    }

    public List<String> getColumnsAsList() {

        return columns;
    }

    public String getFundNameColumnName() {
        return fundNameColumnName;
    }

    public String getDateColumnName() {
        return dateColumnName;
    }

    public String getReturnColumnName() {
        return returnColumnName;
    }

    public String getRankColumnName() {
        return rankColumnName;
    }

    public SimpleDateFormat getSdfDate() {
        return sdfDate;
    }

    public String getFolder() {
        return folder;
    }

    public String getMonthPerformanceFileName() {
        return monthPerformanceFileName;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }


}
