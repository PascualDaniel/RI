package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;

public class WorkOrderRecord {

	public String id;
	public long version;
	public String status;
	public String description;
	public String vehicle_id;
	public LocalDateTime date;
	public double amount;
	public String mechanic_id;
	public String invoice_id;
	public boolean usedForVoucher;

	

}
