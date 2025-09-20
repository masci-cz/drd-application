package cz.masci.drd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ABILITY")
@Data
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ABILITY_ID", nullable = false, updatable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CHARACTER_ID")
    private Character character;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer intelligence;
    private Integer charisma;
}
