package dcom.ssdbackend.api.domain.glass.repository;

import dcom.ssdbackend.api.domain.glass.Glass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlassRepository extends JpaRepository<Glass, String> {
}
