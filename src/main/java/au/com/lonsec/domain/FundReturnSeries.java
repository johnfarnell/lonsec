package au.com.lonsec.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class FundReturnSeries {
    private Fund fund;
    private Date date;
    private BigDecimal returnPercentage;
    private BenchmarkReturnSeries benchmarkReturnSeries;
    private Map<String, BigDecimal> calculatedValues = new HashMap<>();
    private Map<String, String> displayValues = new HashMap<>();
    private int rank = -1;
    public FundReturnSeries(Fund fund, Date date, BigDecimal returnPercentage, BenchmarkReturnSeries benchmarkReturnSeries) {
        this.fund = fund;
        this.date = date;
        this.returnPercentage = returnPercentage;
        this.benchmarkReturnSeries = benchmarkReturnSeries;
    }
    public Fund getFund() {
        return fund;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getReturnPercentage() {
        return returnPercentage;
    }

    public BenchmarkReturnSeries getBenchmarkReturnSeries() {
        return benchmarkReturnSeries;
    }

    public void addDisplayValue(String label, String value)
    {
        displayValues.put(label, value);
    }

    public String getDisplayValue(String label)
    {
        return displayValues.get(label);
    }

    public void addCalculatedValue(String label, BigDecimal value)
    {
        calculatedValues.put(label, value);
    }

    public BigDecimal getCalculatedValue(String label)
    {
        return calculatedValues.get(label);
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
