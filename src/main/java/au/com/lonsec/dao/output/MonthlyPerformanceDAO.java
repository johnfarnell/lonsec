package au.com.lonsec.dao.output;

import au.com.lonsec.domain.FundReturnSeries;

import java.util.List;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public interface MonthlyPerformanceDAO {
    void writeDetails(List<FundReturnSeries> fundReturnSeriesList);
}
