package cz.masci.drd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @NotNull
    private String name;
    @NotNull
    private String viability;
    @NotNull
    private String attack;
    @NotNull
    private String defence;
    @NotNull
    private Integer endurance;
    @NotNull
    private String dimension;
    private Integer combativeness;
    @NotNull
    private String vulnerability;
    @NotNull
    private String moveability;
    private String stamina;
    @NotNull
    private Integer intelligence;
    private Integer conviction;
    @NotNull
    private String treasure;
    @NotNull
    private String experience;
    private String description;
}
