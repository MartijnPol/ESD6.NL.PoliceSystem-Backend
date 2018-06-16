package service;

import dao.JPA;
import dao.interfaces.StolenVehicleDao;
import domain.StolenVehicle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Stateless
public class StolenVehicleService {

    @Inject
    @JPA
    private StolenVehicleDao stolenVehicleDao;

    /**
     * Empty constructor
     */
    public StolenVehicleService() {

    }

    /**
     * Function to create a new StolenVehicle and store it in the database
     *
     * @param stolenVehicle is the StolenVehicle object that needs to be stored
     * @return the stolenCar object with ID
     */
    public StolenVehicle create(StolenVehicle stolenVehicle) {
        return this.stolenVehicleDao.create(stolenVehicle);
    }

    /**
     * Function to update a new StolenVehicle
     *
     * @param stolenVehicle is the StolenVehicle that needs to be updated
     * @return the updated StolenVehicle
     */
    public StolenVehicle update(StolenVehicle stolenVehicle) {
        return this.stolenVehicleDao.update(stolenVehicle);
    }

    /**
     * Function to find a specific StolenVehicle by it's id
     *
     * @param id is the id of the StolenVehicle
     * @return a StolenVehicle object, if found
     */
    public StolenVehicle findById(Long id) {
        return this.stolenVehicleDao.findById(id);
    }

    /**
     * Function to find all StolenVehicles that are currently not resolved in the database
     *
     * @return all StolenCars found in the database who are currently stolen
     */
    public List<StolenVehicle> findAll(boolean isStolen) {
        return this.stolenVehicleDao.findAll(isStolen);
    }

    /**
     * Function to find all StolenCars in the database
     *
     * @return all StolenCars found in the database
     */
    public List<StolenVehicle> findAll() {
        return this.stolenVehicleDao.findAll();
    }

    /**
     * Function to find a Stolen Vehicle based on the given LicensePlate
     *
     * @param licensePlate the LicensePlate of the stolen vehicle
     * @return the StolenVehicle, if found
     */
    public StolenVehicle findByLicensePlate(String licensePlate) {
        return this.stolenVehicleDao.findByLicensePlate(licensePlate);
    }

    /**
     * Function to convert all StolenCars in a List to JsonObjects
     *
     * @param stolenVehicles List of stolen vehicles
     * @return a List of JsonObjects (JSON representatives of the StolenVehicle object)
     */
    public List<JsonObject> convertToJson(List<StolenVehicle> stolenVehicles) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        for (StolenVehicle stolenVehicle : stolenVehicles) {
            jsonObjects.add(stolenVehicle.toJson());
        }
        return jsonObjects;
    }

}
