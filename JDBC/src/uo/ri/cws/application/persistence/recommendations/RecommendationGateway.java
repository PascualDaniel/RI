package uo.ri.cws.application.persistence.recommendations;

import java.util.List;

import persistencia.Gateway;

public interface RecommendationGateway extends Gateway<RecommendationRecord> {

	
	
	
	
	public List<String> getRecommendationsBySponsorId(String SponsorId);;
	
	public void removeRecomendationsBySponsor(String SponsorId);
	public void removeRecomendationsByRecommended(String recommendedID);
}
