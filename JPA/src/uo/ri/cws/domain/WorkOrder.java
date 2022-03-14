package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class WorkOrder extends BaseEntity {
	public enum WorkOrderStatus {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	private LocalDateTime date;
	private String description;
	private double amount = 0.0;

	private WorkOrderStatus status = WorkOrderStatus.OPEN;

	private Vehicle vehicle;

	private Mechanic mechanic;

	private Invoice invoice;

	private boolean usedForVoucher;

	private Set<Intervention> interventions = new HashSet<>();

	WorkOrder() {

	}

	public WorkOrder(Vehicle vehicle) {
		this(vehicle, LocalDateTime.now(), "no description");

	}

	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now(), description);

	}

	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(description);

		Associations.Fix.link(vehicle, this);
		this.description = description;
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date.toString() + ", vehicle="
				+ vehicle.toString() + "]";
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderStatus getStatus() {
		return status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is
	 * called from Invoice.addWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or -
	 *                               The work order is not linked with the
	 *                               invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(status != WorkOrderStatus.ASSIGNED);
		StateChecks.isNotNull(invoice);
		this.status = WorkOrderStatus.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               state, or - The work order is not linked
	 *                               with a mechanic
	 */
	public void markAsFinished() {
		StateChecks.isTrue(status == WorkOrderStatus.ASSIGNED,
				"The work order is not in ASSIGNED status");
		StateChecks.isNotNull(mechanic);
		computeAmount();
		this.status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method
	 * is called from Invoice.removeWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or -
	 *                               The work order is still linked with the
	 *                               invoice
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(status == WorkOrderStatus.INVOICED,
				"The work order is not in OPEN status");
		StateChecks.isNull(invoice);
		computeAmount();
		this.status = WorkOrderStatus.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its status
	 * to ASSIGNED
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN status,
	 *                               or - The work order is already linked with
	 *                               another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		StateChecks.isTrue(status == WorkOrderStatus.OPEN,
				"The work order is not in OPEN status");
		StateChecks.isNull(this.mechanic);
		this.status = WorkOrderStatus.ASSIGNED;
		Associations.Assign.link(mechanic, this);
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * status back to OPEN
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED
	 *                               status
	 */
	public void desassign() {
		StateChecks.isTrue(status == WorkOrderStatus.ASSIGNED,
				"The work order is not in ASSIGNED status");
		Associations.Assign.unlink(mechanic, this);
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be
	 * moved back to OPEN state and unlinked from the previous mechanic.
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED
	 *                               status
	 */
	public void reopen() {
		StateChecks.isTrue(status == WorkOrderStatus.FINISHED,
				"The work order is not in FINISHED status");
		this.status = WorkOrderStatus.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	private void computeAmount() {
		this.amount = 0.0;
		for (Intervention i : interventions) {
			this.amount += +i.getAmount();
		}
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public boolean isInvoiced() {

		return status == WorkOrderStatus.INVOICED;
	}

	public boolean isFinished() {

		return status == WorkOrderStatus.FINISHED;
	}

	public boolean isUsedForVoucher() {
		return usedForVoucher;
	}

	public void setUsedForVoucher(boolean usedForVoucher) {
		this.usedForVoucher = usedForVoucher;
	}

}
