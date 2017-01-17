package au.com.lonsec.exception;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public class FundReturnException  extends RuntimeException{

    public FundReturnException(Exception e){
        super(e);
    }
    public FundReturnException(String message){
        super(message);
    }
    public FundReturnException(String message, Exception e){
        super(message, e);
    }
}
