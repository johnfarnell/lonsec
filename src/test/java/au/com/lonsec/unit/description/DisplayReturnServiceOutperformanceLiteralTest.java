package au.com.lonsec.unit.description;

import au.com.lonsec.calculation.CalculateReturnPercentageDifference;
import au.com.lonsec.description.DisplayReturnServiceOutperformanceLiteral;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class DisplayReturnServiceOutperformanceLiteralTest {
    @InjectMocks
    private DisplayReturnServiceOutperformanceLiteral displayReturnServiceOutperformanceLiteral;
    @Mock
    private CalculateReturnPercentageDifference calculateReturnPercentageDifference;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDisplayLabel()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");
        String actualValue = displayReturnServiceOutperformanceLiteral.getDisplayLabel();
        assertEquals("xyz",actualValue);
    }
    @Test
    public void testGetValueOverPerforms()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLimit",
                new BigDecimal("-2.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLimit",
                new BigDecimal("5.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLiteral",
                "overPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLiteral",
                "underPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "matchedPerformanceLiteral",
                "matchedPerformanceLiteral");

        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");

        FundReturnSeries frs = mock(FundReturnSeries.class);
        when(calculateReturnPercentageDifference.getValue(frs)).thenReturn(new BigDecimal("5.01"));
        String actualValue = displayReturnServiceOutperformanceLiteral.getValue(frs);
        assertEquals("overPerformanceLiteral",actualValue);
    }
    @Test
    public void testGetValueMatchesPerformanceWhenEQUpperLimit()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLimit",
                new BigDecimal("-2.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLimit",
                new BigDecimal("5.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLiteral",
                "overPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLiteral",
                "underPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "matchedPerformanceLiteral",
                "matchedPerformanceLiteral");

        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");

        FundReturnSeries frs = mock(FundReturnSeries.class);
        when(calculateReturnPercentageDifference.getValue(frs)).thenReturn(new BigDecimal("5.00"));
        String actualValue = displayReturnServiceOutperformanceLiteral.getValue(frs);
        assertEquals("matchedPerformanceLiteral",actualValue);
    }
    @Test
    public void testGetValueMatchesPerformanceWhenEQLowerLimit()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLimit",
                new BigDecimal("-2.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLimit",
                new BigDecimal("5.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLiteral",
                "overPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLiteral",
                "underPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "matchedPerformanceLiteral",
                "matchedPerformanceLiteral");

        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");

        FundReturnSeries frs = mock(FundReturnSeries.class);
        when(calculateReturnPercentageDifference.getValue(frs)).thenReturn(new BigDecimal("-2.00"));
        String actualValue = displayReturnServiceOutperformanceLiteral.getValue(frs);
        assertEquals("matchedPerformanceLiteral",actualValue);
    }
    @Test
    public void testGetValueMatchesPerformanceWhenBtwnLowerAndUpperLimit()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLimit",
                new BigDecimal("-2.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLimit",
                new BigDecimal("5.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLiteral",
                "overPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLiteral",
                "underPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "matchedPerformanceLiteral",
                "matchedPerformanceLiteral");

        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");

        FundReturnSeries frs = mock(FundReturnSeries.class);
        when(calculateReturnPercentageDifference.getValue(frs)).thenReturn(new BigDecimal("-1.00"));
        String actualValue = displayReturnServiceOutperformanceLiteral.getValue(frs);
        assertEquals("matchedPerformanceLiteral",actualValue);
    }
    @Test
    public void testGetValueUnderPerformanceWhenLTLowerLimit()
    {
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLimit",
                new BigDecimal("-2.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLimit",
                new BigDecimal("5.00"));
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "overPerformanceLiteral",
                "overPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "underPerformanceLiteral",
                "underPerformanceLiteral");
        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "matchedPerformanceLiteral",
                "matchedPerformanceLiteral");

        ReflectionTestUtils.setField(displayReturnServiceOutperformanceLiteral, "literal",
                "xyz");

        FundReturnSeries frs = mock(FundReturnSeries.class);
        when(calculateReturnPercentageDifference.getValue(frs)).thenReturn(new BigDecimal("-2.01"));
        String actualValue = displayReturnServiceOutperformanceLiteral.getValue(frs);
        assertEquals("underPerformanceLiteral",actualValue);
    }
}
