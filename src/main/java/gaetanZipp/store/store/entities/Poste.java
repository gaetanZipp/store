package gaetanZipp.store.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="postes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Poste {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotBlank(message = "Le libelle du poste est obligatoire")
    private nomPoste libellePoste;

    @Column(nullable = false)
    @Min(value = 42000, message = "Le salaire minimal doit etre au dessus du SMIG")
    private Integer salaireMin;

    @Column(nullable = false)
    @Max(value = 1000000, message = "Le salaire maxiaml est de 1 million")
    private Integer salaireMax;

    @OneToMany(mappedBy = "poste", cascade = CascadeType.ALL)
    private List<Employe> employes;

    @AssertTrue(message="Le salaire maximum doit être supérieur au salaire minimum")
    public boolean isSalaireValide() {
        //pouréviterNullPointerException
        if (salaireMin == null|| salaireMax == null) return true;
        return salaireMax > salaireMin;
    }
}
