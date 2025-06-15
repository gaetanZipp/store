package gaetanZipp.store.store.repository;

import gaetanZipp.store.store.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, UUID> {
    List<Employe> findEmployeByPoste_Id(UUID id);
    List<Employe> findEmployeByDepartement_Id(UUID id);
    boolean existsDistinctByNom(String nom);
    boolean existsDistinctByDateEmbauche(LocalDate dateEmbauche);
}
