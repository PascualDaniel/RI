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

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;

	private MechanicRepository mRep = Factory.repository.forMechanic();

	public FindMechanicById(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		validateInput();
		Optional<Mechanic> mechanic = mRep.findById(id);
		Optional<MechanicDto> result = Optional.empty();
		if (mechanic.isPresent()) {
			result = Optional.of(DtoAssembler.toDto(mechanic.get()));
		}
		return result;
	}

	private void validateInput() throws BusinessException {
		ArgumentChecks.isNotEmpty(id, "The mechanic Id can not be empty");
	}

}
