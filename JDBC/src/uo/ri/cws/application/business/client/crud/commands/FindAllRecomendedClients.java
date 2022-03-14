package uo.ri.cws.application.business.client.crud.commands;

import java.util.LinkedList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.util.DtoAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.recommendations.RecommendationGateway;

public class FindAllRecomendedClients implements Command<List<ClientDto>> {

private String id;
	
	ClientGateway clientGateway =PersistenceFactory.forClient();
	RecommendationGateway recomendationGateway =PersistenceFactory.forRecommendation();
	
	public FindAllRecomendedClients(String id) {
		if(id==null || id.isBlank())
			throw new IllegalArgumentException("No puede ser nulo o vacio");
		this.id = id;
	}	
	/**
	 * @param sponsorID The id of the client who recommended the workshop to this client
	 * @return the dto for the clients or empty if there is none recommended by the argument
	 * @throws IllegalArgumentException when argument is null or empty string
	 *        DOES NOT throw BusinessException
	 */
	@Override
	public List<ClientDto> execute() throws BusinessException {
		
		 List<ClientDto> result = new  LinkedList<ClientDto>();
		if(clientGateway.findById(id).isPresent()) {
			 List<String> list= recomendationGateway.getRecommendationsBySponsorId(id);
			
			for (String string : list) {
				result.add( DtoAssembler.toClientDto( clientGateway.findById(string).get()) );
			}
			
			
		}
		return result;
		
		
	}

}
