package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository extends BaseJpaRepository<Mechanic>
		implements MechanicRepository {

	@Override
	public Optional<Mechanic> findByDni(String dni) {
		List<Mechanic> result = Jpa.getManager()
				.createNamedQuery("Mechanic.findByDni", Mechanic.class)
				.setParameter(1, dni).getResultList();
		if (result.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(result.get(0));
	}

}
