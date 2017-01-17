package au.com.lonsec.sort;

import au.com.lonsec.domain.FundReturnSeries;

import java.util.Comparator;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public interface FundReturnSeriesComparator {

    Comparator<FundReturnSeries> getComparator();
}
