package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class FindMechanicById  implements Command<Optional<MechanicDto>>{

	private String id;
	
	MechanicGateway gateway =PersistenceFactory.forMechanic();
	
	public FindMechanicById(String id) {
		if(id==null || id.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.id = id;
	}



	public Optional<MechanicDto> execute() {

		Optional<MechanicRecord> response = gateway.findById(id);

		return DtoAssembler.toDto(response);

	}

}
