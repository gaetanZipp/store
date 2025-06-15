package gaetanZipp.store.store.dto;

import gaetanZipp.store.store.entities.nomPoste;

import java.util.UUID;

public record EmployeDTO(
        UUID id,
        String nom,
        String email,
        Integer aciennete,
        Integer salaire,
        nomPoste poste,
        String departement
) {
}
