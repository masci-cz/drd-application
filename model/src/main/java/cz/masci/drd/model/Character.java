package cz.masci.drd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CHARACTER")
@Data
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;
    private Integer level;
    private Integer experience;
}
