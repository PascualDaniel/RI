package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;

public class FindClientById implements Command<Optional<ClientDto>> {

	private String id;

	private ClientRepository cRep = Factory.repository.forClient();

	public FindClientById(String id) {
		ArgumentChecks.isNotNull(id);
		this.id = id;
	}

	@Override
	public Optional<ClientDto> execute() throws BusinessException {
		validateInput();
		Optional<Client> client = cRep.findById(id);
		Optional<ClientDto> result = Optional.empty();
		if (client.isPresent()) {
			result = Optional.of(DtoAssembler.toDto(client.get()));
		}
		return result;
	}

	private void validateInput() throws BusinessException {
		ArgumentChecks.isNotEmpty(id, "The client Id can not be empty");
	}

}
