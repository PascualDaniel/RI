package uo.ri.cws.application.persistence.recommendations.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.recommendations.RecommendationGateway;
import uo.ri.cws.application.persistence.recommendations.RecommendationRecord;
import uo.ri.cws.application.persistence.util.Conf;

public class RecommendationGatewayImpl implements RecommendationGateway {

	private Conf conf = Conf.getInstance();
	
	@Override
	public void add(RecommendationRecord t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(RecommendationRecord t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<RecommendationRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecommendationRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRecommendationsBySponsorId(String SponsorId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<String> result = new ArrayList<>();
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("RECOMMENDATION_GET_RECOMENDEDS"));

		
			pst.setString(1, SponsorId);

			rs = pst.executeQuery();
			
			while(rs.next()) {
				result.add(rs.getString(1));
			}

			return result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void removeRecomendationsBySponsor(String SponsorId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("RECOMMENDATION_REMOVE_BY_SPONSOR"));
			pst.setString(1, SponsorId);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
	}

	@Override
	public void removeRecomendationsByRecommended(String recommendedID) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("RECOMMENDATION_REMOVE_BY_RECOMENDED"));
			pst.setString(1, recommendedID);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
		
	}

}
