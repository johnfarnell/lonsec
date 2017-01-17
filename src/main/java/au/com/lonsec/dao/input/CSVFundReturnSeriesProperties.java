package au.com.lonsec.dao.input;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Component
@ConfigurationProperties("csv.input")
@Profile("csvinput")
public class CSVFundReturnSeriesProperties {
    private String folder;
    private String fundFileName;
    private String benchmarkFileName;
    private String frsFileName;
    private String brsFileName;
    private String brsDatePattern;
    private String frsDatePattern;
    public String getFundFileName() {
        return fundFileName;
    }

    public void setFundFileName(String fundFileName) {
        this.fundFileName = fundFileName;
    }

    public String getBenchmarkFileName() {
        return benchmarkFileName;
    }

    public void setBenchmarkFileName(String benchmarkFileName) {
        this.benchmarkFileName = benchmarkFileName;
    }

    public String getFrsFileName() {
        return frsFileName;
    }

    public void setFrsFileName(String frsFileName) {
        this.frsFileName = frsFileName;
    }
    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getBrsFileName() {
        return brsFileName;
    }

    public void setBrsFileName(String brsFileName) {
        this.brsFileName = brsFileName;
    }

    public String getBrsDatePattern() {
        return brsDatePattern;
    }

    public void setBrsDatePattern(String brsDatePattern) {
        this.brsDatePattern = brsDatePattern;
    }

    public String getFrsDatePattern() {
        return frsDatePattern;
    }

    public void setFrsDatePattern(String frsDatePattern) {
        this.frsDatePattern = frsDatePattern;
    }
}
