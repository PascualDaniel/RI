package uo.ri.cws.application.business.client.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientCrudService;
import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.client.crud.commands.AddClient;
import uo.ri.cws.application.business.client.crud.commands.DeleteClient;
import uo.ri.cws.application.business.client.crud.commands.FindAllClients;
import uo.ri.cws.application.business.client.crud.commands.FindAllRecomendedClients;
import uo.ri.cws.application.business.client.crud.commands.FindClientByID;
import uo.ri.cws.application.business.client.crud.commands.UpdateClient;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class ClientCrudServiceImpl implements ClientCrudService{

	
	private  CommandExecutor exectutor = new CommandExecutor();
	
	@Override
	public ClientDto addClient(ClientDto client, String recommenderId) throws BusinessException {
		return exectutor.execute(new AddClient(client,recommenderId));
	}

	@Override
	public void deleteClient(String idClient) throws BusinessException {
		 exectutor.execute(new DeleteClient(idClient));
		
	}

	@Override
	public void updateClient(ClientDto client) throws BusinessException {
		 exectutor.execute(new UpdateClient(client));
		
	}

	@Override
	public List<ClientDto> findAllClients() throws BusinessException {
		return  exectutor.execute(new FindAllClients());
	}

	@Override
	public Optional<ClientDto> findClientById(String idClient) throws BusinessException {
		return  exectutor.execute(new FindClientByID(idClient));
	}

	@Override
	public List<ClientDto> findClientsRecommendedBy(String sponsorID) throws BusinessException {
		return exectutor.execute(new FindAllRecomendedClients(sponsorID));
	}

}
