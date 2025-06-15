package gaetanZipp.store.store.repository;

import gaetanZipp.store.store.entities.Poste;
import gaetanZipp.store.store.entities.nomPoste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PosteRepository extends JpaRepository<Poste, UUID> {
    boolean existsDistinctByLibellePoste(nomPoste libellePoste);
}
