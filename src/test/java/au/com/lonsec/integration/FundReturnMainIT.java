package au.com.lonsec.integration;

import au.com.lonsec.FundReturnMain;
import org.junit.Test;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
public class FundReturnMainIT {

    @Test
    public void testCompleteRunMockWrite() throws Exception {

        //Do the complete run, but switch off the write out put and use the test DAO just so we can see the results easil
        FundReturnMain.main(new String[]{"--csv.monthly.performance.dao=maintest"});
    }
    @Test
    public void testCompleteRun() throws Exception {

        //Do the complete run, but write to a differnt csv
        FundReturnMain.main(new String[]{"--csv.monthly.performance.file-name:FundReturnMainITOutput.csv"});
    }
}
