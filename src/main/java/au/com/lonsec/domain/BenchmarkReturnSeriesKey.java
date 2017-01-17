package au.com.lonsec.domain;

import java.util.Date;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class BenchmarkReturnSeriesKey {
    private final Benchmark  benchmark;
    private final Date  date;

    public BenchmarkReturnSeriesKey(Benchmark benchmark, Date date) {
        this.benchmark = benchmark;
        this.date = date;
    }

    public BenchmarkReturnSeriesKey(BenchmarkReturnSeries benchmarkReturnSeries) {
        this.benchmark = benchmarkReturnSeries.getBenchmark();
        this.date = benchmarkReturnSeries.getDate();
    }

    @Override
    public boolean equals(Object obj) {

        boolean result = false;

        if (obj instanceof BenchmarkReturnSeriesKey)
        {
            BenchmarkReturnSeriesKey supplied = (BenchmarkReturnSeriesKey)obj;
            result = ((benchmark.getBenchmarkCode().equals(supplied.benchmark.getBenchmarkCode()))
                    && (date.equals(supplied.date)));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return benchmark.getBenchmarkCode().hashCode() + date.hashCode();
    }
}
