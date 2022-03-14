package uo.ri.cws.application.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.paymentmean.PaymentmeanRecord;
import uo.ri.cws.application.persistence.vehicle.VehicleRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class RecordAssembler {

	public static Optional<MechanicRecord> toMechanicRecord(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToMechanicRecord(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<MechanicRecord> toMechanicRecordList(ResultSet rs) throws SQLException {
		List<MechanicRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToMechanicRecord(rs));
		}

		return res;
	}
/*
 * public double amount;	// total amount (money) vat included
	public double vat;		// amount of vat (money)
	public long number;		// the invoice identity, a sequential number
	public LocalDate date;		// of the invoice
	public String status;	// the status as in FacturaStatus
 */
	private static InvoiceRecord resultSetToInvoiceRecord(ResultSet rs) throws SQLException {
		InvoiceRecord value = new InvoiceRecord();
		value.id = rs.getString("id");
		value.amount = rs.getDouble("amount");
		value.vat = rs.getDouble("vat");
		value.number = rs.getLong("number");
		value.date = rs.getDate("date").toLocalDate();
		value.status=rs.getString("status");
		
		return value;
	}
	
	
	public static Optional<InvoiceRecord> toInvoiceRecord(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToInvoiceRecord(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	
	public static List<InvoiceRecord> toInvoiceRecordList(ResultSet rs) throws SQLException {
		List<InvoiceRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToInvoiceRecord(rs));
		}
		return res;
	}

	
	private static MechanicRecord resultSetToMechanicRecord(ResultSet rs) throws SQLException {
		MechanicRecord value = new MechanicRecord();
		value.id = rs.getString("id");

		value.dni = rs.getString("dni");
		value.name = rs.getString("name");
		value.surname = rs.getString("surname");
		return value;
	}
	
	
	//WorkOrder
	

	public static Optional<WorkOrderRecord> toWorkOrderRecord ( ResultSet rs ) throws SQLException {
		WorkOrderRecord record = null;
		
		if (rs.next()) {
			record = resultSetToWorkOrderRecord(rs);
			}
		return Optional.ofNullable(record);
		
	}

	public static List<WorkOrderRecord> toWorkOrderRecordList(ResultSet rs) throws SQLException {
		List<WorkOrderRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToWorkOrderRecord(rs)	);
		}
		
		return res;
	}
	
	
	private static WorkOrderRecord resultSetToWorkOrderRecord ( ResultSet rs ) throws SQLException {
		WorkOrderRecord record = new WorkOrderRecord();
		
		record.id = rs.getString("id");
		record.version = rs.getLong("version");
		record.usedForVoucher= rs.getBoolean("usedforvoucher");
		record.vehicle_id = rs.getString( "vehicle_Id");
		record.description = rs.getString( "description");
		record.date = rs.getTimestamp("date").toLocalDateTime();
		record.amount = rs.getDouble("amount");
		record.status = rs.getString( "status");
		record.mechanic_id = rs.getString( "mechanic_Id");
		record.invoice_id = rs.getString( "invoice_Id");
	
		
		return record;		
	}


	public static Optional<ClientRecord> toClientRecord(ResultSet rs) throws SQLException {
		ClientRecord record = null;
		
		if (rs.next()) {
			record = resultSetToClientRecord(rs);
			}
		return Optional.ofNullable(record);
	}
	/*
	 * 	public String id;
		public String dni;
		public String name;
		public String email;
		public String  phone;
		public String surname;
		public String city;
		public String street;
		public int zipcode; 
	 */
	
	private static ClientRecord resultSetToClientRecord ( ResultSet rs ) throws SQLException {
		ClientRecord record = new ClientRecord();
		
		record.id = rs.getString("id");
		record.dni = rs.getString("dni");
		record.version = rs.getLong("version");
		record.name = rs.getString( "name");
		record.surname = rs.getString( "surname");
		record.email = rs.getString( "email");
		record.phone = rs.getString( "phone");
		record.city = rs.getString( "city");
		record.street = rs.getString( "street");
		record.zipcode = rs.getString( "zipcode");


	
		
		return record;		
	}

	public static List<ClientRecord> toClientRecordList(ResultSet rs) throws SQLException {
		List<ClientRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToClientRecord(rs)	);
		}
		
		return res;
	}
	
	
	public static Optional<VehicleRecord> toVehicleRecord(ResultSet rs) throws SQLException {
		VehicleRecord record = null;
		
		if (rs.next()) {
			record = resultSetToVehicleRecord(rs);
			}
		return Optional.ofNullable(record);
	}
	/*
	 * 	public String id;
		public String brand;
		public String model;
		public String plateNumber;
		public String client_Id;
		public String VehicleType_Id;
	 */
	
	private static VehicleRecord resultSetToVehicleRecord ( ResultSet rs ) throws SQLException {
		VehicleRecord record = new VehicleRecord();
		
		record.id = rs.getString("id");
		record.version = rs.getLong("version");
		record.make = rs.getString("make");
		record.model = rs.getString("model");
		record.platenumber = rs.getString("plateNumber");
		record.client_id = rs.getString("client_Id");
		record.vehicletype_id = rs.getString("VehicleType_Id");
		
		return record;		
	}

	public static List<VehicleRecord> toVehicleRecordList(ResultSet rs) throws SQLException {
		List<VehicleRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToVehicleRecord(rs)	);
		}
		
		return res;
	}
	
	
	public static Optional<PaymentmeanRecord> toPaymentRecord(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToPaymentRecord(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	public static List<PaymentmeanRecord> toPaymentRecordList(ResultSet rs) throws SQLException {
		List<PaymentmeanRecord> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToPaymentRecord(rs));
		}

		return res;
	}
	private static PaymentmeanRecord resultSetToPaymentRecord(ResultSet rs) throws SQLException {
		PaymentmeanRecord value = new PaymentmeanRecord();
		value.id = rs.getString("id");
		value.client_id  = rs.getString("client_id");
		value.dtype  = rs.getString("dtype");
		value.accumulated  = rs.getDouble("accumulated");
		value.version = rs.getLong("version");
		

		return value;
	}
	

}
