package au.com.lonsec.factory;

import au.com.lonsec.exception.FundReturnException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class ReturnSeriesLine {

    private String lineId;
    private String code;
    private String date;
    private String returnPercentage;
    private String datePattern;

    public ReturnSeriesLine(String lineId, String code, String dateAsStr, String returnPercentage, String datePattern) {
        this.lineId = lineId;
        this.code = code;
        this.date = dateAsStr;
        this.returnPercentage = returnPercentage;
        this.datePattern = datePattern;
    }

    public BigDecimal getReturnPercentage() {
        try {
            return new BigDecimal(returnPercentage);
        } catch (NumberFormatException e) {
            throw new FundReturnException("Line Id " + lineId + " - Invalid Return Percentage " + returnPercentage);
        }
    }

    public String getCode() {
        return code;
    }

    public Date getDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new FundReturnException("Line Id " + lineId + " - Invalid Date " + date);
        }
        catch (Exception e) {
            throw new FundReturnException("Line Id " + lineId + "exception thrown", e);
        }
    }

}
