package au.com.lonsec.factory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.dao.input.CSVFundReturnSeriesProperties;
import au.com.lonsec.exception.FundReturnException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Component
public class FundReturnSeriesFactory {
    @Autowired
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    public ReturnSeriesLine create(String lineId, CSVReader reader) {
        try
        {
            String [] nextLine = reader.readNext();
            if (nextLine != null)
            {
                if (nextLine.length != 3)
                {
                    throw new FundReturnException("lineId" + lineId + " - Expected 3 fields in each line of the FundReturnSeries");
                }
                String code = nextLine[0];
                String dateAsStr =  nextLine[1];
                String returnPercentageReturn =  nextLine[2];

                return new ReturnSeriesLine(lineId, code, dateAsStr, returnPercentageReturn, csvFundReturnSeriesInputProperties.getFrsDatePattern());
            }
            else
            {
                return null;
            }
        } catch (IOException e) {
            throw new FundReturnException(e);
        }
    }
}
