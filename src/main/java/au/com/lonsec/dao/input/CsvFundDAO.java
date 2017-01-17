package au.com.lonsec.dao.input;

import au.com.bytecode.opencsv.CSVReader;
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
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Autowired
    private BenchmarkDAO benchmarkDAO;
    private Map<String, Fund> funds;

//    private synchronized Map<String, Fund> getFunds()
//    {
//        if (funds == null)
//        {
//            try
//            {
//                funds = new HashMap<String, Fund>();
////                reader = new CsvReader(csvFundReturnSeriesInputProperties.getFolder()
////                        + csvFundReturnSeriesInputProperties.getFundFileName());
//                reader = new CsvReader("./input/fund.csv");
//                reader.readHeaders();
//
//                //FundCode,FundName,BenchmarkCode
//                while (reader.readRecord())
//                {
//                    String fundCode = reader.get(FUND_RETURN_SERIES_FUND_CODE_COLUMN_NAME);
//                    String fundName = reader.get(FUND_RETURN_SERIES_FUND_NAME_COLUMN_NAME);
//                    String benchmarkCode = reader.get(FUND_RETURN_SERIES_BENCHMARK_CODE_COLUMN_NAME);
//
//                    System.out.println(fundCode + fundName + benchmarkCode);
//
//        //            funds.put(benchmarkCode, new Fund(fundCode, fundName, benchmarkDAO.getBenchmark(benchmarkCode)));
//                }
//            }
//            catch (Exception e)
//            {
//                throw new FundReturnException(e);
//            }
//        }
//
//        return funds;
//    }

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
//                reader = new CsvReader(csvFundReturnSeriesInputProperties.getFolder()
//                        + csvFundReturnSeriesInputProperties.getFundFileName());
                    reader = new CSVReader(new FileReader(csvFundReturnSeriesInputProperties.getFolder()
                            + csvFundReturnSeriesInputProperties.getFundFileName()), ',', '"', 1);
                    String [] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        // nextLine[] is an array of values from the line
                        System.out.println(nextLine[0] + nextLine[1]  + nextLine[2]);
                        String fundCode = nextLine[0];
                        String fundName = nextLine[1];
                        String benchmarkCode = nextLine[2];

                        funds.put(fundCode, new Fund(fundCode, fundName, benchmarkDAO.getBenchmark(benchmarkCode)));
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
