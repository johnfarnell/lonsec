package au.com.lonsec.controller;

import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.sort.FundReturnSeriesComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
@Component
public class ReturnSeriesList {

    @Autowired
    private FundReturnSeriesComparator fundReturnSeriesComparator;


    List<FundReturnSeries> fundReturnSeriesList = new ArrayList<FundReturnSeries>();

    public void clear() {
        fundReturnSeriesList.clear();
    }

    public void add(FundReturnSeries fundReturnSeries) {
        fundReturnSeriesList.add(fundReturnSeries);
    }

    public List<FundReturnSeries> getFundReturnSeriesList() {
        return fundReturnSeriesList;
    }
    public List<FundReturnSeries> sort() {
        fundReturnSeriesList.sort(fundReturnSeriesComparator.getComparator());
        return fundReturnSeriesList;
    }


}
