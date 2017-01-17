package au.com.lonsec;

import au.com.lonsec.controller.FundsReturnController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
@SpringBootApplication
public class FundReturnMain implements CommandLineRunner{

    @Autowired
    private FundsReturnController fundsReturnController;
    @Override
    public void run(String... args) {
        //java -jar fundreturns-1.0.0.jar --csv.input.frs-file-name="C:\AWS-Gaurang\FundReturn Series.csv"
        fundsReturnController.execute();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FundReturnMain.class, args);
    }

}
