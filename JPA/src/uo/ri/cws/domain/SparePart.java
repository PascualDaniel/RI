package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class SparePart extends BaseEntity {

	private String code;
	private String description;
	private double price;

	private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {
	}

	public SparePart(String code) {
		super();
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
	}

	public SparePart(String code, String description, double price) {
		this(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isTrue(price >= 0);
		this.description = description;
		this.price = price;
	}

	public Set<Substitution> getSustitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description
				+ ", price=" + price + "]";
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Set<Substitution> getSubstitutions() {
		return substitutions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code);
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
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

}
