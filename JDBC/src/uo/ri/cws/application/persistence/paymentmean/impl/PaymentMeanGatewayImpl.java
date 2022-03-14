package uo.ri.cws.application.persistence.paymentmean.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.paymentmean.PaymentMeanGateway;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class PaymentMeanGatewayImpl implements PaymentMeanGateway{

	private Conf conf = Conf.getInstance();
	@Override
	public void add(PaymentmeanRecord t) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			
//			public String id;
//			public String dtipe;
//			public double accumulated;
//			public String cliend_id;
			
			pst = c.prepareStatement(conf.getProperty("ADD_PAYMENTMEAN"));
			pst.setString(1, t.id);
			pst.setString(2, t.dtype);
			pst.setDouble(3, t.accumulated);
			pst.setString(4, t.client_id);

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

			pst = c.prepareStatement(conf.getProperty("REMOVE_PAYMENTMEAN"));
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}


	@Override
	public void update(PaymentmeanRecord t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PaymentmeanRecord> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentmeanRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeByClient(String client_id) {
		// PAYMENTMEAN_REMOVE_BY_CLIENT_ID
		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("PAYMENTMEAN_REMOVE_BY_CLIENT_ID"));
			pst.setString(1, client_id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		
		
	}


	@Override
	public List<PaymentmeanRecord> getListByClientID(String client_id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(conf.getProperty("PAYMENTMEAN_GET_BY_CLIENT_ID"));
			pst.setString(1, client_id);

			rs = pst.executeQuery();

			return RecordAssembler.toPaymentRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
