package uo.ri.cws.application.business.invoice.create.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import alb.util.math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class CreateInvoice implements Command<InvoiceDto> {

	InvoiceGateway invoiceGateway = PersistenceFactory.forInvoice();
	WorkOrderGateway workOrderGateway = PersistenceFactory.forWorkOrder();

	private List<String> workOrderIds;

	public CreateInvoice(List<String> workOrderIds) {
		if(workOrderIds==null || workOrderIds.isEmpty())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.workOrderIds = workOrderIds;
	}
	
	
	/**
	 * Create a new invoice billing the workorders in the argument
	 * The new invoice will be in NOT_YET_PAID status, the workorders will be marked
	 * as INVOICED
	 * @param the list of workorder ids to bill
	 * @throws BusinessException if
	 * 	- the status of any of the workorders is not FINISHED 
	 *  - any of the workorders does not exist
	 * @throws IllegalArgumentException if list is null, empty or any of the strings in the
	 *  		list is empty or null
	 */
	public InvoiceDto execute() throws BusinessException {

		InvoiceDto result = new InvoiceDto();

		
			if (!checkWorkOrdersExist(workOrderIds))
				throw new BusinessException("Workorder does not exist");
			if (!checkWorkOrdersFinished(workOrderIds))
				throw new BusinessException("Workorder is not finished yet");

			long numberInvoice = generateInvoiceNumber();
			LocalDate dateInvoice = LocalDate.now();
			double amount = calculateTotalInvoice(workOrderIds); // vat not included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat / 100); // vat included
			total = Round.twoCents(total);

			String idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
			linkWorkordersToInvoice(idInvoice, workOrderIds);
			markWorkOrderAsInvoiced(workOrderIds);

			// displayInvoice(numberInvoice, dateInvoice, amount, vat, total);
			result.number = numberInvoice;
			result.date = dateInvoice;
		//	result.amount = amount;
			result.vat = vat;
			result.total = total;
			result.id =idInvoice;
			return result;
		
	}

	/*
	 * checks whether every work order exist
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws BusinessException {

		for (String workOrderID : workOrderIDS) {
			if(workOrderID==null || workOrderID.isBlank())
				throw new IllegalArgumentException("No puede ser nulo o vacio");
			if (workOrderGateway.findById(workOrderID).isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS) throws BusinessException {

		for (String workOrderID : workOrderIDS) {
			Optional<WorkOrderRecord> record = workOrderGateway.findById(workOrderID);
			if (!"FINISHED".equalsIgnoreCase(record.get().status)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber()  {
		return invoiceGateway.getLastInvoiceNumber();
	}

	/*
	 * Compute total amount of the invoice (as the total of individual work orders'
	 * amount
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private Double getWorkOrderTotal(String workOrderID) throws  BusinessException {

		Double money = 0.0;

		Optional<WorkOrderRecord> record = workOrderGateway.findById(workOrderID);

		if (record.isEmpty()) {
			throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
		}

		money = record.get().amount;

		return money;

	}

	/*
	 * returns vat percentage
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(long numberInvoice, LocalDate dateInvoice, double vat, double total)
			 {

		InvoiceRecord record = new InvoiceRecord();

		String idInvoice;
		idInvoice = UUID.randomUUID().toString();

		record.id = idInvoice;
		record.number = numberInvoice;
		record.amount = total;
		record.vat = vat;
		record.date = dateInvoice;
		record.status = "NOT_YET_PAID";

		invoiceGateway.add(record);

		return idInvoice;
	}

	/*
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDS)  {

		for (String breakdownId : workOrderIDS)
			workOrderGateway.linkWorkOrderToInvoice(invoiceId, breakdownId);

	}

	/*
	 * Sets status to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids)  {

		for (String id : ids) {
			workOrderGateway.markWorkOrderToInvoice(id);
		}

	}

}
