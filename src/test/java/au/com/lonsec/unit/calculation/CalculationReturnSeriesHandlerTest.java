package au.com.lonsec.unit.calculation;
import au.com.lonsec.calculation.CalculateReturnPercentageDifferenceRaw;
import au.com.lonsec.calculation.CalculationReturnSeriesHandler;
import au.com.lonsec.calculation.FundReturnSeriesCalculation;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
public class CalculationReturnSeriesHandlerTest {
    private CalculationReturnSeriesHandler calculationReturnSeriesHandler;

    @Test
    public void testAddCalculatedValues() {
        calculationReturnSeriesHandler = new CalculationReturnSeriesHandler();
        List<FundReturnSeriesCalculation> fundReturnSeriesCalculations = new ArrayList<>();

        //Set up and mock a couple of FundReturnSeriesCalculation in the list
        FundReturnSeriesCalculation fundReturnSeriesCalculation1 = mock(FundReturnSeriesCalculation.class);
        fundReturnSeriesCalculations.add(fundReturnSeriesCalculation1);
        FundReturnSeriesCalculation fundReturnSeriesCalculation2 = mock(FundReturnSeriesCalculation.class);
        fundReturnSeriesCalculations.add(fundReturnSeriesCalculation2);

        //Assign this list to the target test instance of calculationReturnSeriesHandler
        ReflectionTestUtils.setField(calculationReturnSeriesHandler, "fundReturnSeriesCalculations",
                fundReturnSeriesCalculations);

        //Create the mock of the FundReturnSeries to be supplied to the test instance
        FundReturnSeries fundReturnSeries = mock(FundReturnSeries.class);

        //Mock what will happen
        when(fundReturnSeriesCalculation1.getValue(fundReturnSeries)).thenReturn(new BigDecimal("3.445"));
        doNothing().when(fundReturnSeries).addCalculatedValue("CalculationLabel1", new BigDecimal("3.445"));
        when(fundReturnSeriesCalculation1.getCalculationLabel()).thenReturn("CalculationLabel1");

        when(fundReturnSeriesCalculation2.getValue(fundReturnSeries)).thenReturn(new BigDecimal("4.445"));
        doNothing().when(fundReturnSeries).addCalculatedValue("CalculationLabel2", new BigDecimal("4.445"));
        when(fundReturnSeriesCalculation2.getCalculationLabel()).thenReturn("CalculationLabel2");

        calculationReturnSeriesHandler.addCalculatedValues(fundReturnSeries);

        //Verify what was mocked above actually occurreed
        verify(fundReturnSeriesCalculation1, times(1)).getCalculationLabel();
        verify(fundReturnSeriesCalculation2, times(1)).getCalculationLabel();

        verify(fundReturnSeriesCalculation1, times(1)).getValue(fundReturnSeries);
        verify(fundReturnSeriesCalculation2, times(1)).getValue(fundReturnSeries);

        verify(fundReturnSeries, times(1)).addCalculatedValue("CalculationLabel1", new BigDecimal("3.445"));
        verify(fundReturnSeries, times(1)).addCalculatedValue("CalculationLabel2", new BigDecimal("4.445"));
    }

    @Test
    public void testHandlesNullList() {
        //Assign NULL
        calculationReturnSeriesHandler = new CalculationReturnSeriesHandler();
        ReflectionTestUtils.setField(calculationReturnSeriesHandler, "fundReturnSeriesCalculations"
                , null, List.class);
        FundReturnSeries fundReturnSeries = mock(FundReturnSeries.class);
        calculationReturnSeriesHandler.addCalculatedValues(fundReturnSeries);
    }
}