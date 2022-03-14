package uo.ri.cws.application.business.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.invoice.InvoicingWorkOrderDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherDto;
import uo.ri.cws.application.persistence.client.ClientRecord;
import uo.ri.cws.application.persistence.invoice.InvoiceRecord;
import uo.ri.cws.application.persistence.mechanic.MechanicRecord;
import uo.ri.cws.application.persistence.voucher.VoucherRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderRecord;

public class DtoAssembler {

	public static MechanicDto toMechanicDto(ResultSet m) throws SQLException {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getString("id");
		//dto.version = m.getLong("version");

		dto.dni = m.getString("dni");
		dto.name = m.getString("name");
		dto.surname = m.getString("surname");
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(ResultSet rs) throws SQLException {
		List<MechanicDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toMechanicDto( rs ) );
		}

		return res;
	}
	
	public static InvoicingWorkOrderDto toWorkOrderForInvoicingDto(ResultSet rs) throws SQLException {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();

		
		
		dto.id = rs.getString("id");
		dto.description = rs.getString("Description");
		Date sqlDate = rs.getDate( "date");
		dto.date =  sqlDate.toLocalDate(); 
		dto.total = rs.getDouble("amount");
		dto.status = rs.getString("status");

		return dto;
	}

	
	public static List<InvoicingWorkOrderDto> toWorkOrderForInvoicingDtoList(ResultSet rs) throws SQLException {
		List<InvoicingWorkOrderDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( toWorkOrderForInvoicingDto( rs ) );
		}

		return res;
	}

	public static List<MechanicDto> toDtoList(List<MechanicRecord> arg) {
		List<MechanicDto> result = new ArrayList<MechanicDto> ();
		for (MechanicRecord mr : arg) 
			result.add(toMechanicDto(mr));
		return result;
	}
	
	public static MechanicRecord toRecord(MechanicDto arg) {
		MechanicRecord result = new MechanicRecord ();
		result.id = arg.id;
		result.dni = arg.dni;
		result.name = arg.name;
		result.surname = arg.surname;
		return result;
	}

	public static MechanicDto toMechanicDto(MechanicRecord arg) {

		MechanicDto result = new MechanicDto();
		result.id = arg.id;
		result.name = arg.name;
		result.surname = arg.surname;
		result.dni = arg.dni;
		return result;
	}
	
	public static Optional<MechanicDto> toDto(Optional<MechanicRecord> arg) {
		Optional<MechanicDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toMechanicDto(arg.get()));
		return result;
	}

	public static InvoiceDto toDto(InvoiceRecord arg) {
		InvoiceDto result = new InvoiceDto();
		result.id = arg.id;
		result.number = arg.number;
		result.status = arg.status;
		result.date = arg.date;
		result.total = arg.amount;
		result.vat = arg.vat;
		return result;
	}

	public static List<InvoicingWorkOrderDto> toInvoicingWorkOrderList(List<WorkOrderRecord> arg) {
		List<InvoicingWorkOrderDto> result = new ArrayList<InvoicingWorkOrderDto> ();
		for (WorkOrderRecord record : arg) 
			result.add(toDto(record));
		return result;
	}

	
	
	private static InvoicingWorkOrderDto toDto(WorkOrderRecord record) {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();
		dto.id = record.id;
		dto.date = record.date.toLocalDate();
		dto.description = record.description;
		dto.date = record.date.toLocalDate();
		dto.status = record.status;
		dto.total = record.amount;
		
		return dto;
	}

	public static ClientRecord toClientRecord(ClientDto dto) {
		ClientRecord result = new ClientRecord ();
		result.id  	    = dto.id  		;
		result.dni      = dto.dni       ;
		result.name     = dto.name      ;
		result.surname	= dto.surname   ;
		result.email    = dto.email     ;
		result.phone    = dto.phone 	;
		result.zipcode  = dto.addressZipcode   ;
		result.street   = dto.addressStreet    ;
		result.city   = dto.addressCity        ;
		result.version  	    = dto.version  ;
		return result;
	}
	
	public static ClientDto toClientDto(ClientRecord record) {
		ClientDto result = new ClientDto ();
		result.id  	    = record.id  		;
		result.dni      = record.dni       ;
		result.name     = record.name      ;
		result.surname	= record.surname   ;
		result.email    = record.email     ;
		result.phone    = record.phone 	   ;
		result.addressZipcode  = record.zipcode   ;
		result.addressCity  = record.city   ;
		result.addressStreet   = record.street    ;
		return result;
	}
	

	public static List<ClientDto> toClientDtoList(List<ClientRecord> list) {
		List<ClientDto> result = new ArrayList<ClientDto> ();
		for (ClientRecord record : list) 
			result.add(toClientDto(record));
		return result;
	}

	public static Optional<ClientDto> toClientDto(Optional<ClientRecord> response) {
		Optional<ClientDto> result = response.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toClientDto(response.get()));
		return result;
	}
	
	
	public static VoucherDto toVoucherDto(VoucherRecord record) {
		VoucherDto result = new VoucherDto ();
		result.id=             record.id;
		result.accumulated=     record.accumulated;
		result.code=		    record.code;
		result.accumulated=		record.accumulated;
		result.description=		record.description;
		result.clientId=		record.client_id;
		result.balance=			record.available;
		return result;
	}
	
	public static VoucherRecord toRecord(VoucherDto record) {
		VoucherRecord result = new VoucherRecord ();
		result.id=              record.id;
		result.accumulated=     record.accumulated;
		result.code=		    record.code;
		result.accumulated=		record.accumulated;
		result.description=		record.description;
		result.client_id=		record.clientId;
		result.available=			record.balance;
		return result;
	}
	
	public static Optional<VoucherRecord> toVoucherRecord(Optional<VoucherDto> response) {
		Optional<VoucherRecord> result = response.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toRecord(response.get()));
		return result;
	}

	public static List<VoucherDto> toVoucherDtoList(List<VoucherRecord> list) {
		List<VoucherDto> result = new ArrayList<VoucherDto> ();
		for (VoucherRecord record : list) 
			result.add(toVoucherDto(record));
		return result;
	}

	public static Optional<VoucherDto> toVoucherDto(Optional<VoucherRecord> response) {
		Optional<VoucherDto> result = response.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toVoucherDto(response.get()));
		return result;
	}
	

	
	
}
