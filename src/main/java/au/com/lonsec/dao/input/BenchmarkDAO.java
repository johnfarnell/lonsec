package au.com.lonsec.dao.input;

import au.com.lonsec.domain.Benchmark;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public interface BenchmarkDAO {

    Benchmark getBenchmark(String code);
}
