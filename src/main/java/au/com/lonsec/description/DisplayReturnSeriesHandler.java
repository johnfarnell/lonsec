package au.com.lonsec.description;

import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
@Component
public class DisplayReturnSeriesHandler {
    @Autowired(required=false)
    private List<DisplayReturnServiceLiteral> displayReturnServiceLiterals ;
    public void addDisplayValues(FundReturnSeries fundReturnSeries) {
        if (displayReturnServiceLiterals != null)
        {
            for (DisplayReturnServiceLiteral displayReturnServiceLiteral : displayReturnServiceLiterals)
            {
                fundReturnSeries.addDisplayValue(displayReturnServiceLiteral.getDisplayLabel(), displayReturnServiceLiteral.getValue(fundReturnSeries));
            }
        }

    }
}
