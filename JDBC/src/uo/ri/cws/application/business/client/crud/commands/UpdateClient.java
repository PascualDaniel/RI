package uo.ri.cws.application.business.client.crud.commands;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;

public class UpdateClient implements Command<Void>{

	
	private ClientDto dto;
	
	ClientGateway gateway =PersistenceFactory.forClient();
	
	public UpdateClient(ClientDto dto) {
		if(dto==null )
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dto = dto;
	}	
	
	/**
	 * Updates values for the client specified by the id field,
	 * 		except id and dni
	 * @param client A dto identifying the client to update by the field id,
	 * 					and new data in the other fields
	 * @throws BusinessException if the client does not exist 
	 * @throws IllegalArgumentException when the argument is null or id is null or empty 
	 */
	@Override
	public Void execute() throws BusinessException {
		ClientDto result = new ClientDto();
		if(dto.id==null || dto.id.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		result.id = dto.id;
		result.dni=dto.dni;
		result.name=dto.name;
		result.surname= dto.surname;
		result.phone= dto.phone;
		result.email= dto.email;
		result.addressZipcode= dto.addressZipcode;
		result.addressStreet= dto.addressStreet;
		result.addressCity= dto.addressCity;
		
		
		
		ClientRecord record = DtoAssembler.toClientRecord(result);
		
		if(gateway.findById(record.id).isEmpty())
			throw new BusinessException("No existe este cliente");
		
		gateway.update(record);
		
		

		return null;
	}

}
