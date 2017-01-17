package au.com.lonsec.rank;

import au.com.lonsec.domain.FundReturnSeries;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Countrywide Austral on 16-Jan-17.
 */
@ConditionalOnProperty(name = "rank.fund.return", havingValue = "default", matchIfMissing = true)
@Component
public class FundServiceRankByBestReturnForSameDate implements FundReturnSeriesRank {
    @Override
    public void setRanking(List<FundReturnSeries> fundReturnSeriesList) {


        final ListMultimap<Date,FundReturnSeries> returnSeriesMultiMap = ArrayListMultimap.create();

        /*
        Group all the FundReturnSeries by date using google multimap
         */
        for (FundReturnSeries fundReturnSeries :fundReturnSeriesList)
        {
            returnSeriesMultiMap.put(fundReturnSeries.getDate(), fundReturnSeries);
        }
        //Now get a distinct set of these dates

        Set<Date> multiMapKeys = returnSeriesMultiMap.keySet();


        multiMapKeys.forEach( (date) ->
                {

                    // for each date, sort the FundReturnSeries by descending return percentage
                    List<FundReturnSeries> fundReturnSeriesByDateList = returnSeriesMultiMap.get(date);

                    fundReturnSeriesByDateList.sort((o1, o2) ->
                            {
                                return o2.getReturnPercentage().compareTo(o1.getReturnPercentage());
                            }
                    );

                    //Now they are sorted as required, assign the ranking

                    int index = 1;
                    for (FundReturnSeries fundSeries : fundReturnSeriesByDateList)
                    {
                        fundSeries.setRank(index++);
                    }
                }

        );

    }
}
