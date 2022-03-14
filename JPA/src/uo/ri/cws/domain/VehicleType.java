package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class VehicleType extends BaseEntity {

	private String name;
	private double pricePerHour;

	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {

	}

	public VehicleType(String name, double pricePerHour) {
		super();
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		this.pricePerHour = pricePerHour;
	}

	public VehicleType(String name) {
		this(name, 0);
	}

	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, pricePerHour);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name)
				&& Double.doubleToLongBits(pricePerHour) == Double
						.doubleToLongBits(other.pricePerHour);
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
				+ "]";
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
