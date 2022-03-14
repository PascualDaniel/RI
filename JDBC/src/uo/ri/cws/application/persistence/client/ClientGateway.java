package uo.ri.cws.application.persistence.client;

import java.util.Optional;

import persistencia.Gateway;

public interface ClientGateway extends Gateway<ClientRecord>{

	
	
	/*
	 * Finds a row in the table
	 * @param record's field to retrieve (dni)
	 * @return dto from that record, probably null
	 */
	Optional<ClientRecord> findByDni(String dni);
}
