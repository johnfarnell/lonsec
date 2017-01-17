package au.com.lonsec.sort;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "sort.return.percent", havingValue = "default", matchIfMissing = true)
@Component
public class SortByDateDescAndRankAsc implements FundReturnSeriesComparator
{

    public SortByDateDescAndRankAsc()
    {
        super();
    }
    public Comparator<FundReturnSeries> getComparator() {
        return (o1, o2) -> {
            int index = o2.getDate().compareTo(o1.getDate());
            if (index == 0) {
                index = o1.getRank() - o2.getRank();
            }

            return index;
        };
    }
}
