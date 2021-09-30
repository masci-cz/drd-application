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
    /** Monster name - Jméno nestvůry */
    private String name;
    /** Monster viability - Životaschopnost */
    private String viability;
    /** Monster attack - Útočné číslo */
    private String attack;
    /** Monster defence - Obranné číslo */
    private String defence;
    /** Monster endurance - Odolnost */
    private Integer endurance;
    /** Monster dimension - Velikost */
    private String dimension;
    /** Monster combativeness - Bojovnost */
    private Integer combativeness;
    /** Monster vulnerability - Zranitelnost */
    private String vulnerability;
    /** Monster moveability - Pohyblivost */
    private String moveability;
    /** Monster stamina - Vytrvalost */
    private String stamina;
    /** Monster intelligence - Inteligence */
    private Integer intelligence;
    /** Monster conviction - Přesvědčení */
    private Integer conviction;
    /** Monster treasure - Poklady */
    private String treasure;
    /** Monster experience - Zkušenost */
    private String experience;
    /** Monster description - Popis */
    private String description;
}
