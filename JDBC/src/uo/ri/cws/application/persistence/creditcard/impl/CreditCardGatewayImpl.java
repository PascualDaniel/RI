package uo.ri.cws.application.persistence.creditcard.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.creditcard.CreditCardRecord;
import uo.ri.cws.application.persistence.creditcard.CreditCardGateway;
import uo.ri.cws.application.persistence.util.Conf;

public class CreditCardGatewayImpl implements CreditCardGateway {

	
	private Conf conf = Conf.getInstance();
	
	
	@Override
	public void add(CreditCardRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("REMOVE_CREDITCARD"));
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(CreditCardRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<CreditCardRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CreditCardRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
