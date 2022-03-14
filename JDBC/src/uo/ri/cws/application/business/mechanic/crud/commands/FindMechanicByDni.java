package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;

public class FindMechanicByDni implements Command< Optional<MechanicDto>>{

	private String dni;
	MechanicGateway gateway = PersistenceFactory.forMechanic();

	public FindMechanicByDni(String dni) {
		if(dni==null || dni.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.dni = dni;
	}

	public Optional<MechanicDto> execute() {

		Optional<MechanicRecord> response = gateway.findByDni(dni);

		return DtoAssembler.toDto(response);

	}

}
