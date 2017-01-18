package au.com.lonsec.dao.input;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.BenchmarkReturnSeries;
import au.com.lonsec.domain.BenchmarkReturnSeriesKey;
import au.com.lonsec.exception.FundReturnException;
import au.com.lonsec.factory.BenchmarkReturnSeriesFactory;
import au.com.lonsec.factory.ReturnSeriesLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Component
public class CsvBenchmarkReturnSeriesDAO implements BenchmarkReturnSeriesDAO {
    @Autowired
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    @Autowired
    private BenchmarkReturnSeriesFactory benchmarkReturnSeriesFactory;
    @Autowired
    private BenchmarkDAO benchmarkDAO;
    private Map<BenchmarkReturnSeriesKey, BenchmarkReturnSeries> benchmarkReturnSeries;

    private synchronized Map<BenchmarkReturnSeriesKey, BenchmarkReturnSeries> getBenchmarkReturnSeries()
    {
        if (benchmarkReturnSeries == null)
        {
            try
            {

                CSVReader reader = null;
                try
                {
                    benchmarkReturnSeries = new HashMap<BenchmarkReturnSeriesKey, BenchmarkReturnSeries>();

                    reader = new CSVReader(new FileReader(csvFundReturnSeriesInputProperties.getFolder()
                            + csvFundReturnSeriesInputProperties.getBrsFileName()), ',', '"', 1);
                    int lineIdIndex = 0;
                    while (true)
                    {

                        ReturnSeriesLine line = benchmarkReturnSeriesFactory.create("LineId" + ++lineIdIndex, reader);

                        // null is returned when all bench marks have been read
                        if (line == null)
                        {
                            break;
                        }

                        Benchmark benchmark = benchmarkDAO.getBenchmark(line.getCode());

                        if (benchmark == null )
                        {
                            throw new FundReturnException("Bench mark code " + line.getCode() + " in the benchmark return series csv file does not exist");
                        }
                        BenchmarkReturnSeries benchmarkReturnSeries = new BenchmarkReturnSeries(benchmark, line.getDate(), line.getReturnPercentage());
                        this.benchmarkReturnSeries.put(new BenchmarkReturnSeriesKey(benchmarkReturnSeries), benchmarkReturnSeries);
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

        return benchmarkReturnSeries;
    }

    @Override
    public BenchmarkReturnSeries getBenchmarkReturnSeries(Benchmark benchmark, Date date)
    {
        return getBenchmarkReturnSeries().get(new BenchmarkReturnSeriesKey(benchmark, date));
    }
}
