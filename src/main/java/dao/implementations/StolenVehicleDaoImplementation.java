package dao.implementations;

import dao.JPA;
import dao.interfaces.StolenVehicleDao;
import domain.StolenVehicle;

import javax.ejb.Stateless;
import java.util.List;

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

    /**
     * Function to find all vehicles (stolen or not)
     * @param isStolen whether the vehicles are currently stolen or not
     * @return a List of StolenVehicles
     */
    @Override
    public List<StolenVehicle> findAll(boolean isStolen) {
        return this.entityManager.createNamedQuery("StolenVehicle.findAllStolenVehicles", StolenVehicle.class)
                .setParameter("isStolen", isStolen).getResultList();
    }
}
