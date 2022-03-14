package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Vehicle extends BaseEntity {

	private String plateNumber;

	private String make;
	private String model;

	private Client client;

	private VehicleType vehicleType;

	private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

	Vehicle() {

	}

	public Vehicle(String plateNumber) {
		super();
		ArgumentChecks.isNotEmpty(plateNumber);
		this.plateNumber = plateNumber;
	}

	public Vehicle(String plateNumber, String make, String model) {
		this(plateNumber);
		ArgumentChecks.isNotEmpty(make);
		ArgumentChecks.isNotEmpty(model);
		this.make = make;
		this.model = model;

	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

	public Client getClient() {
		return client;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	void _setClient(Client c) {
		this.client = c;
	}

	void _setType(VehicleType t) {
		this.vehicleType = t;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<WorkOrder>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {

		return workOrders;
	}

}
