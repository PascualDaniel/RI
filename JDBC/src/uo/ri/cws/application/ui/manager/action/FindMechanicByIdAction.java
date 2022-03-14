package uo.ri.cws.application.ui.manager.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicCrudService;
import uo.ri.cws.application.business.mechanic.MechanicDto;

public class FindMechanicByIdAction implements Action {

	MechanicCrudService service = BusinessFactory.forMechanicCrudService();

	@Override
	public void execute() throws BusinessException {

		String id = Console.readString("Id");

		Optional<MechanicDto> result = service.findMechanicById(id);

		if (result.isPresent()) {
			Console.printf("\t%s %s %s %s\n", result.get().id, result.get().dni, result.get().name,
					result.get().surname);
		} else {
			Console.println("No mechanic with that ID");
		}
	}

}
