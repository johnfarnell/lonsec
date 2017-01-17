package au.com.lonsec.unit.sort;

import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.sort.SortByDateDescAndRankAsc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class SortByDateDescAndRankAscTest {
    @InjectMocks
    SortByDateDescAndRankAsc sortByDateDescAndRankAsc;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSortWhenDatesAreDifferent() throws Exception
    {
        Comparator<FundReturnSeries> comparator = sortByDateDescAndRankAsc.getComparator();

        //Set up 2 dates
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(cal1.getTime());
        //Set second calendar to a different date (e.g. a year later)
        cal2.set(Calendar.YEAR, cal1.get(Calendar.YEAR) +1);
        //Assign the dates to 2 FundReturnSeries instances
        FundReturnSeries frs1 = new FundReturnSeries(null, cal1.getTime(), null, null);
        FundReturnSeries frs2 = new FundReturnSeries(null, cal2.getTime(), null, null);

        //Create and add to a list
        List<FundReturnSeries> listToSort = new ArrayList<>();


        listToSort.add(frs1);
        listToSort.add(frs2);

        //Sort using the comparator
        listToSort.sort(comparator);

        //Expects the order to be switched as frs2 had a later date
        assertEquals(frs2, listToSort.get(0));
        assertEquals(frs1, listToSort.get(1));
    }
    @Test
    public void testSortWhenDatesAreTheSameButRankIsDifferent() throws Exception
    {
        Comparator<FundReturnSeries> comparator = sortByDateDescAndRankAsc.getComparator();

        //Set up 1 date
        Calendar cal1 = Calendar.getInstance();
        //Assign the date to both FundReturnSeries instances, but give the second a lower rank
        FundReturnSeries frs1 = new FundReturnSeries(null, cal1.getTime(),null, null);
        frs1.setRank(2);
        FundReturnSeries frs2 = new FundReturnSeries(null, cal1.getTime(), null, null);
        frs2.setRank(1);

        //Create and add to a list
        List<FundReturnSeries> listToSort = new ArrayList<>();


        listToSort.add(frs1);
        listToSort.add(frs2);

        //Sort using the comparator
        listToSort.sort(comparator);

        //Expects tje order to be switched as frs2 had a lower rank (1)
        assertEquals(frs2, listToSort.get(0));
        assertEquals(frs1, listToSort.get(1));
    }
}
