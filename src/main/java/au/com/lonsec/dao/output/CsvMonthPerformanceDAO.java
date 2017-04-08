package au.com.lonsec.dao.output;

import au.com.bytecode.opencsv.CSVWriter;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "csv.monthly.performance.dao", havingValue = "default", matchIfMissing = true)
@Component
public class CsvMonthPerformanceDAO implements MonthlyPerformanceDAO
{
    @Autowired
    private CsvMonthlyPerformanceProperties csvMonthlyPerformanceProperties;


    public void writeDetails(List<FundReturnSeries> fundReturnSeriesList)
    {
        CSVWriter csvWriter1 = null;
        try
        {
            try
            {
                csvWriter1 = new CSVWriter(new FileWriter(csvMonthlyPerformanceProperties.getFolder() + csvMonthlyPerformanceProperties.getMonthPerformanceFileName())
                        , ',', '"');
                csvWriter1.writeNext(csvMonthlyPerformanceProperties.getColumns());

                for (FundReturnSeries fundReturnSeries :fundReturnSeriesList)
                {
                    String[] values = getValues(fundReturnSeries, csvMonthlyPerformanceProperties.getColumnsAsList());
                    csvWriter1.writeNext(values);
                }
            }
            finally
            {
                if (csvWriter1 != null)
                {
                    csvWriter1.close();;
                }
            }
        }
        catch (Exception e)
        {
            throw new FundReturnException(e);

        }

    }

    private String[] getValues(FundReturnSeries fundReturnSeries, List<String> columns)
    {
        Map<Integer, String> sortedMap = new TreeMap<>();
        int iColumn = columns.indexOf(csvMonthlyPerformanceProperties.getDateColumnName());
        if (iColumn > -1)
        {
            sortedMap.put(iColumn, csvMonthlyPerformanceProperties.getSdfDate().format(fundReturnSeries.getDate()));
        }
        iColumn = columns.indexOf(csvMonthlyPerformanceProperties.getFundNameColumnName());
        if (iColumn > -1)
        {
            sortedMap.put(iColumn, fundReturnSeries.getFund().getFundName());
        }

        iColumn = columns.indexOf(csvMonthlyPerformanceProperties.getReturnColumnName());
        if (iColumn > -1)
        {
            sortedMap.put(iColumn, csvMonthlyPerformanceProperties.getDecimalFormat().format(fundReturnSeries.getReturnPercentage()));
        }

        iColumn = columns.indexOf(csvMonthlyPerformanceProperties.getRankColumnName());
        if (iColumn > -1)
        {
            sortedMap.put(iColumn, String.valueOf(fundReturnSeries.getRank()));
        }

        /*
        Now get all the calculate and display columns
         */

        for (String column : columns)
        {
            iColumn = columns.indexOf(column);
            /*
            See if the column is associated with one of the display values
             */
            String value = fundReturnSeries.getDisplayValue(column);
            if (value != null)
            {
                sortedMap.put(iColumn, value);
                continue;
            }
            /*
            See if the column is associated with one of the calculated values
             */
            BigDecimal bdValue = fundReturnSeries.getCalculatedValue(column);
            if (bdValue != null)
            {
                sortedMap.put(iColumn, csvMonthlyPerformanceProperties.getDecimalFormat().format(bdValue));
                continue;
            }
        }

        return sortedMap.values().toArray(new String[0]);

    }
}
