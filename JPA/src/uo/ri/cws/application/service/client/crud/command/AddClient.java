package uo.ri.cws.application.service.client.crud.command;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.Recommendation;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class AddClient implements Command<ClientDto> {

	private ClientDto dto;
	private String recommenderId;
	private ClientRepository clientRep = Factory.repository.forClient();
	private RecommendationRepository recRep = Factory.repository
			.forRecomendacion();
	private PaymentMeanRepository payRep = Factory.repository.forPaymentMean();

	public AddClient(ClientDto dto, String recommenderId) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.dni, "The DNI can not be empty");

		this.dto = dto;
		this.recommenderId = recommenderId;
	}

	@Override
	public ClientDto execute() throws BusinessException {
		validateClient();
		Address address = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		Client client = new Client(dto.dni, dto.name, dto.surname, dto.email,
				dto.phone, address);

		clientRep.add(client);

		Cash cash = new Cash(client);
		payRep.add(cash);
		if (recommenderId != null && !recommenderId.isBlank())
			createRecommendation(client.getId());

		dto.id = client.getId();

		return dto;
	}

	private void validateClient() throws BusinessException {
		Optional<Client> targetClient = clientRep.findByDni(dto.dni);
		BusinessChecks.isTrue(targetClient.isEmpty());
	}

	private void createRecommendation(String id) {
		if (validateSponsor()) {
			Recommendation recommendation = new Recommendation();
			recRep.add(recommendation);

		}

	}

	private boolean validateSponsor() {
		Optional<Client> cliente = clientRep.findById(recommenderId);

		if (cliente.isEmpty()) {
			return false;
		}

		Set<Vehicle> vehiculos = cliente.get().getVehicles();

		Set<WorkOrder> workOrders = new HashSet<>();

		if (!vehiculos.isEmpty()) {
			for (Vehicle vehicle : vehiculos) {
				workOrders.addAll(vehicle.getWorkOrders());
			}
			if (!workOrders.isEmpty()) {
				for (WorkOrder workOrder : workOrders) {
					Invoice invoice = workOrder.getInvoice();
					if (invoice != null
							&& invoice.getStatus() == InvoiceStatus.PAID)
						return true;
				}
			}
		}

		return false;
	}

}
