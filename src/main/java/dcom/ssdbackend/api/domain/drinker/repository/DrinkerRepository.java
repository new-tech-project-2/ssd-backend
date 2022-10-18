package dcom.ssdbackend.api.domain.drinker.repository;

import dcom.ssdbackend.api.domain.drinker.Drinker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkerRepository extends JpaRepository<Drinker, String> {
}
