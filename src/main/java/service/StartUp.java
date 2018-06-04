package service;

import domain.StolenCar;

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
    private StolenCarService stolenCarService;

    @PostConstruct
    public void init() {
        StolenCar stolenVolvo = new StolenCar("Volvo", true);
        this.stolenCarService.create(stolenVolvo);

        StolenCar resolvedCampers = new StolenCar("Campers", false);
        this.stolenCarService.create(resolvedCampers);
    }

}
