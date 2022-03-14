package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import alb.util.jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.util.RecordAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private Conf conf = Conf.getInstance();

	// public String id;

	// public String status;
	// public String description;
	// public String vehicle_id;
	// public LocalDateTime date;
	// public double amount;
	// public String mechanic_id;
	// public String invoice_id;

	@Override
	public void add(WorkOrderRecord t) {
//		Connection c = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//
//		try {
//			c = Jdbc.getCurrentConnection();
//
//			pst = c.prepareStatement(conf.getProperty(""));
//			pst.setString(1, t.id);
//			pst.setString(2, t.description);
//			pst.setString(3, t.vehicle_id);
//			pst.setDate(  4, Date.valueOf( t.date.toLocalDate()));
//			pst.setDouble(5, t.amount);
//			pst.setString(6, t.mechanic_id);
//			pst.setString(7, t.invoice_id);
//
//			pst.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new PersistenceException(e);
//		} finally {
//			Jdbc.close(rs, pst);
//		}
//		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WorkOrderRecord t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WorkOrderRecord> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("FIND_WORKORDER"));

			pst.setString(1, id);

			rs = pst.executeQuery();

			return RecordAssembler.toWorkOrderRecord(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderRecord> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	// LINK_WORKORDER_TO_INVOICE = update TWorkOrders set invoice_id = ? where id =
	// ?
	@Override
	public void linkWorkOrderToInvoice(String invoiceId, String workOrderId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("LINK_WORKORDER_TO_INVOICE"));
			pst.setString(1, invoiceId);
			pst.setString(2, workOrderId);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	// MARK_WORKORDER_AS_INVOICED = update TWorkOrders set status = 'INVOICED' where
	// id = ?
	@Override
	public void markWorkOrderToInvoice(String workOrderId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("MARK_WORKORDER_AS_INVOICED"));

			pst.setString(1, workOrderId);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}

	}

	@Override
	public List<WorkOrderRecord> getNotInvoicedByVehicleID(String vehicleId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("WORKORDER_GET_NOT_INVOICED_AND_BY_VEHICLE"));

			pst.setString(1, vehicleId);

			rs = pst.executeQuery();

			return RecordAssembler.toWorkOrderRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderRecord> getInvoicedByVehicleID(String vehicleId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("WORKORDER_GET_INVOICED_AND_BY_VEHICLE"));

			pst.setString(1, vehicleId);

			rs = pst.executeQuery();

			return RecordAssembler.toWorkOrderRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}
	

	@Override
	public List<WorkOrderRecord> getByMechanicID(String mechanicId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("WORKORDER_GET_BY_MECHANIC"));

			pst.setString(1, mechanicId);

			rs = pst.executeQuery();

			return RecordAssembler.toWorkOrderRecordList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void markAsUsed(String workOrderId) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(conf.getProperty("WORKORDER_MARK_AS_USED"));

			pst.setString(1, workOrderId);

			pst.executeUpdate();


		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
