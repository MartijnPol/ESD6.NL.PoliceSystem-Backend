package service;

import dao.JPA;
import dao.interfaces.AccountDao;
import domain.Account;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
@Stateless
public class AccountService {

    @Inject
    @JPA
    private AccountDao accountDao;

    public AccountService() {
    }

    /**
     * Function to create an account and save it to the database
     *
     * @param account the account that needs to be saved
     * @return the account with persisted id
     */
    public Account create(Account account) {
        return this.accountDao.create(account);
    }

    /**
     * Function to find a specific account by it's username
     *
     * @param username is the username of the account
     * @return the account, if found
     */
    public Account findByUsername(String username) {
        return this.accountDao.findByUsername(username);
    }

}
