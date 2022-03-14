package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;

public class InvoiceGatewayImpl implements InvoiceGateway {

	private Conf conf = Conf.getInstance();

	public InvoiceGatewayImpl() {
		// TODO Auto-generated constructor stub
	}

	// INSERT_INVOICE = insert into TInvoices(id, number, date, vat, amount, status)
	// values(?, ?, ?, ?, ?, ?)
	@Override
	public void add(InvoiceRecord t) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("INSERT_INVOICE"));
			pst.setString(1, t.id);
			pst.setLong(2, t.number);
			pst.setDate(3, Date.valueOf(t.date));
			pst.setDouble(4, t.vat);
			pst.setDouble(5, t.amount);
			pst.setString(6, t.status);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<InvoiceRecord> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("FIND_INVOICE_BY_ID"));

			pst.setString(1, id);

			rs = pst.executeQuery();

			return RecordAssembler.toInvoiceRecord(rs);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public List<InvoiceRecord> findAll() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(conf.getProperty("FINDALL_INVOICES"));
			rs = pst.executeQuery();

			return RecordAssembler.toInvoiceRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<InvoiceRecord> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLastInvoiceNumber() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(conf.getProperty("LAST_INVOICE_NUMBER"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
