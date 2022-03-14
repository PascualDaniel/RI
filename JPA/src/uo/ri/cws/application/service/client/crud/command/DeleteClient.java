package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Recommendation;

public class DeleteClient implements Command<Void> {

	private String clientId;

	private ClientRepository cRep = Factory.repository.forClient();
	private RecommendationRepository recRep = Factory.repository
			.forRecomendacion();
	private PaymentMeanRepository payRep = Factory.repository.forPaymentMean();

	public DeleteClient(String clientId) {
		ArgumentChecks.isNotNull(clientId);
		ArgumentChecks.isNotEmpty(clientId, "The client Id can not be empty");
		this.clientId = clientId;
	}

	@Override
	public Void execute() throws BusinessException {
		Client client = checkClientExist();
		checkClientCanBeRemoved(client);

		removeRecommedations(client);
		removePaymentMeans(client);

		deleteClient(client);

		return null;
	}

	private void removePaymentMeans(Client client) {
		Set<PaymentMean> pays = client.getPaymentMeans();
		for (PaymentMean paymentMean : pays) {
			payRep.remove(paymentMean);
		}

	}

	private void removeRecommedations(Client client) {
		Set<Recommendation> res = client._getSponsored();

		for (Recommendation recommendation : res) {
			recRep.remove(recommendation);
		}
		Recommendation rec = client.getRecommended();
		if (rec != null)
			recRep.remove(rec);

	}

	private void deleteClient(Client c) {
		cRep.remove(c);
	}

	private Client checkClientExist() throws BusinessException {
		Optional<Client> targetClient = cRep.findById(clientId);
		BusinessChecks.isFalse(targetClient.isEmpty(),
				"No Client with this id");
		return targetClient.get();

	}

	private void checkClientCanBeRemoved(Client m) throws BusinessException {
		BusinessChecks.isTrue(m.getVehicles().isEmpty());
	}

}
