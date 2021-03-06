package au.com.lonsec.unit.dao.output;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.dao.output.CsvMonthPerformanceDAO;
import au.com.lonsec.dao.output.CsvMonthlyPerformanceProperties;
import au.com.lonsec.domain.Fund;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CsvMonthPerformanceDAOTest {
    @InjectMocks
    private CsvMonthPerformanceDAO csvMonthPerformanceDAO;
    @Mock
    private CsvMonthlyPerformanceProperties csvMonthlyPerformanceProperties;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWriteDetails() throws IOException {
        List<String> columnsAsList = new ArrayList<>();
        columnsAsList.add("FundName");
        columnsAsList.add("Date");
        columnsAsList.add("Return");
        columnsAsList.add("Rank");

        String[] columns = columnsAsList.toArray(new String[]{});

        when(csvMonthlyPerformanceProperties.getFolder()).thenReturn("./output/");
        when(csvMonthlyPerformanceProperties.getMonthPerformanceFileName()).thenReturn("CsvMonthPerformanceDAOTestOutperformance.csv");
        when(csvMonthlyPerformanceProperties.getColumns()).thenReturn(columns);
        when(csvMonthlyPerformanceProperties.getColumnsAsList()).thenReturn(columnsAsList);
        when(csvMonthlyPerformanceProperties.getDateColumnName()).thenReturn("Date");
        when(csvMonthlyPerformanceProperties.getReturnColumnName()).thenReturn("Return");
        when(csvMonthlyPerformanceProperties.getFundNameColumnName()).thenReturn("FundName");
        when(csvMonthlyPerformanceProperties.getRankColumnName()).thenReturn("Rank");


        FundReturnSeries frs1 = mock(FundReturnSeries.class);
        Fund fund1 = mock(Fund.class);
        when(frs1.getFund()).thenReturn(fund1);
        when(fund1.getFundName()).thenReturn("fund1");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,2010);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 15);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        when(frs1.getDate()).thenReturn(cal.getTime());
        when(csvMonthlyPerformanceProperties.getSdfDate()).thenReturn(sdfDate);

        DecimalFormat dcf = new DecimalFormat("#,##0.00");
        BigDecimal returnBD = new BigDecimal("2.456");
        when(frs1.getReturnPercentage()).thenReturn(returnBD);
        when(csvMonthlyPerformanceProperties.getDecimalFormat()).thenReturn(dcf);

        when(frs1.getRank()).thenReturn(2);



        List<FundReturnSeries> fundReturnSeriesList = new ArrayList<>();
        fundReturnSeriesList.add(frs1);
        csvMonthPerformanceDAO.writeDetails(fundReturnSeriesList);

        CSVReader reader = null;
        try
        {
            reader = new CSVReader(new FileReader(csvMonthlyPerformanceProperties.getFolder()
                    + csvMonthlyPerformanceProperties.getMonthPerformanceFileName()), ',', '"', 0);
            String[] header = reader.readNext();
            Assert.assertEquals("FundName", header[0]);
            Assert.assertEquals("Date", header[1]);
            Assert.assertEquals("Return", header[2]);
            Assert.assertEquals("Rank", header[3]);
            String[] line = reader.readNext();
            Assert.assertEquals("fund1", line[0]);
            Assert.assertEquals("15/03/2010", line[1]);
            Assert.assertEquals("2.46", line[2]);
            Assert.assertEquals("2", line[3]);
        }
        finally
        {
            reader.close();
        }

    }

    @Test(expected=FundReturnException.class)
    public void testExceptionIsThrownWhenNonExistentFolderIsSupplied()
    {
        when(csvMonthlyPerformanceProperties.getFolder()).thenReturn("zzz:///");
        when(csvMonthlyPerformanceProperties.getMonthPerformanceFileName()).thenReturn("xyz.csv");
        List<FundReturnSeries> fundReturnSeriesList = new ArrayList<>();
        csvMonthPerformanceDAO.writeDetails(fundReturnSeriesList);
    }

}
