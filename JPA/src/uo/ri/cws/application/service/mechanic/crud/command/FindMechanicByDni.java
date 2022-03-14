package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class FindMechanicByDni implements Command<Optional<MechanicDto>> {

	private String dni;

	private MechanicRepository mRep = Factory.repository.forMechanic();

	public FindMechanicByDni(String dni) {
		ArgumentChecks.isNotNull(dni);
		this.dni = dni;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		validateInput();
		Optional<Mechanic> mechanic = mRep.findByDni(dni);
		Optional<MechanicDto> result = Optional.empty();
		if (mechanic.isPresent()) {
			result = Optional.of(DtoAssembler.toDto(mechanic.get()));
		}
		return result;
	}

	private void validateInput() throws BusinessException {
		ArgumentChecks.isNotEmpty(dni, "The mechanic Dni can not be empty");

	}

}
