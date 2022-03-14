package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

public class Intervention extends BaseEntity {

	private LocalDateTime date;
	private int minutes;

	private WorkOrder workOrder;

	private Mechanic mechanic;

	private Set<Substitution> substitutions = new HashSet<>();

	Intervention() {
	}

	public Intervention(WorkOrder workOrder, Mechanic mechanic) {
		super();
		this.date = LocalDateTime.now();
		Associations.Intervene.link(workOrder, this, mechanic);

	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder,
			LocalDateTime date, int minutes) {
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(mechanic);
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		;
		this.minutes = minutes;
		this.workOrder = workOrder;
		this.mechanic = mechanic;

		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public Intervention(Mechanic mechanic, WorkOrder workOrder, int i) {
		this(mechanic, workOrder, LocalDateTime.now(), i);
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>(substitutions);
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public LocalDateTime getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Intervention [date=" + date + ", minutes=" + minutes
				+ ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
	}

	public double getAmount() {
		double a = 0;
		for (Substitution s : substitutions) {
			if (s.getIntervention() == this) {
				a += s.getAmount();
			}
		}
		return (workOrder.getVehicle().getVehicleType().getPricePerHour()
				* ((double) minutes / 60)) + a;
	}

	public int getMinutes() {
		return minutes;
	}

}
