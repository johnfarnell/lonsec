package au.com.lonsec.unit.description;

import au.com.lonsec.calculation.CalculationReturnSeriesHandler;
import au.com.lonsec.calculation.FundReturnSeriesCalculation;
import au.com.lonsec.description.DisplayReturnSeriesHandler;
import au.com.lonsec.description.DisplayReturnServiceLiteral;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
public class DisplayReturnSeriesHandlerTest {
    private DisplayReturnSeriesHandler displayReturnSeriesHandler;
    @Test
    public void testAddDisplayValues()
    {
        displayReturnSeriesHandler = new DisplayReturnSeriesHandler();
        List<DisplayReturnServiceLiteral> displayReturnServiceLiterals = new ArrayList<>();

        //Set up and mock a couple of FundReturnSeriesCalculation in the list
        DisplayReturnServiceLiteral displayReturnServiceLiteral1 =  mock(DisplayReturnServiceLiteral.class);
        displayReturnServiceLiterals.add(displayReturnServiceLiteral1);
        DisplayReturnServiceLiteral displayReturnServiceLiteral2 =  mock(DisplayReturnServiceLiteral.class);
        displayReturnServiceLiterals.add(displayReturnServiceLiteral2);

        //Assign this list to the target test instance of displayReturnSeriesHandler
        ReflectionTestUtils.setField(displayReturnSeriesHandler, "displayReturnServiceLiterals",
                displayReturnServiceLiterals);

        //Create the mock of the FundReturnSeries to be supplied to the test instance
        FundReturnSeries fundReturnSeries = mock(FundReturnSeries.class);

        //Mock what will happen
        when(displayReturnServiceLiteral1.getValue(fundReturnSeries)).thenReturn("Value1");
        doNothing().when(fundReturnSeries).addDisplayValue("DisplayLabel1", "Value1");
        when(displayReturnServiceLiteral1.getDisplayLabel()).thenReturn("DisplayLabel1");

        when(displayReturnServiceLiteral2.getValue(fundReturnSeries)).thenReturn("Value2");
        doNothing().when(fundReturnSeries).addDisplayValue("DisplayLabel2", "Value2");
        when(displayReturnServiceLiteral2.getDisplayLabel()).thenReturn("DisplayLabel2");

        displayReturnSeriesHandler.addDisplayValues(fundReturnSeries);

        //Verify what was mocked above actually occurreed
        verify(displayReturnServiceLiteral1, times(1)).getDisplayLabel();
        verify(displayReturnServiceLiteral2, times(1)).getDisplayLabel();

        verify(displayReturnServiceLiteral1, times(1)).getValue(fundReturnSeries);
        verify(displayReturnServiceLiteral2, times(1)).getValue(fundReturnSeries);

        verify(fundReturnSeries, times(1)).addDisplayValue("DisplayLabel1", "Value1");
        verify(fundReturnSeries, times(1)).addDisplayValue("DisplayLabel2", "Value2");

   }
    @Test
    public void testHandlesNullList() {
        //Assign NULL
        displayReturnSeriesHandler = new DisplayReturnSeriesHandler();
        ReflectionTestUtils.setField(displayReturnSeriesHandler, "displayReturnServiceLiterals"
                , null, List.class);
        FundReturnSeries fundReturnSeries = mock(FundReturnSeries.class);
        displayReturnSeriesHandler.addDisplayValues(fundReturnSeries);
    }

}
