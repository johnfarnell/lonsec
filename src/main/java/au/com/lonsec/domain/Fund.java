package au.com.lonsec.domain;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class Fund {

    private String fundCode;
    private String fundName;
    private Benchmark benchmark;

    public Fund(String fundCode, String fundName, Benchmark benchMark) {
        this.fundCode = fundCode;
        this.fundName = fundName;
        this.benchmark = benchMark;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }
}
