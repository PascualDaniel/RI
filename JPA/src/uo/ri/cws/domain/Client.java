package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Client extends BaseEntity {

	private String dni;

	private String name;

	private String surname;
	private String email;
	private String phone;

	private Address address;

	private Set<Vehicle> vehicles = new HashSet<Vehicle>();

	private Set<PaymentMean> paymentMeans = new HashSet<PaymentMean>();

	private Recommendation recommended;
	private Set<Recommendation> sponsored = new HashSet<Recommendation>();

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	Client() {
	}

	public Client(String dni) {
		this(dni, "no-name", "no-surname", "no-email", "no-phone", null);
	}

	public Client(String dni, String name, String surname) {
		this(dni, name, surname, "no-email", "no-phone", null);

	}

	public Client(String dni, String name, String surname, String email,
			String phone, Address address) {

		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(email);
		ArgumentChecks.isNotEmpty(phone);

		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(dni, other.dni);
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<Vehicle>(vehicles);
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<PaymentMean>(paymentMeans);
	}

	public Recommendation getRecommended() {
		return recommended;
	}

	public void _setRecommended(Recommendation recommended) {
		this.recommended = recommended;
	}

	public Set<Recommendation> getSponsored() {
		return new HashSet<>(sponsored);
	}

	public Set<Recommendation> _getSponsored() {
		return sponsored;
	}

	public void _setSponsored(Set<Recommendation> sponsored) {
		this.sponsored = sponsored;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", phone=" + phone + ", address="
				+ address + "]";
	}

	Set<Vehicle> _getVehicle() {
		return vehicles;
	}

	public void setAddress(Address address) {
		this.address = address;

	}

}
