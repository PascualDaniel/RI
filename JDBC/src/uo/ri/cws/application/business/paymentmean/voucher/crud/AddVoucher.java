package uo.ri.cws.application.business.paymentmean.voucher.crud;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import alb.util.random.Random;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class AddVoucher implements Command<Integer> {


	VoucherGateway gateway = PersistenceFactory.forVoucher();
	WorkOrderGateway workOrderGateway =  PersistenceFactory.forWorkOrder();
	InvoiceGateway invoiceGateway =  PersistenceFactory.forInvoice();
	ClientGateway clientGateway = PersistenceFactory.forClient();
	VehicleGateway vehicleGateway = PersistenceFactory.forVehicle();
	PaymentMeanGateway payGateway = PersistenceFactory.forPaymentmean();
	

	
	
	public AddVoucher() {
		super();
	}

	private int numOfCreatedVouchers =0;
	/**
     * Generate the vouchers following one or several policies:
     * 	- by total number of workorders in their vehicles
     * 	- by number of recommendations
     * 	- by invoices over 500 euros
     * 
     * @return a counter with the number of generated vouchers
     * DOES NOT @throws BusinessException.
     */
	@Override
	public Integer execute() throws BusinessException {
		
		List<ClientRecord> clientes  =clientGateway.findAll();
		
		for (ClientRecord clientRecord : clientes) {
			
			
			List<WorkOrderRecord> averiasSeleccionadas = check(clientRecord.id);
			if(averiasSeleccionadas!= null && !averiasSeleccionadas.isEmpty()) {
				
				List<WorkOrderRecord> tresAverias = new LinkedList<WorkOrderRecord>();
				for (int i = 0; i < averiasSeleccionadas.size(); i++) {
					tresAverias.add(averiasSeleccionadas.get(i));
					if((i+1)%3==0) {
						for (WorkOrderRecord orders : tresAverias) {
							workOrderGateway.markAsUsed(orders.id);	
							tresAverias = new LinkedList<WorkOrderRecord>();
						}
						createVoucher(clientRecord.id);
					}
					
				}
				
				
				
				
			
			}
			
		}
		
		
		
		return numOfCreatedVouchers;
	}
	
	
	
	private void createVoucher(String clientid) {
		String id = UUID.randomUUID().toString();
		
		PaymentmeanRecord paymentMeanRecord = new PaymentmeanRecord();
		
		VoucherRecord voucherRecord = new VoucherRecord();
	
		paymentMeanRecord.id= id;
		paymentMeanRecord.client_id=clientid;
		paymentMeanRecord.dtype="VOUCHER";
		paymentMeanRecord.accumulated = 00.0;
		
		voucherRecord.id=id;
		voucherRecord.code="B"+Random.integer(0, 9999);
		voucherRecord.accumulated=00.0;
		voucherRecord.description="By three workorders";
		voucherRecord.client_id=clientid;
		voucherRecord.available=20.0;
		
		
		payGateway.add(paymentMeanRecord);
		gateway.add(voucherRecord);
		
		numOfCreatedVouchers++;
	}
	
	/**
a) El cliente tiene 3 averías facturadas en el taller.
b) La factura o facturas de esas 3 averías debe estar pagada.
La descripción del campo de este cupón será "Por tres ordenes de trabajo".
Las averías utilizadas para generar este bono, no deberían haberse utilizado para generar otro 
bono y no se pueden volver a utilizar para generar otro bon
	 */
	private List<WorkOrderRecord> check(String clientId) {
		if(clientId == null ||clientId.isEmpty()) {
			return null;
		}
		
		if (clientGateway.findById(clientId).isPresent()) {
			List<VehicleRecord> vehiculos= vehicleGateway.findByClientId(clientId);
		
			List<WorkOrderRecord> averias= new LinkedList<WorkOrderRecord>();
			for (VehicleRecord vehicleRecord : vehiculos) {
				averias.addAll( workOrderGateway.getInvoicedByVehicleID(vehicleRecord.id));
			}
			if(averias.size() >=3) {
				List<WorkOrderRecord> averiasSeleccionadas= new LinkedList<WorkOrderRecord>();
			
				for (WorkOrderRecord workOrderRecord : averias) {
					
					if(!workOrderRecord.usedForVoucher) {
						Optional<InvoiceRecord> re =   invoiceGateway.findById(workOrderRecord.invoice_id);
						if(re.isPresent() &&  re.get().status.equals("PAID")) {
							averiasSeleccionadas.add(workOrderRecord);
						
						}
					}
					
					
				}
				return averiasSeleccionadas;
				
				
			}
		}
		
		return null;
	}
	
	

}
