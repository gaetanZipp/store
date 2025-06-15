package gaetanZipp.store.store.repository;

import gaetanZipp.store.store.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, UUID> {
    boolean existingDistinctByLibelleDepartement(String libelleDepartement);
}
