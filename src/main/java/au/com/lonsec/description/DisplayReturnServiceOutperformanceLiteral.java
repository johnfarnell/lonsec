package au.com.lonsec.description;

import au.com.lonsec.calculation.CalculateReturnPercentageDifference;
import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@Component
@ConditionalOnBean(CalculateReturnPercentageDifference.class)
public class DisplayReturnServiceOutperformanceLiteral implements DisplayReturnServiceLiteral{

    @Value("${display.return.service.output.performance-underlimit:-1.00}")
    private BigDecimal underPerformanceLimit;
    @Value("${display.return.service.output.performance.overlimit:1.00}")
    private BigDecimal overPerformanceLimit;
    @Value("${display.return.service.output.over.performance.literal:out performed}")
    private String overPerformanceLiteral;
    @Value("${display.return.service.output.under.performance.literal:under performed}")
    private String underPerformanceLiteral;
    @Value("${display.return.service.output.matched.performance.literal:}")
    private String matchedPerformanceLiteral;
    @Autowired
    private CalculateReturnPercentageDifference calculateReturnPercentageDifference;
    @Value("${display.return.service.output.performance.label}")
    private String literal;
    @Override
    public String getValue(FundReturnSeries fundReturnSeries) {
        BigDecimal excess = calculateReturnPercentageDifference.getValue(fundReturnSeries);

        if (excess.compareTo(underPerformanceLimit) < 0)
        {
            return underPerformanceLiteral;
        }
        else
        {
            if (excess.compareTo(overPerformanceLimit)> 0)
            {
                return overPerformanceLiteral;
            }
            else
            {
                return matchedPerformanceLiteral;
            }
        }
    }

    @Override
    public String getDisplayLabel() {
        return literal;
    }
}
