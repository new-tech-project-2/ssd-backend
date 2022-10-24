package dcom.ssdbackend.api.domain.dispenser.repository;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispenserRepository extends JpaRepository<Dispenser, String> {
}
