package domain;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Entity
@NamedQueries({
        @NamedQuery(name = "StolenVehicle.findAllStolenVehicles", query = "SELECT v FROM StolenVehicle v WHERE v.isStolen = :isStolen")
})
public class StolenVehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlate;
    private boolean isStolen;

    public StolenVehicle() {
    }

    public StolenVehicle(String licensePlate, boolean isStolen) {
        this.licensePlate = licensePlate;
        this.isStolen = isStolen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public boolean isStolen() {
        return isStolen;
    }

    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("licensePlate", this.licensePlate)
                .add("isStolen", this.isStolen)
                .build();
    }
}
