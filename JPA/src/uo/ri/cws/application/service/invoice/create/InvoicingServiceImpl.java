package uo.ri.cws.application.service.invoice.create;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.command.CreateInvoiceFor;
import uo.ri.cws.application.util.command.CommandExecutor;

/**
 * This service is intended to be used by the Cashier It follows the ISP
 * principle (@see SOLID principles from RC Martin)
 */
public class InvoicingServiceImpl implements InvoicingService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<String> workOrderIds)
			throws BusinessException {

		return executor.execute(new CreateInvoiceFor(workOrderIds));
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoicingWorkOrderDto> findNotInvoicedWorkOrdersByClientDni(
			String dni) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByPlateNumber(String plate)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceDto> findInvoiceByNumber(Long number)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoicingPaymentMeanDto> findPayMeansByClientDni(String dni)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleInvoice(String invoiceId, Map<String, Double> charges)
			throws BusinessException {
		// TODO Auto-generated method stub

	}

}
