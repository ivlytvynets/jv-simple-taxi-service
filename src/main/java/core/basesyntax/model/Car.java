package core.basesyntax.model;

import core.basesyntax.exception.NullValueException;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private Long id;
    private String model;
    private Manufacturer manufacturer;
    private List<Driver> drivers;

    public Car(String model, Manufacturer manufacturer) {
        if (!(checkForNull(model) || checkForNull(manufacturer))) {
            this.model = model;
            this.manufacturer = manufacturer;
        }
    }

    public Car() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        checkForNull(id);
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        checkForNull(model);
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        checkForNull(manufacturer);
        this.manufacturer = manufacturer;
    }

    public List<Driver> getDrivers() {
        return drivers == null ? new ArrayList<>() : drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        checkForNull(drivers);
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", model='" + model + '\''
                + ", manufacturer=" + manufacturer
                + ", drivers=" + drivers
                + '}';
    }

    private boolean checkForNull(Object o) {
        if (o == null) {
            throw new NullValueException("Invalid value: null");
        }
        return true;
    }
}
