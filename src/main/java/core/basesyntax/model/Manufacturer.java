package core.basesyntax.model;

import core.basesyntax.exception.NullValueException;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        if (!(checkForNull(name) || checkForNull(country))) {
            this.name = name;
            this.country = country;
        }
    }

    public Manufacturer() {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        checkForNull(country);
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "name='" + name + '\''
                + ", country='"
                + country + '\''
                + '}';
    }

    private boolean checkForNull(Object o) {
        if (o == null) {
            throw new NullValueException("Invalid value: null");
        }
        return true;
    }
}
