package uo.ri.cws.application.business.client.crud.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.chashes.CashGateway;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.recommendations.RecommendationGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.voucher.VoucherGateway;

public class DeleteClient implements Command<Void>{

	
	
	private String idClient;
	
	ClientGateway clientGateway =PersistenceFactory.forClient();
	VehicleGateway vehicleGateway = PersistenceFactory.forVehicle();
	RecommendationGateway recommendationGateway = PersistenceFactory.forRecommendation();
	
	PaymentMeanGateway pamentGateway = PersistenceFactory.forPaymentmean();
	CashGateway cashGateway = PersistenceFactory.forCash();
	CreditCardGateway cardGateway = PersistenceFactory.forCreditCard();
	VoucherGateway voucherGateway = PersistenceFactory.forVoucher();		
	
	public DeleteClient(String idClient) {
		if(idClient ==null || idClient.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.idClient=idClient;
	}
	
	 //Chequear id -> chequear vehiculos -> borrar recomendaciones hizo -> borrar recomendaciones recividas->borrar metodos de pago->borrar cliente.
	 
	/**
	 * @param idClient the id of the client to be deleted
	 * @throws 		BusinessException if the client does not exist or if there are vehicles
	 * registered for this client
	 * @throws		IllegalArgumentException when argument is null or empty string
	 */
	@Override
	public Void execute() throws BusinessException {
		
	
		if(clientGateway.findById(idClient).isPresent()) {
			if(vehicleGateway.findByClientId(idClient).isEmpty()) {
				recommendationGateway.removeRecomendationsByRecommended(idClient);
				recommendationGateway.removeRecomendationsBySponsor(idClient);
				List<PaymentmeanRecord> records = pamentGateway.getListByClientID(idClient);
				for (PaymentmeanRecord paymentMeanRecord : records) {
					String id = paymentMeanRecord.id;
					cashGateway.remove(id);
					cardGateway.remove(id);
					voucherGateway.remove(id);
					pamentGateway.remove(id);
					
				}
				
				
				
				clientGateway.remove(idClient);
				
			}else {
				throw new BusinessException("Tiene vehiculos");
			}
		}else {
			throw new BusinessException("No existe este cliente");
		}
		
		return null;
	}

}
