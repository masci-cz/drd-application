package cz.masci.drd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CHARACTER")
@Data
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARACTER_ID", nullable = false, updatable = false)
    private Long id;
    private String name;
    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;
    private Integer level;
    private Integer experience;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ABILITY_ID")
    private Ability ability;
}
