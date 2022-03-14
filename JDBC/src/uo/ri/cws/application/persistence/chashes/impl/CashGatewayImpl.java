package uo.ri.cws.application.persistence.chashes.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.chashes.CashGateway;
import uo.ri.cws.application.persistence.chashes.CashRecord;
import uo.ri.cws.application.persistence.util.Conf;

public class CashGatewayImpl implements CashGateway {

	
	private Conf conf = Conf.getInstance();
	
	@Override
	public void add(CashRecord t) {
				Connection c = null;
				PreparedStatement pst = null;
				ResultSet rs = null;

				try {
					c = Jdbc.getCurrentConnection();
					pst = c.prepareStatement(conf.getProperty("ADD_CASH"));
					pst.setString(1, t.id);
		

					pst.executeUpdate();

				} catch (SQLException e) {
					throw new PersistenceException(e);
				} finally {
					Jdbc.close(rs, pst);
				}
	}

	@Override
	public void remove(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("REMOVE_CASH"));
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(CashRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<CashRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CashRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
