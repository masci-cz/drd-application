package cz.masci.drd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "MONSTER")
@Data
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MONSTER_ID", nullable = false, updatable = false)
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String viability;
    @NotEmpty
    private String attack;
    @NotEmpty
    private String defence;
    @NotNull
    private Integer endurance;
    @NotEmpty
    private String dimension;
    private Integer combativeness;
    @NotEmpty
    private String vulnerability;
    @NotEmpty
    private String moveability;
    private String stamina;
    @NotNull
    private Integer intelligence;
    private Integer conviction;
    @NotEmpty
    private String treasure;
    @NotEmpty
    private String experience;
    private String description;
}
