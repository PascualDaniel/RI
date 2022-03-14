package uo.ri.cws.application.persistence.vehicle;

import java.util.List;

import persistencia.Gateway;

public interface VehicleGateway extends Gateway<VehicleRecord>{

	
	
	List<VehicleRecord> findByClientId(String id);
}
