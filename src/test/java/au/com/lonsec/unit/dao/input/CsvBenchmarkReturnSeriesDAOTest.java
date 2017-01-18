package au.com.lonsec.unit.dao.input;
import au.com.lonsec.dao.input.*;
import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.BenchmarkReturnSeriesFactory;
import au.com.lonsec.unit.CalendarHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class CsvBenchmarkReturnSeriesDAOTest {
    @InjectMocks
    private CsvBenchmarkReturnSeriesDAO csvBenchmarkReturnSeriesDAO;
    @Mock
    private BenchmarkDAO benchmarkDAO;
    @Mock
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;

    //Don't mock this
    private BenchmarkReturnSeriesFactory benchmarkReturnSeriesFactory;


    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBenchmarkReturnSeries()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getBrsFileName()).thenReturn("BenchReturnSeries.csv");

        benchmarkReturnSeriesFactory = new BenchmarkReturnSeriesFactory();
        ReflectionTestUtils.setField(benchmarkReturnSeriesFactory, "csvFundReturnSeriesInputProperties",
                csvFundReturnSeriesInputProperties);
        when(csvFundReturnSeriesInputProperties.getBrsDatePattern()).thenReturn("yyyy-MM-dd");
        ReflectionTestUtils.setField(csvBenchmarkReturnSeriesDAO, "benchmarkReturnSeriesFactory",
                benchmarkReturnSeriesFactory);

        // BM-18,2016-08-31,0.42006038
        Calendar cal = CalendarHelper.getCalendar(2016, Calendar.AUGUST, 31);

        Benchmark benchmarkBM18 = new Benchmark("BM-18", "BM-18-Name");
        Benchmark benchmarkBM672= new Benchmark("BM-672", "BM-672-Name");

        when(benchmarkDAO.getBenchmark("BM-18")).thenReturn(benchmarkBM18);
        when(benchmarkDAO.getBenchmark("BM-672")).thenReturn(benchmarkBM672);


        BenchmarkReturnSeries brs = csvBenchmarkReturnSeriesDAO.getBenchmarkReturnSeries(benchmarkBM18, cal.getTime());
        Assert.assertNotNull(brs);
        Assert.assertEquals("BM-18", brs.getBenchmark().getBenchmarkCode());
        Assert.assertEquals(cal.getTime(), brs.getDate());
        Assert.assertEquals(new BigDecimal("0.42006038"), brs.getReturnPercentage());


    }

    @Test(expected=FundReturnException.class)
    public void testGetBenchmarkReturnSeriesThowsExceptionWhenBenchmarkCodeDoesNotExist()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getBrsFileName()).thenReturn("BenchReturnSeries.csv");

        benchmarkReturnSeriesFactory = new BenchmarkReturnSeriesFactory();
        ReflectionTestUtils.setField(benchmarkReturnSeriesFactory, "csvFundReturnSeriesInputProperties",
                csvFundReturnSeriesInputProperties);
        when(csvFundReturnSeriesInputProperties.getBrsDatePattern()).thenReturn("yyyy-MM-dd");
        ReflectionTestUtils.setField(csvBenchmarkReturnSeriesDAO, "benchmarkReturnSeriesFactory",
                benchmarkReturnSeriesFactory);

        // BM-18,2016-08-31,0.42006038
        Calendar cal = CalendarHelper.getCalendar(2016, Calendar.AUGUST, 31);

        Benchmark benchmarkBM18 = new Benchmark("BM-18", "BM-18-Name");

        when(benchmarkDAO.getBenchmark("BM-18")).thenReturn(benchmarkBM18);

        //Mock a null return
        when(benchmarkDAO.getBenchmark("BM-672")).thenReturn(null);


        csvBenchmarkReturnSeriesDAO.getBenchmarkReturnSeries(benchmarkBM18, cal.getTime());

    }

    @Test(expected=FundReturnException.class)
    public void testExceptionIsThrownWhenNonExistentFileIsSupplied()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getBrsFileName()).thenReturn("doesnotexist.csv");
        Benchmark benchmarkBM18 = new Benchmark("BM-18", "BM-18-Name");
        csvBenchmarkReturnSeriesDAO.getBenchmarkReturnSeries(benchmarkBM18, new Date());
    }


}
