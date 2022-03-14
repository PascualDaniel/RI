package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Mechanic extends BaseEntity {

	private String dni;
	private String surname;
	private String name;

	private Set<WorkOrder> assigned = new HashSet<>();
	private Set<Intervention> interventions = new HashSet<>();

	Mechanic() {

	}

	public Mechanic(String string) {
		this(string, "NO SURNAME", "NO NAME");

	}

	public Mechanic(String dni, String name, String surname) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
		this.surname = surname;
		this.dni = dni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dni);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
				+ name + "]";
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		ArgumentChecks.isNotEmpty(surname);
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

}
