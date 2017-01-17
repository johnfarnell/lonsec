package au.com.lonsec.unit.factory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.dao.input.CSVFundReturnSeriesProperties;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.ReturnSeriesLine;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by Countrywide Austral on 17-Jan-17.
 */
public class ReturnSeriesLineTest {
    @InjectMocks
    ReturnSeriesLine returnSeriesLine;
    @Mock
    private CSVReader reader;
    @Mock
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected=FundReturnException.class)
    public void testGetDateThrowsExceptionWhenDateInvalid()
    {

        ReturnSeriesLine returnSeriesLine = new ReturnSeriesLine("id","code", "invaliddate", "2.567",  "dd/MM/yyyy");
        returnSeriesLine.getDate();
    }
    @Test(expected=FundReturnException.class)
    public void testGetDateThrowsExceptionWhenDatePatternInvalid()
    {

        ReturnSeriesLine returnSeriesLine = new ReturnSeriesLine("id","code", "22/06/2016", "2.567",  "invalid");
        returnSeriesLine.getDate();
    }
    @Test(expected=FundReturnException.class)
    public void testGetReturnPercentageThrowsExceptionWhenInvalid()
    {

        ReturnSeriesLine returnSeriesLine = new ReturnSeriesLine("id","code", "22/06/2016", "invalid",  "dd/MM/yyyy");
        returnSeriesLine.getReturnPercentage();
    }
}
