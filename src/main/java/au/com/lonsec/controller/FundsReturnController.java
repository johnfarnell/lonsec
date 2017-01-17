package au.com.lonsec.controller;

import au.com.lonsec.calculation.FundReturnSeriesCalculation;
import au.com.lonsec.dao.input.FundReturnSeriesDAO;
import au.com.lonsec.dao.output.CsvMonthPerformanceDAO;
import au.com.lonsec.rank.FundReturnSeriesRank;
import au.com.lonsec.description.DisplayReturnServiceLiteral;
import au.com.lonsec.domain.FundReturnSeries;
import au.com.lonsec.sort.FundReturnSeriesComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@Controller
public class FundsReturnController {

    @Autowired
    private FundReturnSeriesDAO fundReturnSeriesDAO;

    @Autowired
    private FundReturnSeriesComparator fundReturnSeriesComparator;

    @Autowired
    private CsvMonthPerformanceDAO csvMonthPerformanceDAO;
    @Autowired(required=false)
    private List<FundReturnSeriesCalculation> fundReturnSeriesCalculations;
    @Autowired(required=false)
    private List<DisplayReturnServiceLiteral> displayReturnServiceLiteral;

    @Autowired
    private FundReturnSeriesRank fundReturnSeriesRank;

    public void execute()
    {
        FundReturnSeries fundReturnSeries = fundReturnSeriesDAO.getFirstReturnSeries();

        List<FundReturnSeries> fundReturnSeriesList = new ArrayList<FundReturnSeries>();

        while (fundReturnSeries != null)
        {
            System.out.println("");
            System.out.println("");
            System.out.println("start");
            System.out.println("fundReturnSeries.return percentage " + fundReturnSeries.getReturnPercentage());
            System.out.println("fundReturnSeries.return date " + fundReturnSeries.getDate());
            System.out.println("fundReturnSeries.return fund code " + fundReturnSeries.getFund().getFundCode());
            System.out.println("fundReturnSeries.return fund name " + fundReturnSeries.getFund().getFundName());
            System.out.println("fundReturnSeries.return benchmark code " + fundReturnSeries.getFund().getBenchmark().getBenchmarkCode());
            System.out.println("fundReturnSeries.return benchmark name " + fundReturnSeries.getFund().getBenchmark().getBenchmarkName());
            System.out.println("fundReturnSeries.return brs benchmark code " + fundReturnSeries.getBenchmarkReturnSeries().getBenchmark().getBenchmarkCode());
            System.out.println("fundReturnSeries.return brs benchmark name " + fundReturnSeries.getBenchmarkReturnSeries().getBenchmark().getBenchmarkName());
            System.out.println("fundReturnSeries.return brs date " + fundReturnSeries.getBenchmarkReturnSeries().getDate());
            System.out.println("fundReturnSeries. brs ReturnPercentage " + fundReturnSeries.getBenchmarkReturnSeries().getReturnPercentage());

            if (fundReturnSeriesCalculations != null)
            {
                for (FundReturnSeriesCalculation fundReturnSeriesCalculation :fundReturnSeriesCalculations)
                {
                    fundReturnSeries.addCalculatedValue(fundReturnSeriesCalculation.getCalculationLabel(), fundReturnSeriesCalculation.getValue(fundReturnSeries));
                }
            }
            if (displayReturnServiceLiteral != null)
            {
                for (DisplayReturnServiceLiteral displayReturnServiceLiteralx :displayReturnServiceLiteral)
                {
                    fundReturnSeries.addDisplayValue(displayReturnServiceLiteralx.getDisplayLabel(), displayReturnServiceLiteralx.getValue(fundReturnSeries));
                }
            }


            System.out.println("end");
            fundReturnSeriesList.add(fundReturnSeries);
            fundReturnSeries = fundReturnSeriesDAO.getNextReturnSeries();
        }

        fundReturnSeriesRank.setRanking(fundReturnSeriesList);

        fundReturnSeriesList.sort(fundReturnSeriesComparator.getComparator());
        fundReturnSeriesList.forEach(frs ->
                {
                    System.out.println("");
                    System.out.println("");
                    System.out.println("start vvv");
                    System.out.println("fundReturnSeries.return date " + frs.getDate());
                    System.out.println("fundReturnSeries.return percentage " + frs.getReturnPercentage());
                    System.out.println("BENCHMARK.return percentage " + frs.getBenchmarkReturnSeries().getReturnPercentage());
                    System.out.println("Excess " + frs.getCalculatedValue("Excess"));
                    System.out.println("Excess Raw " + frs.getCalculatedValue("Excess Rawcus"));
                    System.out.println("Outprform " + frs.getDisplayValue("OutPerformance"));
                    System.out.println("end");
                }

        );

        csvMonthPerformanceDAO.setHeaders(fundReturnSeriesList);

    }
}
