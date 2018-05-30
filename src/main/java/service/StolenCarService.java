package service;

import dao.JPA;
import dao.interfaces.StolenCarDao;
import domain.StolenCar;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Stateless
public class StolenCarService {

    @Inject
    @JPA
    private StolenCarDao stolenCarDao;

    /**
     * Empty constructor
     */
    public StolenCarService() {

    }

    /**
     * Function to create a new StolenCar and store it in the database
     * @param stolenCar is the StolenCar object that needs to be stored
     * @return the stolenCar object with ID
     */
    public StolenCar create(StolenCar stolenCar) {
        return this.stolenCarDao.create(stolenCar);
    }

    /**
     * Function to find all StolenCars in the database
     * @return all StolenCars found in the database
     */
    public List<StolenCar> findAll() {
        return this.stolenCarDao.findAll();
    }

    /**
     * Function to convert all StolenCars in a List to JsonObjects
     * @param stolenCars List of StolenCars
     * @return a List of JsonObjects (JSON representatives of the StolenCar object)
     */
    public List<JsonObject> convertToJson(List<StolenCar> stolenCars) {
        List<JsonObject> jsonObjects = new ArrayList<>();
        for (StolenCar stolenCar : stolenCars) {
            jsonObjects.add(stolenCar.toJson());
        }
        return jsonObjects;
    }

}
