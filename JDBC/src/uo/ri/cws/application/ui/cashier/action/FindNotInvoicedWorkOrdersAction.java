package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;

public class FindNotInvoicedWorkOrdersAction implements Action {

	
	InvoicingService service = BusinessFactory.forInvoicingService();
	

	@Override
	public void execute() throws BusinessException {
		String dni = Console.readString("Client DNI ");
		
		List<InvoicingWorkOrderDto> list = service.findNotInvoicedWorkOrdersByClientDni(dni);
		
		Console.println("\nClient's not invoiced work orders\n");  
		
		for (InvoicingWorkOrderDto dto : list) {
			Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",  
					dto.id
					,dto.description
					, dto.date
					,dto.status
					,dto.total 
				);
		}
		
	
				
	
	}

}