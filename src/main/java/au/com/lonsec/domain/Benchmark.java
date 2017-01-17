package au.com.lonsec.domain;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class Benchmark {
    private String benchmarkCode;
    private String benchmarkName;

    public Benchmark(String benchmarkCode, String benchmarkName) {
        this.benchmarkCode = benchmarkCode;
        this.benchmarkName = benchmarkName;
    }

    public String getBenchmarkCode() {
        return benchmarkCode;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }
}
