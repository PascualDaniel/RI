package uo.ri.cws.application.service.paymentmean.voucher.command;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import alb.util.random.Random;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class AddVouchers implements Command<Integer> {

	private int numOfCreatedVouchers = 0;

	private ClientRepository clientRep = Factory.repository.forClient();
	private PaymentMeanRepository payRep = Factory.repository.forPaymentMean();

	@Override
	public Integer execute() throws BusinessException {
		List<Client> clientes = clientRep.findAll();
		for (Client client : clientes) {
			List<WorkOrder> averiasSeleccionadas = check(client);
			if (averiasSeleccionadas != null
					&& !averiasSeleccionadas.isEmpty()) {

				List<WorkOrder> tresAverias = new LinkedList<WorkOrder>();
				for (int i = 0; i < averiasSeleccionadas.size(); i++) {
					tresAverias.add(averiasSeleccionadas.get(i));
					if ((i + 1) % 3 == 0) {
						for (WorkOrder orders : tresAverias) {
							orders.setUsedForVoucher(true);
							tresAverias = new LinkedList<WorkOrder>();
						}
						createVoucher(client);
					}

				}
			}
		}
		return numOfCreatedVouchers;
	}

	private void createVoucher(Client client) {
		Voucher v = new Voucher(client, "B" + Random.integer(0, 9999),
				"By three workorders", 20);
		payRep.add(v);
		numOfCreatedVouchers++;
	}

	private List<WorkOrder> check(Client client) {
		if (client == null) {
			return null;
		}

		Set<Vehicle> vehiculos = client.getVehicles();

		Set<WorkOrder> averias = new HashSet<WorkOrder>();
		for (Vehicle vehicle : vehiculos) {
			averias.addAll(vehicle.getWorkOrders());
		}
		if (averias.size() >= 3) {
			List<WorkOrder> averiasSeleccionadas = new LinkedList<WorkOrder>();

			for (WorkOrder workOrder : averias) {

				if (!workOrder.isUsedForVoucher()) {
					Invoice re = workOrder.getInvoice();
					if (re != null && !re.isNotSettled()) {
						averiasSeleccionadas.add(workOrder);

					}
				}

			}
			return averiasSeleccionadas;

		}

		return null;
	}

}
