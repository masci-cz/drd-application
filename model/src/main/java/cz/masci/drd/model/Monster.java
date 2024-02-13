package cz.masci.drd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "MONSTER")
@Data
public class Monster {

    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
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
