package au.com.lonsec.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class BenchmarkReturnSeries {

    private Benchmark benchmark;
    private Date date;
    private BigDecimal returnPercentage;

    public BenchmarkReturnSeries(Benchmark benchMark, Date date, BigDecimal returnPercentage) {
        this.benchmark = benchMark;
        this.date = date;
        this.returnPercentage = returnPercentage;
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(BigDecimal returnPercentage) {
        this.returnPercentage = returnPercentage;
    }
}
