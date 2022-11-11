package dcom.ssdbackend.api.domain.glass.repository;

import dcom.ssdbackend.api.domain.dispenser.Dispenser;
import dcom.ssdbackend.api.domain.glass.Glass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlassRepository extends JpaRepository<Glass, String> {
    List<Glass> findAllByDispenser(Dispenser dispenser);
}
