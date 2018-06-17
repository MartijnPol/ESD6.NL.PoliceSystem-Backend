package dao.interfaces;

import domain.StolenVehicle;

import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
public interface StolenVehicleDao extends GenericDao<StolenVehicle> {

    List<StolenVehicle> findAll(boolean isStolen);

    StolenVehicle findByLicensePlate(String licensePlate);

}
