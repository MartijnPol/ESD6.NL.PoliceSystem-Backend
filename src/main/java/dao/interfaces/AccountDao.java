package dao.interfaces;

import domain.Account;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
public interface AccountDao extends GenericDao<Account> {

    Account findByUsername(String username);

}
