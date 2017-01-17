package au.com.lonsec.sort;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "sort.return.percent", havingValue = "DateAscReturnDesc")
@Component
public class SortByDateAscAndReturnDesc implements FundReturnSeriesComparator
{

    public SortByDateAscAndReturnDesc()
    {
        super();
    }
    public Comparator<FundReturnSeries> getComparator() {
        return (o1, o2) -> {
            int index = o1.getDate().compareTo(o2.getDate());
            if (index == 0) {
                index = o2.getReturnPercentage().compareTo(o1.getReturnPercentage());
            }

            return index;
        };
    }
}
