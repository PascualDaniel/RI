package uo.ri.cws.application.persistence.creditcard;

import java.time.LocalDate;

import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;

public class CreditCardRecord extends PaymentmeanRecord {
	public String number;
	public LocalDate validthru;
	public String type;
}
