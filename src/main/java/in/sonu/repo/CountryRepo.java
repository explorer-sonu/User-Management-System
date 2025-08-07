package in.sonu.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sonu.entities.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer>{

}
