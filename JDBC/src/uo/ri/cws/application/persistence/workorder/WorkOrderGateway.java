package uo.ri.cws.application.persistence.workorder;

import java.util.List;

import persistencia.Gateway;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

	
	//LINK_WORKORDER_TO_INVOICE      = update TWorkOrders set invoice_id = ? where id = ?
	void linkWorkOrderToInvoice(String invoiceId, String workOrderId );
	//MARK_WORKORDER_AS_INVOICED     = update TWorkOrders set status = 'INVOICED' where id = ?
	void markWorkOrderToInvoice(String workOrderId);
	

	List<WorkOrderRecord> getNotInvoicedByVehicleID(String vehicleId);
	List<WorkOrderRecord> getInvoicedByVehicleID(String vehicleId);
	
	void markAsUsed(String workOrderId);
	List<WorkOrderRecord> getByMechanicID(String mechanicId);
	
}
