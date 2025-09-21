/*
 * Copyright (C) 2025 Daniel Masek
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.masci.drd.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "\"CHARACTER\"")
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
