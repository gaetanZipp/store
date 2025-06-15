package gaetanZipp.store.store.service;

import gaetanZipp.store.store.dto.EmployeDTO;
import gaetanZipp.store.store.entities.Employe;
import gaetanZipp.store.store.repository.EmployeRepository;
import gaetanZipp.store.store.repository.PosteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeService {
    private final EmployeRepository employeRepository;
    private final PosteRepository posteRepository;

    //Ajouter un nouvel employé dans la BD
    public Employe addEmploye(Employe employe){
        if(employeRepository.existsDistinctByNom(employe.getNom()) & employeRepository.existsDistinctByDateEmbauche(employe.getDateEmbauche())){
            throw new IllegalArgumentException("Un employé avec le même nom et la même date d'embauche existe déjà.");
        }
        return employeRepository.save(employe);
    }

    //RecupérerlalistedetouslesemployesquiexistentdanslaBD
    public List<EmployeDTO>getAllEmployes(){
        return employeRepository.findAll()
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList()
        );
    }

    //Recuperer un employe qui existe dans la BD par son ID
    public Employe getEmployeById(UUID id){
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employe non retrouve !"));
        return employe;
    }

    //Recuperer la liste des employes par Departement
    public List<EmployeDTO> getAllEmployeesByDepartement(UUID id){
        return employeRepository.findEmployeByDepartement_Id(id)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Modifier les informations sur un employe (existingEmploye) deja existamt en BD
    public Employe updateEmploye(UUID id, Employe employe){
        return employeRepository.findById(id).map(
                existingEmploye -> {
                    existingEmploye.setNom(employe.getNom());
                    existingEmploye.setPoste(employe.getPoste());
                    existingEmploye.setDateEmbauche(employe.getDateEmbauche());
                    existingEmploye.setDepartement(employe.getDepartement());
                    existingEmploye.setSalaire(employe.getSalaire());
                    existingEmploye.setEmail(employe.getEmail());

                    return employeRepository.save(existingEmploye);
                }
        ).orElseThrow(() -> new RuntimeException("Employe non retrouve !"));
    }

    //Supprimer un employe de la BD par son ID
    public void deleteEmploye(UUID id) {
        if (!employeRepository.existsById(id)) {
            throw new RuntimeException("Employe non retrouve !");
        }
        employeRepository.deleteById(id);
    }

    public EmployeDTO mapToDTO(Employe employe){
        return new EmployeDTO(
            employe.getId(),
                employe.getNom(),
                employe.getEmail(),
                Period.between(employe.getDateEmbauche(), LocalDate.now()).getYears(),
                employe.getSalaire(),
                employe.getPoste().getLibellePoste(),
                employe.getDepartement().getLibelleDepartement()
        );
    }
}
