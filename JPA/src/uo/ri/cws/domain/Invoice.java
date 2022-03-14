package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;
import uo.ri.cws.domain.base.BaseEntity;

public class Invoice extends BaseEntity {
	public enum InvoiceStatus {
		NOT_YET_PAID, PAID
	}

	private Long number;
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceStatus status = InvoiceStatus.NOT_YET_PAID;

	private Set<WorkOrder> workOrders = new HashSet<WorkOrder>();

	private Set<Charge> charges = new HashSet<Charge>();

	Invoice() {
	}

	public Invoice(Long number) {
		this(number, LocalDate.now(), new LinkedList<WorkOrder>());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, new LinkedList<WorkOrder>());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {

		ArgumentChecks.isNotNull(workOrders);
		ArgumentChecks.isNotNull(date);
		this.number = number;
		this.date = date;
		for (WorkOrder o : workOrders) {
			addWorkOrder(o);
		}

	}

	public Invoice(Long number, LocalDate date, double amount, double vat,
			InvoiceStatus status) {
		super();
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isTrue(number >= 0);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isTrue(amount >= 0);
		ArgumentChecks.isTrue(vat >= 0);
		ArgumentChecks.isNotNull(status);
		this.number = number;
		this.date = LocalDate.from(date);
		this.amount = amount;
		computeAmount();
		this.vat = vat;
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double total = 0;
		for (WorkOrder w : workOrders) {
			total += (w.getAmount() * (getVatType() + 1));
		}
		this.amount = Math.rint(total * 100) / 100;
	}

	private double getVatType() {
		LocalDate JULY_1_2012 = LocalDate.of(2012, 7, 1);
		return date.isAfter(JULY_1_2012) ? 0.21 : 0.18;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount
	 * and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(status == InvoiceStatus.NOT_YET_PAID,
				"the invoice status is not NOT_YET_PAID");
		StateChecks.isTrue(workOrder.getStatus() == WorkOrderStatus.FINISHED,
				"the workOrder is not FINISEHED");
		computeAmount();
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		workOrders.add(workOrder);
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(status == InvoiceStatus.NOT_YET_PAID,
				"the invoice status is not NOT_YET_PAID");
		computeAmount();
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		workOrders.remove(workOrder);
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts
	 *                               paid with charges to payment means do not
	 *                               cover the total of the invoice
	 */
	public void settle() {
		StateChecks.isTrue(isNotSettled());
		this.status = InvoiceStatus.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return LocalDate.from(date);
	}

	public double getAmount() {
		computeAmount();
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceStatus getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount="
				+ amount + ", vat=" + vat + ", status=" + status + "]";
	}

	public boolean isNotSettled() {
		return status.equals(InvoiceStatus.NOT_YET_PAID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

}
