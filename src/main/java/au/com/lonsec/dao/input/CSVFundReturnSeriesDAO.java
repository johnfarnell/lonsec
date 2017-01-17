package au.com.lonsec.dao.input;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.Fund;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.FundReturnSeriesFactory;
import au.com.lonsec.factory.ReturnSeriesLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Component
@Profile("csvinput")
public class CSVFundReturnSeriesDAO implements FundReturnSeriesDAO {

    @Autowired
    private CSVFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Autowired
    private FundDAO fundDAO;
    @Autowired
    private BenchmarkReturnSeriesDAO benchmarkReturnSeriesDAO;
    @Autowired
    private FundReturnSeriesFactory fundReturnSeriesFactory;

    private CSVReader reader;

    private int lineIdIndex = 0;

    public FundReturnSeries getFirstReturnSeries()
    {
        try
        {
            if (this.reader != null)
            {
                this.reader.close();
            }

            lineIdIndex = 1;
            reader = new CSVReader(new FileReader(csvFundReturnSeriesInputProperties.getFolder()
                    + csvFundReturnSeriesInputProperties.getFrsFileName()), ',', '"', 1);
        }
        catch (Exception e)
        {
            throw new FundReturnException(e);
        }

        return getNextReturnSeries();
    }

    public FundReturnSeries getNextReturnSeries()
    {
        ReturnSeriesLine line = null;
        try
        {
            line = fundReturnSeriesFactory.create("LineId" + ++lineIdIndex, reader);
            if (line != null)
            {
                Fund fund = fundDAO.getFund(line.getCode());
                BenchmarkReturnSeries benchmarkReturnSeries =
                        benchmarkReturnSeriesDAO.getBenchmark(fund.getBenchmark(), line.getDate());
                return new FundReturnSeries(fund, line.getDate(), line.getReturnPercentage(), benchmarkReturnSeries);
            }
            else
            {
                reader.close();
                return null;
            }
        }
        catch (Exception e)
        {
            throw new FundReturnException(e);
        }
    }
}
