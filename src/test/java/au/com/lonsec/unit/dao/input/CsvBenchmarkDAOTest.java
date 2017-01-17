package au.com.lonsec.unit.dao.input;

import au.com.lonsec.dao.input.CSVFundReturnSeriesProperties;
import au.com.lonsec.dao.input.CsvBenchmarkDAO;
import au.com.lonsec.domain.Benchmark;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("csvinput")
public class CsvBenchmarkDAOTest {
    @InjectMocks
    private CsvBenchmarkDAO csvBenchmarkDAO;
    @Mock
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetBenchmarkIsSuccessfulWhenCodeExists()
    {
        when(csvFundReturnSeriesInputProperties.getFolder()).thenReturn("./input/");
        when(csvFundReturnSeriesInputProperties.getBenchmarkFileName()).thenReturn("benchmark.csv");
        Benchmark benchMark = csvBenchmarkDAO.getBenchmark("BM-18");

        assertNotNull(benchMark);
        assertEquals("BM-18", benchMark.getBenchmarkCode());
    }

}
