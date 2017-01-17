package au.com.lonsec.calculation;

import au.com.lonsec.domain.FundReturnSeries;

import java.math.BigDecimal;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
public interface FundReturnSeriesCalculation {
    BigDecimal getValue(FundReturnSeries fundReturnSeries);
    String getCalculationLabel();
}
