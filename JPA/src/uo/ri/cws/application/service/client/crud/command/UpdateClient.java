package uo.ri.cws.application.service.client.crud.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ClientRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Address;
import uo.ri.cws.domain.Client;

public class UpdateClient implements Command<Void> {

	private ClientDto dto;
	private ClientRepository clientRep = Factory.repository.forClient();

	public UpdateClient(ClientDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotEmpty(dto.dni);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotEmpty(dto.surname);
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Client targetClient = checkClientExist();

		Address address = new Address(dto.addressStreet, dto.addressCity,
				dto.addressZipcode);
		targetClient.setAddress(address);

		targetClient.setEmail(dto.email);
		targetClient.setName(dto.name);
		targetClient.setPhone(dto.phone);
		targetClient.setSurname(dto.surname);
		targetClient.setDni(dto.dni);
		return null;
	}

	private Client checkClientExist() throws BusinessException {
		Optional<Client> targetClient = clientRep.findById(dto.id);
		BusinessChecks.isFalse(targetClient.isEmpty(),
				"No Client with this id");
		BusinessChecks.isTrue(targetClient.get().getVersion() == dto.version);
		return targetClient.get();

	}

}
