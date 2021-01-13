package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Car;
import core.basesyntax.model.Driver;
import core.basesyntax.model.Manufacturer;
import core.basesyntax.service.CarService;
import core.basesyntax.service.DriverService;
import core.basesyntax.service.ManufacturerService;

public class Application {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        Manufacturer mercedes = new Manufacturer();
        mercedes.setName("Mercedes-Benz");
        mercedes.setCountry("Germany");

        Manufacturer toyota = new Manufacturer();
        toyota.setName("Toyota");
        toyota.setCountry("Japan");

        Manufacturer volkswagen = new Manufacturer();
        volkswagen.setName("Volkswagen");
        volkswagen.setCountry("Germany");

        /*Car car1 = new Car();
        car1.setModel("GLC-350");
        car1.setManufacturer(mercedes);

        Car car2 = new Car();
        car2.setModel("Rav-4");
        car2.setManufacturer(toyota);

        Car car3 = new Car();
        car3.setModel("Golf-V");
        car3.setManufacturer(volkswagen);

        Driver ivan = new Driver();
        ivan.setName("Ivan");
        ivan.setLicenceNumber("AD123456");

        Driver dmytro = new Driver();
        dmytro.setName("Dmytro");
        dmytro.setLicenceNumber("GF456879");

        Driver bohdan = new Driver();
        bohdan.setName("Bohdan");
        bohdan.setLicenceNumber("DS789654");*/

        ManufacturerService manufacturerService = (ManufacturerService) injector
                .getInstance(ManufacturerService.class);
        /*manufacturerService.create(mercedes);
        manufacturerService.create(toyota);
        manufacturerService.create(volkswagen);*/

        /*DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        driverService.create(ivan);
        driverService.create(dmytro);
        driverService.create(bohdan);

        CarService carService = (CarService) injector.getInstance(CarService.class);
        carService.create(car1);
        carService.create(car2);
        carService.create(car3);*/

        System.out.println(manufacturerService.getAll());
        /*System.out.println(driverService.getAll());
        System.out.println(carService.getAll());*/

        /*manufacturerService.update(mercedes);
        manufacturerService.update(toyota);
        manufacturerService.update(volkswagen);*/

        /*driverService.update(ivan);
        driverService.update(dmytro);
        driverService.update(bohdan);*/

        System.out.println(manufacturerService.get(1L));
        System.out.println(manufacturerService.get(2L));
        System.out.println(manufacturerService.get(3L));

        /*System.out.println(driverService.get(1L));
        System.out.println(driverService.get(2L));
        System.out.println(driverService.get(3L));

        carService.addDriverToCar(ivan, car1);
        carService.addDriverToCar(ivan, car2);
        carService.addDriverToCar(ivan, car3);
        carService.addDriverToCar(bohdan, car2);
        carService.addDriverToCar(bohdan, car1);
        carService.addDriverToCar(dmytro, car1);

        System.out.println(carService.get(1L));
        System.out.println(carService.get(2L));
        System.out.println(carService.get(3L));

        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.getAllByDriver(2L));
        System.out.println(carService.getAllByDriver(3L));

        carService.removeDriverFromCar(bohdan, car1);
        carService.removeDriverFromCar(ivan, car2);
        carService.removeDriverFromCar(dmytro, car1);

        System.out.println(carService.getAllByDriver(1L));
        System.out.println(carService.getAllByDriver(2L));
        System.out.println(carService.getAllByDriver(3L));

        carService.delete(1L);
        System.out.println(carService.getAll());
        carService.delete(2L);
        System.out.println(carService.getAll());
        carService.delete(3L);
        System.out.println(carService.getAll());

        driverService.delete(1L);
        System.out.println(driverService.getAll());
        driverService.delete(2L);
        System.out.println(driverService.getAll());
        driverService.delete(3L);
        System.out.println(driverService.getAll());*/

        manufacturerService.delete(1L);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(2L);
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(3L);
        System.out.println(manufacturerService.getAll());
    }
}
