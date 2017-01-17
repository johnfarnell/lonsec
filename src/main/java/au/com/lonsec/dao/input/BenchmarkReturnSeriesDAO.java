package au.com.lonsec.dao.input;

import au.com.lonsec.domain.Benchmark;
import au.com.lonsec.domain.BenchmarkReturnSeries;

import java.util.Date;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public interface BenchmarkReturnSeriesDAO {

    BenchmarkReturnSeries getBenchmark(Benchmark benchmark, Date date);
}
