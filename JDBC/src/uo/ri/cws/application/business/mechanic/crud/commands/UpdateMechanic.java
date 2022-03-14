package uo.ri.cws.application.business.mechanic.crud.commands;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class UpdateMechanic implements Command<Void>{

	private MechanicDto dto;

	MechanicGateway gateway =PersistenceFactory.forMechanic();
	
	public UpdateMechanic(MechanicDto dto) {
		if(dto==null)
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dto = dto;
	}

	/**
	 * Updates values for the mechanic specified by the id field,
	 * 		just name and surname will be updated
	 * @param mechanic A dto identifying the mechanic to update by the field id,
	 * 					and the data to update in name and surname
	 * @throws BusinessException if the mechanic does not exist 
	 * @throws IllegalArgumentException when the argument is null or any of the fields (id, dni, name, surname) are null or empty 
	 */
	public Void execute() throws BusinessException {
		
		if(dto.id==null || dto.id.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		if(dto.dni==null || dto.dni.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		if(dto.name==null || dto.name.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		if(dto.surname==null || dto.surname.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		
		
		MechanicDto result = new MechanicDto();
		result.id = dto.id;
		result.dni=dto.dni;
		result.name=dto.name;
		result.surname= dto.surname;

		
		MechanicRecord record = DtoAssembler.toRecord(result);
		
		
		if(!gateway.findById(dto.id).isPresent())
			throw new BusinessException("No existe este mecanico");
		
		gateway.update(record);
		
		

		return null;

	}

}
