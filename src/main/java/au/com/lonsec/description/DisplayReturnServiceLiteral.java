package au.com.lonsec.description;

import au.com.lonsec.domain.FundReturnSeries;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public interface DisplayReturnServiceLiteral {
    String getValue(FundReturnSeries fundReturnSeries);
    String getDisplayLabel();
}
