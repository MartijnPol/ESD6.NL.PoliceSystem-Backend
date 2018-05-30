package dao.implementations;

import dao.JPA;
import dao.interfaces.StolenCarDao;
import domain.StolenCar;

import javax.ejb.Stateless;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Stateless
@JPA
public class StolenCarDaoImplementation extends GenericDaoImplementation<StolenCar> implements StolenCarDao {

    /**
     * Empty constructor
     */
    public StolenCarDaoImplementation() {

    }

}
