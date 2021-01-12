package core.basesyntax.model;

import core.basesyntax.exception.NullValueException;

public class Driver {
    private Long id;
    private String name;
    private String licenceNumber;

    public Driver(String name, String licenceNumber) {
        if (!(checkForNull(name) || checkForNull(licenceNumber))) {
            this.name = name;
            this.licenceNumber = licenceNumber;
        }
    }

    public Driver() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        checkForNull(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkForNull(name);
        this.name = name;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        checkForNull(licenceNumber);
        this.licenceNumber = licenceNumber;
    }

    @Override
    public String toString() {
        return "Driver{"
                + "name='" + name + '\''
                + ", licenceNumber='" + licenceNumber + '\''
                + '}';
    }

    private boolean checkForNull(Object o) {
        if (o == null) {
            throw new NullValueException("Invalid value: null");
        }
        return true;
    }
}
