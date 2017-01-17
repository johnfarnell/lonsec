package au.com.lonsec.rank;

import au.com.lonsec.domain.FundReturnSeries;

import java.util.List;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public interface FundReturnSeriesRank {
    void setRanking(List<FundReturnSeries> fundReturnSeriesList);
}
