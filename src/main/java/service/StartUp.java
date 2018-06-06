package service;

import domain.StolenVehicle;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Singleton
@Startup
public class StartUp {

    @Inject
    private StolenVehicleService stolenVehicleService;

    @PostConstruct
    public void init() {
        StolenVehicle stolenVolvo = new StolenVehicle("08-SK-PX", true);
        this.stolenVehicleService.create(stolenVolvo);

        StolenVehicle resolvedCampers = new StolenVehicle("12-ASD-76", false);
        this.stolenVehicleService.create(resolvedCampers);
    }

}
