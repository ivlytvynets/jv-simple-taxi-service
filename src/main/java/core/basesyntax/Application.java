package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.ManufacturerService;
import java.util.List;

public class Application {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setName("Mercedes-Benz");
        manufacturer1.setCountry("Germany");

        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setName("Toyota");
        manufacturer2.setCountry("Japan");

        Manufacturer manufacturer3 = new Manufacturer();
        manufacturer3.setName("Volkswagen");
        manufacturer3.setCountry("Germany");

        ManufacturerService manufacturerService = (ManufacturerService) injector
                .getInstance(ManufacturerService.class);

        manufacturerService.create(manufacturer1);
        manufacturerService.create(manufacturer2);
        manufacturerService.create(manufacturer3);

        List<Manufacturer> manufacturers = manufacturerService.getAll();

        System.out.println(manufacturers);

        manufacturerService.update(manufacturer1);
        manufacturerService.update(manufacturer2);
        manufacturerService.update(manufacturer3);

        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.get(2L));
        System.out.println(manufacturerService.get(3L));

        manufacturerService.delete(1L);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(3L);
        System.out.println(manufacturerService.getAll());
    }
}
