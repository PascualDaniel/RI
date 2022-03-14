package uo.ri.cws.application.business.mechanic.crud.commands;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class DeleteMechanic  implements Command<Void>{

	private String idMechanic;
	
	MechanicGateway gateway =PersistenceFactory.forMechanic();
	WorkOrderGateway workOrderGateway = PersistenceFactory.forWorkOrder();
	public DeleteMechanic(String idMechanic) {
		if(idMechanic==null || idMechanic.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.idMechanic=idMechanic;
	}
	
	
	/**
	 * @param idMechanic the id of the mechanid to be deleted
	 * @throws 		BusinessException if the mechanic does not exist or if there are work orders
	 * registered for this mechanic
	 * @throws		IllegalArgumentException when argument is null or empty string
	 */
	public Void execute() throws BusinessException {
		
		if(gateway.findById(idMechanic).isEmpty())
			throw new BusinessException("No existe mecanico");
		if (!workOrderGateway.getByMechanicID(idMechanic).isEmpty()) {
			throw new BusinessException("Tiene WorkOrders Abiertas");
		}
		gateway.remove(idMechanic);
		
		return null;
	}
	
}
