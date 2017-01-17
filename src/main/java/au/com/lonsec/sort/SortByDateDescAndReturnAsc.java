package au.com.lonsec.sort;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@Component
@ConditionalOnProperty(name = "sort.return.percent", havingValue = "DateDescReturnAsc")
public class SortByDateDescAndReturnAsc implements FundReturnSeriesComparator
{

    public Comparator<FundReturnSeries> getComparator()
    {
        return Comparator.comparing(FundReturnSeries::getDate, Collections.reverseOrder()).thenComparing(FundReturnSeries::getReturnPercentage);
    }
}
