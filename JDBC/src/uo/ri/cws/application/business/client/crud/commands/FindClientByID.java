package uo.ri.cws.application.business.client.crud.commands;

import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;

public class FindClientByID implements Command<Optional<ClientDto>>{

	private String id;
	
	ClientGateway gateway =PersistenceFactory.forClient();
	
	public FindClientByID(String id) {
		if(id==null || id.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.id = id;
	}	
	
	
	@Override
	public Optional<ClientDto> execute() throws BusinessException {
		
		Optional<ClientRecord> response = gateway.findById(id);

		return DtoAssembler.toClientDto(response);

	}

}
