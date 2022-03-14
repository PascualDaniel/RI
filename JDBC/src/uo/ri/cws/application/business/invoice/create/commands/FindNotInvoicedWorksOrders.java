package uo.ri.cws.application.business.invoice.create.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class FindNotInvoicedWorksOrders implements Command<List<InvoicingWorkOrderDto>> {

	ClientGateway clientGateway = PersistenceFactory.forClient();
	VehicleGateway vehicleGateway = PersistenceFactory.forVehicle();
	WorkOrderGateway workOrderGateway = PersistenceFactory.forWorkOrder();
	private String dni;
	
	public FindNotInvoicedWorksOrders(String dni) {
		if(dni==null || dni.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dni = dni;
	}

	
	/**
	 * Process:
	 * 
	 *   - Ask customer dni
	 *    
	 *   - Display all uncharged workorder  
	 *   		(status <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, status, amount and description
	 * Find FINISHED BUT NOT INVOICED workorders due to a client with certain dni.
	 * @param the dni 
	 * @throws BusinessException if
	 * 	- client with this dni does not exist
	 *         IllegalArgumentException if dni is empty 
	 */
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		
		if (clientGateway.findByDni(dni).isEmpty()) {
			throw new BusinessException("No existe este cliente");
		}
		
		List<InvoicingWorkOrderDto> result = new LinkedList<InvoicingWorkOrderDto>();
			
		String id = getIDbyDNI(dni);
		List<String> vehiculos =getVehiclesIDs( id);
		
		
		List<WorkOrderRecord> woRecords= new LinkedList<WorkOrderRecord>();
		for (String string : vehiculos) {
			List<WorkOrderRecord> add = getFromWorkOrders(string);
			if(add!=null && !add.isEmpty())
				woRecords.addAll( add );
		}
		
		List<WorkOrderRecord> wofinished= new LinkedList<WorkOrderRecord>();
		
		for (WorkOrderRecord workOrderRecord : woRecords) {
			if(workOrderRecord!= null && workOrderRecord.status.equals("FINISHED") ) {
				wofinished.add(workOrderRecord);
			}
				
		}
		result =DtoAssembler.toInvoicingWorkOrderList(wofinished);
		
		return result;
	}
	
	private List<WorkOrderRecord> getFromWorkOrders(String VehicleId){
	
		//select * from TWorkOrders where vehicle_id = ? and status <> 'INVOICED';
		
		return  workOrderGateway.getNotInvoicedByVehicleID(VehicleId);
	}
	private List<String> getVehiclesIDs(String clientId){
		//select * from TVehicles where client_id = ?;
		//vehicleGateway
		List<String> result = new ArrayList<String>();
		
		List<VehicleRecord> records=  vehicleGateway.findByClientId(clientId);
		
		for (VehicleRecord vehicleRecord : records) {
			result.add(vehicleRecord.id);
		}
		
		return result;
	}
	private String getIDbyDNI(String Dni) {
		
		return clientGateway.findByDni(Dni).get().id;
	}
	
	
}
