package au.com.lonsec.calculation;

import au.com.lonsec.description.DisplayReturnServiceLiteral;
import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
@Component
public class CalculationReturnSeriesHandler {

    @Autowired(required=false)
    private List<FundReturnSeriesCalculation> fundReturnSeriesCalculations = new ArrayList<>();

    public void addCalculatedValues(FundReturnSeries fundReturnSeries)
    {
        if (fundReturnSeriesCalculations != null)
        {
            for (FundReturnSeriesCalculation fundReturnSeriesCalculation :fundReturnSeriesCalculations)
            {
                fundReturnSeries.addCalculatedValue(fundReturnSeriesCalculation.getCalculationLabel(), fundReturnSeriesCalculation.getValue(fundReturnSeries));
            }
        }

    }
}
