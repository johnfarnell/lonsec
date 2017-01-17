package au.com.lonsec.controller;

import au.com.lonsec.calculation.CalculationReturnSeriesHandler;
import au.com.lonsec.dao.input.FundReturnSeriesDAO;
import au.com.lonsec.dao.output.CsvMonthPerformanceDAO;
import au.com.lonsec.description.DisplayReturnSeriesHandler;
import au.com.lonsec.rank.FundReturnSeriesRank;
import au.com.lonsec.domain.FundReturnSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Controller
public class FundReturnsController {

    @Autowired
    private FundReturnSeriesDAO fundReturnSeriesDAO;

    @Autowired
    private CsvMonthPerformanceDAO csvMonthPerformanceDAO;

    @Autowired
    private CalculationReturnSeriesHandler calculationReturnSeriesHandler;

    @Autowired
    private DisplayReturnSeriesHandler displayReturnSeriesHandler;

    @Autowired
    private ReturnSeriesList returnSeriesList;

    @Autowired
    private FundReturnSeriesRank fundReturnSeriesRank;

    public void execute()
    {
        //Loop throguh the Fund Return Series
        FundReturnSeries fundReturnSeries = fundReturnSeriesDAO.getFirstReturnSeries();

        //Clear the list
        returnSeriesList.clear();
        while (fundReturnSeries != null) //the DAO will  return null when there are no more FundReturnSeries
        {
            //Add any configured calculations
            calculationReturnSeriesHandler.addCalculatedValues(fundReturnSeries);
            //Add any configured displays
            displayReturnSeriesHandler.addDisplayValues(fundReturnSeries);

            //Add to the list
            returnSeriesList.add(fundReturnSeries);
            //get next
            fundReturnSeries = fundReturnSeriesDAO.getNextReturnSeries();
        }

        //Supply the list to calculate the ranking
        fundReturnSeriesRank.setRanking(returnSeriesList.getFundReturnSeriesList());
        csvMonthPerformanceDAO.writeDetails(returnSeriesList.sort());

    }
}
