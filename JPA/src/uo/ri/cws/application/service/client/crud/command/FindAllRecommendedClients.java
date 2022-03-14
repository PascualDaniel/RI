package uo.ri.cws.application.service.client.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.Recommendation;

public class FindAllRecommendedClients implements Command<List<ClientDto>> {

	private String id;

	private ClientRepository clientRep = Factory.repository.forClient();

	public FindAllRecommendedClients(String id) {
		ArgumentChecks.isNotEmpty(id);
		this.id = id;
	}

	@Override
	public List<ClientDto> execute() throws BusinessException {
		List<Client> result = new ArrayList<Client>();
		Optional<Client> sponsor = clientRep.findById(id);
		if (sponsor.isPresent()) {
			Set<Recommendation> reco = sponsor.get()._getSponsored();
			for (Recommendation recommendation : reco) {
				result.add(recommendation.getRecommended());
			}
		}
		return DtoAssembler.toClientDtoList(result);
	}

}
