package au.com.lonsec.dao.input;

import au.com.bytecode.opencsv.CSVReader;
import au.com.lonsec.domain.Benchmark;
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
public class CsvBenchmarkDAO implements BenchmarkDAO{

    @Autowired
    private CsvFundReturnSeriesProperties csvFundReturnSeriesInputProperties;
    private Map<String, Benchmark> benchmarks;

    private synchronized Map<String, Benchmark> getBenchmarks()
    {
        if (benchmarks == null)
        {
            try
            {
                CSVReader reader = null;
                try {
                    benchmarks = new HashMap<String, Benchmark>();
                    reader = new CSVReader(new FileReader(csvFundReturnSeriesInputProperties.getFolder()
                            + csvFundReturnSeriesInputProperties.getBenchmarkFileName()), ',', '"', 1);
                    String [] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        String benchmarkCode = nextLine[0];
                        String benchmarkName = nextLine[1];

                        benchmarks.put(benchmarkCode, new Benchmark(benchmarkCode, benchmarkName));
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

        return benchmarks;
    }

    @Override
    public Benchmark getBenchmark(String code)
    {
        return getBenchmarks().get(code);
    }
}
