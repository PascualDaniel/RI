package uo.ri.cws.application.persistence.paymentmean;

import java.util.List;

import persistencia.Gateway;

public interface PaymentMeanGateway extends Gateway<PaymentmeanRecord> {

	
	
	public void removeByClient(String client_id);
	
	List<PaymentmeanRecord> getListByClientID(String client_id);
}
