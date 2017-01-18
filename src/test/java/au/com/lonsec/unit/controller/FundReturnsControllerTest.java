package au.com.lonsec.unit.controller;

import au.com.lonsec.calculation.CalculationReturnSeriesHandler;
import au.com.lonsec.controller.FundReturnsController;
import au.com.lonsec.controller.ReturnSeriesList;
import au.com.lonsec.dao.input.FundReturnSeriesDAO;
import au.com.lonsec.dao.output.MonthlyPerformanceDAO;
import au.com.lonsec.description.DisplayReturnSeriesHandler;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.rank.FundReturnSeriesRank;
import au.com.lonsec.sort.FundReturnSeriesComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class FundReturnsControllerTest {
    @InjectMocks
    private FundReturnsController fundReturnsController;
    @Mock
    private FundReturnSeriesDAO fundReturnSeriesDAO;
    @Mock
    private MonthlyPerformanceDAO monthlyPerformanceDAO;
    @Mock
    private FundReturnSeriesRank fundReturnSeriesRank;
    @Mock
    private FundReturnSeriesComparator fundReturnSeriesComparator;
    @Mock
    private CalculationReturnSeriesHandler calculationReturnSeriesHandler;
    @Mock
    private DisplayReturnSeriesHandler displayReturnSeriesHandler;
    @Mock
    private ReturnSeriesList returnSeriesList;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testASuccessfulExecution()
    {
        //Mock 2 FundReturnSeries being supplied to the controller
        FundReturnSeries frs1 = mock(FundReturnSeries.class);
        when(fundReturnSeriesDAO.getFirstReturnSeries()).thenReturn(frs1);
        FundReturnSeries frs2 = mock(FundReturnSeries.class);
        when(fundReturnSeriesDAO.getNextReturnSeries()).thenReturn(frs2).thenReturn(null);

        doNothing().when(returnSeriesList).clear();
        //Mock the calls to  the calculationReturnSeriesHandler

        doNothing().when(calculationReturnSeriesHandler).addCalculatedValues(frs1);
        doNothing().when(calculationReturnSeriesHandler).addCalculatedValues(frs2);

        doNothing().when(displayReturnSeriesHandler).addDisplayValues(frs1);
        doNothing().when(displayReturnSeriesHandler).addDisplayValues(frs2);

        doNothing().when(returnSeriesList).add(frs1);
        doNothing().when(returnSeriesList).add(frs2);

        List<FundReturnSeries> fundReturnSeriesList = new ArrayList<>();
        when(returnSeriesList.getFundReturnSeriesList()).thenReturn(fundReturnSeriesList);

        doNothing().when(fundReturnSeriesRank).setRanking(fundReturnSeriesList);

        List<FundReturnSeries> sortedFundReturnSeriesList = new ArrayList<>();
        when(returnSeriesList.sort()).thenReturn(sortedFundReturnSeriesList);

        doNothing().when(monthlyPerformanceDAO).writeDetails(sortedFundReturnSeriesList);


        fundReturnsController.execute();

        //Verify the correct activity took place

        verify(returnSeriesList, times(1)).clear();
        verify(fundReturnSeriesDAO, times(1)).getFirstReturnSeries();
        verify(fundReturnSeriesDAO, times(2)).getNextReturnSeries();

        verify(returnSeriesList, times(1)).add(frs1);
        verify(calculationReturnSeriesHandler, times(1)).addCalculatedValues(frs1);
        verify(displayReturnSeriesHandler, times(1)).addDisplayValues(frs1);

        verify(returnSeriesList, times(1)).add(frs2);
        verify(calculationReturnSeriesHandler, times(1)).addCalculatedValues(frs2);
        verify(displayReturnSeriesHandler, times(1)).addDisplayValues(frs2);

        verify(returnSeriesList, times(1)).getFundReturnSeriesList();
        verify(returnSeriesList, times(1)).sort();

        verify(fundReturnSeriesRank, times(1)).setRanking(fundReturnSeriesList);
//        verify(fundReturnSeriesComparator, times(1)).getComparator();

        //This last one is important monthlyPerformanceDAO MUST use the sortedFundReturnSeriesList
        verify(monthlyPerformanceDAO, times(1)).writeDetails(sortedFundReturnSeriesList);
    }
}
