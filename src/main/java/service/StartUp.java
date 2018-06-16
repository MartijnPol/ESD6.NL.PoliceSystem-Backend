package service;

import domain.Account;
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

    @Inject
    private AccountService accountService;

    @PostConstruct
    public void init() {
        StolenVehicle stolenVolvo = new StolenVehicle("08-SK-PX", true);
        this.stolenVehicleService.create(stolenVolvo);

        StolenVehicle resolvedCampers = new StolenVehicle("00-01-ES", false);
        this.stolenVehicleService.create(resolvedCampers);

        Account adminAccount = new Account("admin", "admin");
        this.accountService.create(adminAccount);
    }

}
