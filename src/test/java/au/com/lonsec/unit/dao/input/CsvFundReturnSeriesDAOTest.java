package au.com.lonsec.unit.dao.input;

import au.com.lonsec.dao.input.BenchmarkReturnSeriesDAO;
import au.com.lonsec.dao.input.CsvFundReturnSeriesDAO;
import au.com.lonsec.dao.input.CsvFundReturnSeriesProperties;
import au.com.lonsec.dao.input.FundDAO;
import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.Fund;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.FundReturnSeriesFactory;
import au.com.lonsec.unit.CalendarHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
public class CsvFundReturnSeriesDAOTest {
    @InjectMocks
    private CsvFundReturnSeriesDAO csvFundReturnSeriesDAO;
    @Mock
    private FundDAO fundDAO;
    @Mock
    private BenchmarkReturnSeriesDAO benchmarkReturnSeriesDAO;
    @Mock
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;

    //Don't mock this
    private FundReturnSeriesFactory fundReturnSeriesFactory;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFirstAndMultipleNextCalls()
    {
      /*  MF-1-4220,30/06/2016,-4.229084292
        MF-1-4220,31/07/2016,8.067664911
        EF-2-21255,30/06/2016,-3.575406872
        EF-2-21255,31/07/2016,2.157211437 */
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getFrsFileName()).thenReturn("FundReturnSeriesForTesting.csv");

        fundReturnSeriesFactory = new FundReturnSeriesFactory();
        ReflectionTestUtils.setField(fundReturnSeriesFactory, "csvFundReturnSeriesInputProperties",
                csvFundReturnSeriesInputProperties);
        ReflectionTestUtils.setField(csvFundReturnSeriesDAO, "fundReturnSeriesFactory",
                fundReturnSeriesFactory);
        when(csvFundReturnSeriesInputProperties.getFrsDatePattern()).thenReturn("dd/MM/yyyy");

        Benchmark bm1 = new Benchmark("Bm1", "BmName1");
        Fund fund1 = new Fund("fund1", "fundName1", bm1);
        when(fundDAO.getFund("MF-1-4220")).thenReturn(fund1);

        Benchmark bm2 = new Benchmark("Bm2", "BmName2");
        Fund fund2 = new Fund("fund2", "fundName2", bm2);
        when(fundDAO.getFund("EF-2-21255")).thenReturn(fund2);

        Calendar calFRS1 = CalendarHelper.getCalendar(2016, Calendar.JUNE, 30);
        Calendar calFRS2 = CalendarHelper.getCalendar(2016, Calendar.JULY, 31);
        BenchmarkReturnSeries benchmarkReturnSeries1 = new BenchmarkReturnSeries(bm1, calFRS1.getTime(), new BigDecimal("2.00"));
        BenchmarkReturnSeries benchmarkReturnSeries2 = new BenchmarkReturnSeries(bm1, calFRS2.getTime(), new BigDecimal("3.00"));
        BenchmarkReturnSeries benchmarkReturnSeries3 = new BenchmarkReturnSeries(bm1, calFRS1.getTime(), new BigDecimal("4.00"));
        BenchmarkReturnSeries benchmarkReturnSeries4 = new BenchmarkReturnSeries(bm1, calFRS2.getTime(), new BigDecimal("5.00"));
        when(benchmarkReturnSeriesDAO.getBenchmarkReturnSeries(bm1, calFRS1.getTime())).thenReturn(benchmarkReturnSeries1);
        when(benchmarkReturnSeriesDAO.getBenchmarkReturnSeries(bm1, calFRS2.getTime())).thenReturn(benchmarkReturnSeries2);
        when(benchmarkReturnSeriesDAO.getBenchmarkReturnSeries(bm2, calFRS1.getTime())).thenReturn(benchmarkReturnSeries3);
        when(benchmarkReturnSeriesDAO.getBenchmarkReturnSeries(bm2, calFRS2.getTime())).thenReturn(benchmarkReturnSeries4);

        //Read the 4 rows in FundReturnSeriesForTesting.csv
        FundReturnSeries frs = csvFundReturnSeriesDAO.getFirstReturnSeries();
        assertNotNull(frs);
        assertEquals(calFRS1.getTime(), frs.getDate());
        assertEquals(benchmarkReturnSeries1, frs.getBenchmarkReturnSeries());
        assertEquals(new BigDecimal("-4.229084292"), frs.getReturnPercentage());
        assertEquals(fund1, frs.getFund());

        frs = csvFundReturnSeriesDAO.getNextReturnSeries();
        assertNotNull(frs);
        assertEquals(calFRS2.getTime(), frs.getDate());
        assertEquals(benchmarkReturnSeries2, frs.getBenchmarkReturnSeries());
        assertEquals(new BigDecimal("8.067664911"), frs.getReturnPercentage());
        assertEquals(fund1, frs.getFund());

        frs = csvFundReturnSeriesDAO.getNextReturnSeries();
        assertNotNull(frs);
        assertEquals(calFRS1.getTime(), frs.getDate());
        assertEquals(benchmarkReturnSeries3, frs.getBenchmarkReturnSeries());
        assertEquals(new BigDecimal("-3.575406872"), frs.getReturnPercentage());
        assertEquals(fund2, frs.getFund());

        frs = csvFundReturnSeriesDAO.getNextReturnSeries();
        assertNotNull(frs);
        assertEquals(calFRS2.getTime(), frs.getDate());
        assertEquals(benchmarkReturnSeries4, frs.getBenchmarkReturnSeries());
        assertEquals(new BigDecimal("2.157211437"), frs.getReturnPercentage());
        assertEquals(fund2, frs.getFund());

        FundReturnSeries end = csvFundReturnSeriesDAO.getNextReturnSeries();
        assertNull(end);

    }

    @Test(expected=FundReturnException.class)
    public void testExceptionIsThrownWhenFundDoesNotExist()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getFrsFileName()).thenReturn("FundReturnSeriesForTesting.csv");

        fundReturnSeriesFactory = new FundReturnSeriesFactory();
        ReflectionTestUtils.setField(fundReturnSeriesFactory, "csvFundReturnSeriesInputProperties",
                csvFundReturnSeriesInputProperties);
        ReflectionTestUtils.setField(csvFundReturnSeriesDAO, "fundReturnSeriesFactory",
                fundReturnSeriesFactory);
        when(csvFundReturnSeriesInputProperties.getFrsDatePattern()).thenReturn("dd/MM/yyyy");

        when(fundDAO.getFund(any(String.class))).thenReturn(null);


        //Read the 4 rows in FundReturnSeriesForTesting.csv
        csvFundReturnSeriesDAO.getFirstReturnSeries();
    }

    @Test(expected=FundReturnException.class)
    public void testExceptionIsThrownWhenNonExistentFileIsSupplied()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getFrsFileName()).thenReturn("doesnotexist.csv");
        csvFundReturnSeriesDAO.getFirstReturnSeries();
    }

}
