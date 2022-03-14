package uo.ri.cws.domain;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

public class Charge extends BaseEntity {

	private double amount = 0.0;

	private Invoice invoice;

	private PaymentMean paymentMean;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isNotNull(paymentMean);
		this.amount = amount;
		paymentMean.pay(amount);
		this.paymentMean = paymentMean;
		Associations.Charges.link(paymentMean, this, invoice);

	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {

		ArgumentChecks.isTrue(invoice.getStatus() == InvoiceStatus.PAID);
		paymentMean.pay(-amount);
		Associations.Charges.unlink(this);
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

}
