package uo.ri.cws.application.persistence.client.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class ClientGatewayImpl implements ClientGateway{

	
	private Conf conf = Conf.getInstance();
	
	public ClientGatewayImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void add(ClientRecord t) {
		//DNI, nombre, apellidos, dirección 
		// postal, número de teléfono y correo electrónico.
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			//insert into TClients(id, dni, name, surname,zipcode,street,city,phone,email) values (?, ?, ?, ?,?, ?, ?, ?)
			pst = c.prepareStatement(conf.getProperty("CLIENT_ADD"));
			pst.setString(1, t.id);
			pst.setString(2, t.dni);
			pst.setString(3, t.name);
			pst.setString(4, t.surname);
			pst.setString(5, t.zipcode);
			pst.setString(6, t.street);
			pst.setString(7, t.city);
			pst.setString(8, t.phone);
			pst.setString(9, t.email);

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

			pst = c.prepareStatement(conf.getProperty("CLIENT_DELETE"));
			pst.setString(1, id);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void update(ClientRecord t) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			//update TClients set name = ?, surname = ? ,phone =? ,email =?,zipcode =?,city =?,street =? where id = ?
			pst = c.prepareStatement(conf.getProperty("CLIENT_UPDATE"));
			pst.setString(1, t.name);
			pst.setString(2, t.surname);
			pst.setString(3, t.phone);
			pst.setString(4, t.email);
			pst.setString(5, t.zipcode);
			pst.setString(6, t.city);
			pst.setString(7, t.street);
			
			pst.setString(8, t.id);
			

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<ClientRecord> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("CLIENT_FIND_BY_ID"));

			pst.setString(1, id);

			rs = pst.executeQuery();

			return RecordAssembler.toClientRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<ClientRecord> findAll() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(conf.getProperty("CLIENT_FINDALL"));
			rs = pst.executeQuery();

			return RecordAssembler.toClientRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<ClientRecord> findByDni(String dni) {

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("CLIENT_FIND_BY_DNI"));

			pst.setString(1, dni);

			rs = pst.executeQuery();

			return RecordAssembler.toClientRecord(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
