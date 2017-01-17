package au.com.lonsec.dao.output;

import au.com.bytecode.opencsv.CSVWriter;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import jdk.nashorn.internal.runtime.ECMAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@Component
public class CsvMonthPerformanceDAO implements MonthlyPerformanceDAO
{
    @Autowired
    private CsvMonthlyPerformanceProperties csvMonthlyPerformanceProperties;


    public void setHeaders(List<FundReturnSeries> fundReturnSeriesList)
    {
        CSVWriter csvWriter = null;
        try
        {
            try
            {
                csvWriter = new CSVWriter(new FileWriter(csvMonthlyPerformanceProperties.getFolder() + csvMonthlyPerformanceProperties.getMonthPerformanceFileName())
                        , ',', '"');
                csvWriter.writeNext(csvMonthlyPerformanceProperties.getColumns());

                for (FundReturnSeries fundReturnSeries :fundReturnSeriesList)
                {
                    String[] values = getValues(fundReturnSeries, csvMonthlyPerformanceProperties.getColumnsAsList());
                    csvWriter.writeNext(values);
                }
            }
            finally
            {
                if (csvWriter != null)
                {
                    csvWriter.close();;
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