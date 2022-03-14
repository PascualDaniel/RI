package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class MechanicGatewayImpl implements MechanicGateway {

	private Conf conf = Conf.getInstance();

	@Override
	public void add(MechanicRecord t) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("ADD_MECHANIC"));
			pst.setString(1, t.id);
			pst.setString(2, t.dni);
			pst.setString(3, t.name);
			pst.setString(4, t.surname);

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

			pst = c.prepareStatement(conf.getProperty("DELETE_MECHANIC"));
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(MechanicRecord record) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("UPDATE_MECHANIC"));
			pst.setString(1, record.name);
			pst.setString(2, record.surname);
			pst.setString(3, record.id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public Optional<MechanicRecord> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("FIND_BY_ID"));

			pst.setString(1, id);

			rs = pst.executeQuery();

			return RecordAssembler.toMechanicRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public List<MechanicRecord> findAll() {

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(conf.getProperty("FINDALL_MECHANICS"));
			rs = pst.executeQuery();

			return RecordAssembler.toMechanicRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public Optional<MechanicRecord> findByDni(String dni) {

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("FIND_BY_DNI"));

			pst.setString(1, dni);

			rs = pst.executeQuery();

			return RecordAssembler.toMechanicRecord(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

}
