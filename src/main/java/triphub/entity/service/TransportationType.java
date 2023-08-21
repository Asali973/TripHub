package triphub.entity.service;

public enum TransportationType {
	Plane("Plane"),
	Train("Train"),
	Bus("Bus"), 
	Boat("Boat"), 
	Bike("Bike"), 
	Motorcycle("Motorcyle"),
	Car("Car"),
	Campingcar("Camping Car"),
	ElectricCar("Electric Car"),
	ElectricBike("Electric Bike"),
	ElectricMotorcycle("Electric Motorcycle"),
	ElectricScooter("Electric Scooter");
	
	 private String label;

    TransportationType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
