package gaetanZipp.store.store.service;

import gaetanZipp.store.store.entities.Poste;
import gaetanZipp.store.store.repository.PosteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PosteService {
    private final PosteRepository posteRepository;

    //Ajouter un nouveau poste en BD
    public Poste addPoste(Poste poste) {
        // Vérifie si le libellé existe déjà
        if (posteRepository.existsDistinctByLibellePoste(poste.getLibellePoste())) {
            throw new IllegalArgumentException("Ce poste existe déjà.");
        }
        return posteRepository.save(poste);
    }

    //Recuperer la liste de tous les postes qui existe dans la BD par son ID
    public List<Poste> getAllPostes() {
        return posteRepository.findAll();
    }

    // Recupérer un poste qui existe dans la BD par son ID
    public Optional<Poste> getPosteById(UUID id) {
        return posteRepository.findById(id);
    }

    //Modifier les informations sur un poste (existingPoste) deja existant en BD
    public Poste updatePoste(UUID id, Poste updatePoste) {
        return  posteRepository.findById(id).map(
            existingPoste -> {
                existingPoste.setLibellePoste(updatePoste.getLibellePoste());
                existingPoste.setSalaireMax(updatePoste.getSalaireMax());
                existingPoste.setSalaireMin(updatePoste.getSalaireMin());
                return posteRepository.save(existingPoste);
            }
        ).orElseThrow(() -> new RuntimeException("Poste non trouvé !"));
    }

    //Supprimer un poste qui existe dans la BD par son ID
    public void deletePoste(UUID id) {
        if (!posteRepository.existsById(id)) {
            throw new RuntimeException("Le poste avec id " + id + " n'existe pas.");
        }
        posteRepository.deleteById(id);
    }
}
