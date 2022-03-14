package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

public class CreditCard extends PaymentMean {

	private String number;
	private String type;
	private LocalDate validThru;

	CreditCard() {

	}

	public CreditCard(String number) {
		super();
		ArgumentChecks.isNotEmpty(number);
		this.number = number;

	}

	public CreditCard(String number, String type, LocalDate validThru) {
		this(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		this.type = type;
		this.validThru = validThru;
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
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
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type
				+ ", validThru=" + validThru + "]";
	}

	@Override
	public void pay(double amount) {
		StateChecks.isTrue(validThru.isAfter(LocalDate.now()),
				"The creditCard date is not correct");
		super.pay(amount);

	}

}
