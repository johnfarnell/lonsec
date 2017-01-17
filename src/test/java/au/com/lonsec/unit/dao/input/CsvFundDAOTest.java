package au.com.lonsec.unit.dao.input;

import au.com.lonsec.dao.input.BenchmarkDAO;
import au.com.lonsec.dao.input.CSVFundReturnSeriesProperties;
import au.com.lonsec.dao.input.CsvFundDAO;
import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.Fund;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CsvFundDAOTest {
    @InjectMocks
    private CsvFundDAO csvFundDAO;
    @Mock
    private BenchmarkDAO benchmarkDAO;
    @Mock
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetFundIsSuccessfulWhenCodeExists()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getFundFileName()).thenReturn("fund.csv");
        when(benchmarkDAO.getBenchmark("BM-672")).thenReturn(mock(Benchmark.class));
        Fund fund = csvFundDAO.getFund("EF-2-21255");

        assertNotNull(fund);
        assertEquals("EF-2-21255", fund.getFundCode());
    }

}
