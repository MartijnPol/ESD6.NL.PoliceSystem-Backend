package dao.implementations;

import dao.JPA;
import dao.interfaces.StolenVehicleDao;
import domain.StolenVehicle;

import javax.ejb.Stateless;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Stateless
@JPA
public class StolenVehicleDaoImplementation extends GenericDaoImplementation<StolenVehicle> implements StolenVehicleDao {

    /**
     * Empty constructor
     */
    public StolenVehicleDaoImplementation() {

    }

}
