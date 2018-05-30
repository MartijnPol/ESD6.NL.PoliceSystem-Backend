package domain;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Martijn van der Pol on 30-05-18
 **/
@Entity
public class StolenCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean isStolen;

    public StolenCar() {
    }

    public StolenCar(String name, boolean isStolen) {
        this.name = name;
        this.isStolen = isStolen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .add("name", this.name)
                .add("isStolen", this.isStolen)
                .build();
    }
}
