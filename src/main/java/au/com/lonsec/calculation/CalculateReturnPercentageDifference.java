package au.com.lonsec.calculation;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "calc.return.percent.diff.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class CalculateReturnPercentageDifference implements FundReturnSeriesCalculation{

    @Value("${calc.return.percent.diff.dec.places:2}")
    private int decimalPlaces;
    @Value("${calc.return.percent.diff.label}")
    private String literal;
    @Override
    public BigDecimal getValue(FundReturnSeries fundReturnSeries)
    {
        BigDecimal frsReturn_trial2 = fundReturnSeries.getReturnPercentage();
        BigDecimal brsReturn = fundReturnSeries.getBenchmarkReturnSeries().getReturnPercentage();
        //Excess = [fund return (%)] - benchmark return (%)]
        return frsReturn_trial2.subtract(brsReturn).setScale(decimalPlaces, RoundingMode.HALF_DOWN);
    }

    @Override
    public String getCalculationLabel() {
        return literal;
    }
}
