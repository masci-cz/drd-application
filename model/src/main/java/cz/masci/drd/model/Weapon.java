/*
 * Copyright (C) 2021 Daniel
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

/**
 *
 * @author Daniel
 */
@Entity
@Table(name = "WEAPON")
@Data
public class Weapon {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WEAPON_ID", nullable = false, updatable = false)
  private Long id;
  
  /**
   * Weapon name
   */
  private String name;
  /**
   * Weapon offense number
   */
  private Integer strength;
  /**
   * Weapon attack number
   */
  private Integer damage;
  
}
