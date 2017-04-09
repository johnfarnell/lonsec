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
        int indexofColumn = columns.indexOf(csvMonthlyPerformanceProperties.getDateColumnName());
        if (indexofColumn > -1)
        {
            sortedMap.put(indexofColumn, csvMonthlyPerformanceProperties.getSdfDate().format(fundReturnSeries.getDate()));
        }
        indexofColumn = columns.indexOf(csvMonthlyPerformanceProperties.getFundNameColumnName());
        if (indexofColumn > -1)
        {
            sortedMap.put(indexofColumn, fundReturnSeries.getFund().getFundName());
        }

        indexofColumn = columns.indexOf(csvMonthlyPerformanceProperties.getReturnColumnName());
        if (indexofColumn > -1)
        {
            sortedMap.put(indexofColumn, csvMonthlyPerformanceProperties.getDecimalFormat().format(fundReturnSeries.getReturnPercentage()));
        }

        indexofColumn = columns.indexOf(csvMonthlyPerformanceProperties.getRankColumnName());
        if (indexofColumn > -1)
        {
            sortedMap.put(indexofColumn, String.valueOf(fundReturnSeries.getRank()));
        }

        /*
        Now get all the calculate and display columns
         */

        for (String column : columns)
        {
            indexofColumn = columns.indexOf(column);
            /*
            See if the column is associated with one of the display values
             */
            String value = fundReturnSeries.getDisplayValue(column);
            if (value != null)
            {
                sortedMap.put(indexofColumn, value);
                continue;
            }
            /*
            See if the column is associated with one of the calculated values
             */
            BigDecimal bdValue = fundReturnSeries.getCalculatedValue(column);
            if (bdValue != null)
            {
                sortedMap.put(indexofColumn, csvMonthlyPerformanceProperties.getDecimalFormat().format(bdValue));
                continue;
            }
        }

        return sortedMap.values().toArray(new String[0]);

    }
}
