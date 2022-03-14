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

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;

	private MechanicRepository mRep = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotNull(dto.dni);
		ArgumentChecks.isNotEmpty(dto.dni, "The DNI can not be empty");
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {
		validateMechanic();
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		mRep.add(m);
		dto.id = m.getId();
		return dto;
	}

	private void validateMechanic() throws BusinessException {
		Optional<Mechanic> targetMechanic = mRep.findByDni(dto.dni);
		BusinessChecks.isTrue(targetMechanic.isEmpty());

	}

}
