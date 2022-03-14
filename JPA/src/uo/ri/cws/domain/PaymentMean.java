package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.cws.domain.base.BaseEntity;

public abstract class PaymentMean extends BaseEntity {

	private double accumulated = 0.0;

	private Client client;

	private Set<Charge> charges = new HashSet<>();

	@Override
	public String toString() {
		return "PaymentMean [accumulated=" + accumulated + ", client=" + client
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentMean other = (PaymentMean) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		return true;
	}

	public void pay(double importe) {
		this.accumulated += importe;
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public double getAccumulated() {
		return accumulated;
	}

	public Client getClient() {
		return client;
	}

}
