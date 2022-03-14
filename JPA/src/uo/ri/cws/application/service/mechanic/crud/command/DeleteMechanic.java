package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;

	private MechanicRepository mRep = Factory.repository.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId);
		ArgumentChecks.isNotEmpty(mechanicId,
				"The mechanic Id can not be empty");
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {
		Mechanic m = checkMechanicExist();
		checkMechanicCanBeRemoved(m);
		deleteMechanic(m);
		return null;
	}

	private void deleteMechanic(Mechanic m) {
		mRep.remove(m);
	}

	private Mechanic checkMechanicExist() throws BusinessException {
		Optional<Mechanic> targetMechanic = mRep.findById(mechanicId);
		BusinessChecks.isFalse(targetMechanic.isEmpty(),
				"No mechanic with this id");
		return targetMechanic.get();

	}

	private void checkMechanicCanBeRemoved(Mechanic m)
			throws BusinessException {
		BusinessChecks.isTrue(m.getAssigned().isEmpty(),
				"Can't remove a Mechanic with Assigned WorkOrders");
		BusinessChecks.isTrue(m.getInterventions().isEmpty(),
				"Can't remove a Mechanic with Interventions");

	}

}
