package uo.ri.cws.application.business.client.crud.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.chashes.CashGateway;
import uo.ri.cws.application.persistence.chashes.CashRecord;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.recommendations.RecommendationGateway;
import uo.ri.cws.application.persistence.recommendations.RecommendationRecord;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class AddClient implements Command<ClientDto> {

	private ClientDto dto;
	private String recommenderId;
	
	ClientGateway gateway =PersistenceFactory.forClient();
	
	InvoiceGateway invoiceGateway = PersistenceFactory.forInvoice();
	
	VehicleGateway vehicleGateway  = PersistenceFactory.forVehicle();
	
	RecommendationGateway recommendationGateway = PersistenceFactory.forRecommendation();
	
	WorkOrderGateway workOrderGateway = PersistenceFactory.forWorkOrder();
	
	PaymentMeanGateway payGateway = PersistenceFactory.forPaymentmean();
	CashGateway cashGateway = PersistenceFactory.forCash();
	
	public AddClient(ClientDto dto, String recommenderId) {
		
		if(dto ==null )
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dto=dto;
		this.recommenderId= recommenderId;
	}
	
	/**
	 * Add a new client to the system with the data specified in the dto.
	 * 		The id value will be ignored as it is generated here.
	 * @param client A dto containing info to be added
	 * @param 
	 * @return dto with the id value set to the UUID generated 
	 * @throws IllegalArgumentException when argument is null or dni is null or empty string
	 * @throws BusinessException if there already exists another client with the same dni
	 */
	@Override
	public ClientDto execute() throws BusinessException {
		if(dto.dni==null || dto.dni.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		
		if(gateway.findByDni(dto.dni).isPresent())
			throw new BusinessException("Dni Repetido");
		
		String id ;
		if(dto.id==null || dto.id.isBlank())
			id = UUID.randomUUID().toString();
		else 
			id=dto.id;
		
		ClientDto result = new ClientDto();
		
		result.id=id;
		result.version=dto.version;
		result.dni=dto.dni;
		result.name=dto.name;
		result.surname= dto.surname;	
		result.email =dto.email;
		result.phone = dto.phone;
		result.addressZipcode = dto.addressZipcode;
		result.addressStreet = dto.addressStreet;
		result.addressCity = dto.addressCity;

		ClientRecord  record = DtoAssembler.toClientRecord(result);
		gateway.add(record);
		
		
		PaymentmeanRecord payment = new PaymentmeanRecord();
		CashRecord cash = new CashRecord();
		
		
		String payment_Id = UUID.randomUUID().toString();
		payment.id=payment_Id;
		cash.id = payment_Id;
		payment.client_id=id;
		cash.client_id=id;
		payment.dtype ="CASH";
		
		payGateway.add(payment);
		cashGateway.add(cash);
		
		if(recommenderId !=null && !recommenderId.isBlank())
			createRecommendation(id);
		
		
		return result;
	}
	
	
	private boolean validateSponsor() {
		
		if(gateway.findById(recommenderId).isEmpty()) {
			return false;
		}
		
		List<VehicleRecord> vehiculos = vehicleGateway.findByClientId(recommenderId);
		List<WorkOrderRecord> records  = new LinkedList<WorkOrderRecord>();
		
		if (!vehiculos.isEmpty()) {
			for (VehicleRecord vehicleRecord : vehiculos) {
				records.addAll(workOrderGateway.getInvoicedByVehicleID(vehicleRecord.id));
			}
			if (!records.isEmpty()) {
				for (WorkOrderRecord workOrderRecord : records) {
					if (invoiceGateway.findById(workOrderRecord.invoice_id).get().status == "PAID") {
						return true;
					}
				} 
			} 
		}
		return false;
	}
	
	private void createRecommendation(String clientID) {
		
		if (validateSponsor()) {
			RecommendationRecord record = new RecommendationRecord();
			record.id = UUID.randomUUID().toString();
			record.sponsor_id = recommenderId;
			record.recommended_id = clientID;
			recommendationGateway.add(record);
		}
		
		
		
	}
	

	
	
}
