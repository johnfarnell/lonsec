package au.com.lonsec.unit.rank;
import au.com.lonsec.calculation.CalculateReturnPercentageDifference;
import au.com.lonsec.rank.FundServiceRankByBestReturnForSameDate;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class FundServiceRankByBestReturnForSameDateTest {
    @InjectMocks
    FundServiceRankByBestReturnForSameDate fundServiceRankByBestReturnForSameDate;
    @Mock
    private CalculateReturnPercentageDifference calculateReturnPercentageDifference;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetRankingIsSuccessful() throws Exception
    {
        List<FundReturnSeries> fundReturnSeriesList = new ArrayList<>();
        //Set up 2 dates
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal2.setTime(cal1.getTime());
        cal2.set(Calendar.YEAR, cal1.get(Calendar.YEAR) -1);

        //Set up 4 FundReturnSeries mocks, they should be sorted in ascending date, descending return percentage order

        FundReturnSeries frs1 = Mockito.mock(FundReturnSeries.class);
        when(frs1.getDate()).thenReturn(cal1.getTime());
        when(frs1.getReturnPercentage()).thenReturn(new BigDecimal("3.45"));
        doNothing().when(frs1).setRank(2);

        FundReturnSeries frs2 = Mockito.mock(FundReturnSeries.class);
        when(frs2.getDate()).thenReturn(cal1.getTime());
        when(frs2.getReturnPercentage()).thenReturn(new BigDecimal("4.45"));
        doNothing().when(frs2).setRank(1);
        //Note change of date
        FundReturnSeries frs3 = Mockito.mock(FundReturnSeries.class);
        when(frs3.getDate()).thenReturn(cal2.getTime());
        when(frs3.getReturnPercentage()).thenReturn(new BigDecimal("3.45"));
        doNothing().when(frs3).setRank(2);

        FundReturnSeries frs4 = Mockito.mock(FundReturnSeries.class);
        when(frs4.getDate()).thenReturn(cal2.getTime());
        when(frs4.getReturnPercentage()).thenReturn(new BigDecimal("4.45"));
        doNothing().when(frs4).setRank(1);

        // Add to the list

        fundReturnSeriesList.add(frs1);
        fundReturnSeriesList.add(frs2);
        fundReturnSeriesList.add(frs3);
        fundReturnSeriesList.add(frs4);


        fundServiceRankByBestReturnForSameDate.setRanking(fundReturnSeriesList);

        //Verify the following setRank methods were called
        verify(frs1, times(1)).setRank(2);
        verify(frs2, times(1)).setRank(1);
        verify(frs3, times(1)).setRank(2);
        verify(frs4, times(1)).setRank(1);
    }
}
