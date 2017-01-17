package au.com.lonsec.dao.input;

import au.com.lonsec.domain.FundReturnSeries;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public interface FundReturnSeriesDAO {
    FundReturnSeries getFirstReturnSeries();
    FundReturnSeries getNextReturnSeries();
}
