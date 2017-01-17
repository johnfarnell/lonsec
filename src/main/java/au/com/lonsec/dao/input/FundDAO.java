package au.com.lonsec.dao.input;

import au.com.lonsec.domain.Fund;

/**
 * Created by Countrywide Austral on 15-Jan-17.
 */
public interface FundDAO {

    Fund getFund(String code);
}
