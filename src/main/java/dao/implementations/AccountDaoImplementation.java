package dao.implementations;

import dao.JPA;
import dao.interfaces.AccountDao;
import domain.Account;

import javax.ejb.Stateless;

/**
 * Created by Martijn van der Pol on 16-06-18
 **/
@Stateless
@JPA
public class AccountDaoImplementation extends GenericDaoImplementation<Account> implements AccountDao {

    public AccountDaoImplementation() {
    }

    @Override
    public Account findByUsername(String username) {
        try {
            return this.entityManager.createNamedQuery("Account.findByUsername", Account.class)
                    .setParameter("username", username).getSingleResult();
        } catch (Exception exception) {
            return null;
        }
    }
}
