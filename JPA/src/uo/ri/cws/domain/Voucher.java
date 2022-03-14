package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class Voucher extends PaymentMean {

	private String code;
	private double available = 0.0;
	private String description;

	Voucher() {

	}

	public Voucher(String code, String description, double available) {
		super();
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		this.code = code;
		this.description = description;
		this.available = available;
	}

	public Voucher(Client c, String code, String description,
			double available) {
		this(code, description, available);
		Associations.Pay.link(this, c);

	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the
	 * available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isTrue(available >= amount, "Not enough available to pay");
		super.pay(amount);
		this.available -= amount;
	}

	public double getAvailable() {

		return available;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Voucher other = (Voucher) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
