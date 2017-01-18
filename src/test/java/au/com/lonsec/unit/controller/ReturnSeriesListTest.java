package au.com.lonsec.unit.controller;

import au.com.lonsec.controller.ReturnSeriesList;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.sort.FundReturnSeriesComparator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;

import static org.mockito.Mockito.*;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class ReturnSeriesListTest {
    @InjectMocks
    private ReturnSeriesList returnSeriesList;
    @Mock
    private FundReturnSeriesComparator fundReturnSeriesComparator;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAdd()
    {

        returnSeriesList.add(mock(FundReturnSeries.class));
        returnSeriesList.add(mock(FundReturnSeries.class));

        Assert.assertEquals(2, returnSeriesList.getFundReturnSeriesList().size());

    }
    @Test
    public void testSort()
    {
        //Set up a simple comparator
        Comparator<FundReturnSeries> comparator = (FundReturnSeries o1, FundReturnSeries o2) -> 0;
        when(fundReturnSeriesComparator.getComparator()).thenReturn(comparator);

        returnSeriesList.add(mock(FundReturnSeries.class));
        returnSeriesList.add(mock(FundReturnSeries.class));

        returnSeriesList.sort();

        verify(fundReturnSeriesComparator, times(1)).getComparator();

    }
    @Test
    public void testClear()
    {
        returnSeriesList.add(mock(FundReturnSeries.class));
        returnSeriesList.add(mock(FundReturnSeries.class));
        Assert.assertEquals(2, returnSeriesList.getFundReturnSeriesList().size());

        returnSeriesList.clear();

        Assert.assertEquals(0, returnSeriesList.getFundReturnSeriesList().size());

    }
}
