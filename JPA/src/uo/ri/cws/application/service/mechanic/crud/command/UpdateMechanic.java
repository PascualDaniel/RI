package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;
	private MechanicRepository mRep = Factory.repository.forMechanic();

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotEmpty(dto.dni);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotEmpty(dto.surname);
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		Mechanic targetMechanic = checkMechanicExist();
		targetMechanic.setName(dto.name);
		targetMechanic.setSurname(dto.surname);
		return null;
	}

	private Mechanic checkMechanicExist() throws BusinessException {
		Optional<Mechanic> targetMechanic = mRep.findById(dto.id);
		BusinessChecks.isFalse(targetMechanic.isEmpty(),
				"No mechanic with this id");
		BusinessChecks.isTrue(targetMechanic.get().getVersion() == dto.version);
		return targetMechanic.get();

	}
}
