package in.sonu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sonu.entities.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer>{
	
	List<StateEntity> findByCountryCountryId(Integer countryId);
}
