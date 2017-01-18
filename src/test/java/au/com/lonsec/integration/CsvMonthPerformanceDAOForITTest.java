package au.com.lonsec.integration;

import au.com.lonsec.dao.output.MonthlyPerformanceDAO;
import au.com.lonsec.domain.FundReturnSeries;
import org.junit.Assert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
@Component
@ConditionalOnProperty(name = "csv.monthly.performance.dao", havingValue = "maintest")
public class CsvMonthPerformanceDAOForITTest implements MonthlyPerformanceDAO
{
    private List<FundReturnSeries> fundReturnSeriesList;
    @Override
    public void writeDetails(List<FundReturnSeries> fundReturnSeriesList) {
        Assert.assertEquals(36, fundReturnSeriesList.size());
    }
}
