package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Substitution extends BaseEntity {

	private int quantity;

	private SparePart sparePart;

	private Intervention intervention;

	Substitution() {
	}

	public Substitution(int quantity, SparePart sparePart,
			Intervention intervention) {
		super();
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(intervention);
		ArgumentChecks.isTrue(quantity > 0);
		this.quantity = quantity;
		Associations.Sustitute.link(sparePart, this, intervention);
	}

	public Substitution(SparePart sparePart, Intervention intervention,
			int quantity) {
		this(quantity, sparePart, intervention);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((intervention == null) ? 0 : intervention.hashCode());
		result = prime * result
				+ ((sparePart == null) ? 0 : sparePart.hashCode());
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
		Substitution other = (Substitution) obj;
		if (intervention == null) {
			if (other.intervention != null)
				return false;
		} else if (!intervention.equals(other.intervention))
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart
				+ ", intervention=" + intervention + "]";
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public double getAmount() {

		return quantity * sparePart.getPrice();
	}

	public int getQuantity() {
		return quantity;
	}

}
