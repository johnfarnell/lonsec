package au.com.lonsec.unit.calculation;

import au.com.lonsec.calculation.CalculateReturnPercentageDifferenceRaw;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public class CalculateReturnPercentageDifferenceRawTest {

    @InjectMocks
    private CalculateReturnPercentageDifferenceRaw calculateReturnPercentageDifferenceRaw;
    @Mock
    private FundReturnSeries fundReturnSeries;
    @Mock
    private BenchmarkReturnSeries benchmarkReturnSeries;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRawValueReturned()
    {
        when(fundReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("2.67834"));
        when(fundReturnSeries.getBenchmarkReturnSeries()).thenReturn(benchmarkReturnSeries);
        when(benchmarkReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("1.2331"));
        BigDecimal actualValue = calculateReturnPercentageDifferenceRaw.getValue(fundReturnSeries);
        assertEquals(new BigDecimal("1.44524"),actualValue);

    }
    @Test
    public void testLiteral()
    {
        ReflectionTestUtils.setField(calculateReturnPercentageDifferenceRaw, "literal",
                "xyz");
        String actualValue = calculateReturnPercentageDifferenceRaw.getCalculationLabel();
        assertEquals("xyz",actualValue);

    }
}
