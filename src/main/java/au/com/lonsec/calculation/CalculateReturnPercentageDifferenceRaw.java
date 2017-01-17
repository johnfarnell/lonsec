package au.com.lonsec.calculation;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "calc.return.percent.diff.raw.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class CalculateReturnPercentageDifferenceRaw implements FundReturnSeriesCalculation{

    @Value("${calc.return.percent.diff.raw.label:Excess Raw}")
    private String literal;
    @Override
    public BigDecimal getValue(FundReturnSeries fundReturnSeries)
    {
        BigDecimal frsReturn = fundReturnSeries.getReturnPercentage();
        BigDecimal brsReturn = fundReturnSeries.getBenchmarkReturnSeries().getReturnPercentage();
        //Excess = [fund return (%)] - benchmark return (%)]
        return frsReturn.subtract(brsReturn);
    }

    @Override
    public String getCalculationLabel() {
        return literal;
    }
}
