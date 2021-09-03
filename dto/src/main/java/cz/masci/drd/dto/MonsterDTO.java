/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.masci.drd.dto;

import lombok.Data;

/**
 *
 * @author Daniel
 */
@Data
public class MonsterDTO {
    
    private Long id;
    private String name;
    private String viability;
    private String attack;
    private String defence;
    private Integer endurance;
    private String dimension;
    private Integer combativeness;
    private String vulnerability;
    private String moveability;
    private String stamina;
    private Integer intelligence;
    private Integer conviction;
    private String treasure;
    private String experience;
    private String description;
}
