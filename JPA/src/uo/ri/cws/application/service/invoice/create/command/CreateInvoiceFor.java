package uo.ri.cws.application.service.invoice.create.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		ArgumentChecks.isTrue(!workOrderIds.isEmpty());
		for (String string : workOrderIds) {
			ArgumentChecks.isNotEmpty(string);
		}
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		InvoiceDto dto = new InvoiceDto();

		List<WorkOrder> workOrders = checkWorkOrdersExist();
		checkFinishedWO(workOrders);
		BusinessChecks.isTrue(workOrders.size() == workOrderIds.size());
		Long number = generateInvoiceNumber();
		Invoice invoice = new Invoice(number, LocalDate.now(), workOrders);
		dto.id = invoice.getId();
		dto.date = invoice.getDate();
		dto.number = invoice.getNumber();
		dto.total = invoice.getAmount();
		dto.vat = invoice.getVat();

		invsRepo.add(invoice);
		return dto;

	}

	private List<WorkOrder> checkFinishedWO(List<WorkOrder> workOrders)
			throws BusinessException {
		List<WorkOrder> result = new ArrayList<WorkOrder>();
		for (WorkOrder workOrder : workOrders) {
			BusinessChecks.isTrue(workOrder.isFinished());

		}
		return result;
	}

	private long generateInvoiceNumber() {
		return invsRepo.getNextInvoiceNumber();
	}

	private List<WorkOrder> checkWorkOrdersExist() throws BusinessException {
		List<WorkOrder> result = wrkrsRepo.findByIds(workOrderIds);
		BusinessChecks.isNotNull(result);
		return result;

	}

}
