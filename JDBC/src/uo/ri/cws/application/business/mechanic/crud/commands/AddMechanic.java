package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class AddMechanic implements Command<MechanicDto>{

	private MechanicDto dto;
	
	MechanicGateway gateway =PersistenceFactory.forMechanic();
	
	public AddMechanic(MechanicDto dto) {
		if(dto==null)
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dto=dto;
	}
	
	
	/**
	 * Add a new mechanic to the system with the data specified in the dto.
	 * 		The id value will be ignored as it is generated here.
	 * @param mecanich A dto containing info to be added
	 * @return dto with the id value set to the UUID generated 
	 * @throws IllegalArgumentException when argument is null or dni is null or empty string
	 * @throws BusinessException if there already exists another mechanic with the same dni
	 */
	public MechanicDto execute() throws BusinessException {
		
		if(dto.dni==null || dto.dni.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		
		String id = UUID.randomUUID().toString();
	
		MechanicDto result = new MechanicDto();
		result.id=id;
		result.dni=dto.dni;
		result.name=dto.name;
		result.surname= dto.surname;		
		
		MechanicRecord record = DtoAssembler.toRecord(result);
		
		if(gateway.findByDni(dto.dni).isPresent())
			throw new BusinessException("Dni Repetido");
		
		gateway.add(record);
		
		return result;
		
	}
	
}
