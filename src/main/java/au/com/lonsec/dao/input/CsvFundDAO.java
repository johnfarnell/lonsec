package au.com.lonsec.dao.input;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.Fund;
import au.com.lonsec.exception.FundReturnException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Component
public class CsvFundDAO implements FundDAO {

    @Autowired
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Autowired
    private BenchmarkDAO benchmarkDAO;
    private Map<String, Fund> funds;

    private synchronized Map<String, Fund> getFunds()
    {
        if (funds == null)
        {
            try
            {
                CSVReader reader = null;
                try
                {
                    funds = new HashMap<String, Fund>();
                    reader = new CSVReader(new FileReader(csvFundReturnSeriesInputProperties.getFolder()
                            + csvFundReturnSeriesInputProperties.getFundFileName()), ',', '"', 1);
                    String [] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        String fundCode = nextLine[0];
                        String fundName = nextLine[1];
                        String benchmarkCode = nextLine[2];

                        Benchmark benchmark = benchmarkDAO.getBenchmark(benchmarkCode);
                        if (benchmark == null )
                        {
                            throw new FundReturnException("Bench mark code " + benchmarkCode + " in the fund csv file does not exist");
                        }
                        funds.put(fundCode, new Fund(fundCode, fundName, benchmark));
                    }
                }
                finally
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                }
            }
            catch (FundReturnException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new FundReturnException(e);
            }
        }

        return funds;
    }

    public Fund getFund(String code)
    {
        return getFunds().get(code);
    }
}
