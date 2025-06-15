package gaetanZipp.store.store.service;

import gaetanZipp.store.store.entities.Departement;
import gaetanZipp.store.store.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartementService {
    private final DepartementRepository departementRepository;

    //Ajouter un nouveau departement en BD
    public Departement addDepartement(Departement departement) {
        //On vérifie pour voir le departement n'existe pas deja
        if(departementRepository.existsDistinctByLibelleDepartement(departement.getLibelleDepartement())) {
            throw new IllegalArgumentException("Ce département existe déjà.");
        }
        return departementRepository.save(departement);
    }

    //Recuperer la liste de tous les departements qui existe dans la BD
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    //Recuperer un departement qui existe dans la BD par son ID
    public Optional<Departement> getDepartementById(UUID id) {
        return departementRepository.findById(id);
    }

    //Modifier les informations sur un departement (existingDepartement) deja existant en BD
    public Departement updateDepartement(UUID id, Departement updateDepartement) {
        return departementRepository.findById(id).map(
            existingDepartement -> {
                existingDepartement.setLibelleDepartement(updateDepartement.getLibelleDepartement());
                return departementRepository.save(existingDepartement);
            }
        ).orElseThrow(() -> new RuntimeException("Département non trouvé !"));
    }

    //Supprimer de la BD un poste par son ID
    public void deleteDepartement(UUID id) {
        if (!departementRepository.existsById(id)) {
            throw new RuntimeException("Le département avec id " + id + " n'existe pas.");
        }
        departementRepository.deleteById(id);
    }
}
