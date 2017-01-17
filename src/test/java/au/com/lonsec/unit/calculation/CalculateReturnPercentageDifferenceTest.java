package au.com.lonsec.unit.calculation;

import au.com.lonsec.calculation.CalculateReturnPercentageDifference;
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
public class CalculateReturnPercentageDifferenceTest {

    @InjectMocks
    private CalculateReturnPercentageDifference calculateReturnPercentageDifference;
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
    public void testRoundsUpWhenGTPoint445()
    {
        when(fundReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("2.67834"));
        when(fundReturnSeries.getBenchmarkReturnSeries()).thenReturn(benchmarkReturnSeries);
        when(benchmarkReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("1.2331"));
        ReflectionTestUtils.setField(calculateReturnPercentageDifference, "decimalPlaces",
                2);
        BigDecimal actualValue = calculateReturnPercentageDifference.getValue(fundReturnSeries);
        assertEquals(new BigDecimal("1.45"),actualValue);

    }
    @Test
    public void testRoundsDownWhenEqPoint445()
    {
        when(fundReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("2.678"));
        when(fundReturnSeries.getBenchmarkReturnSeries()).thenReturn(benchmarkReturnSeries);
        when(benchmarkReturnSeries.getReturnPercentage()).thenReturn(new BigDecimal("1.233"));
        ReflectionTestUtils.setField(calculateReturnPercentageDifference, "decimalPlaces",
                2);
        BigDecimal actualValue = calculateReturnPercentageDifference.getValue(fundReturnSeries);
        assertEquals(new BigDecimal("1.44"),actualValue);

    }
    @Test
    public void testLiteral()
    {
        ReflectionTestUtils.setField(calculateReturnPercentageDifference, "literal",
                "xyz");
        String actualValue = calculateReturnPercentageDifference.getCalculationLabel();
        assertEquals("xyz",actualValue);

    }
}
