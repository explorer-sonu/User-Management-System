package in.sonu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sonu.entities.CityEntity;


public interface CityRepo extends JpaRepository<CityEntity, Integer> {

	public List<CityEntity> findByStateId(Integer stateId);

}
