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
package cz.masci.drd.service.mapper;

import cz.masci.drd.service.impl.TestUtils;
import org.junit.jupiter.api.Test;

import static cz.masci.drd.service.impl.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Daniel
 */
public class MonsterMapperImplTest {

    private final MonsterMapper monsterMapper = new MonsterMapperImpl();

    @Test
    void mapToDto_null() {
        var result = monsterMapper.mapToDto(null);
        assertNull(result);
    }

    @Test
    void mapToDto() {
        var result = monsterMapper.mapToDto(TestUtils.createMonsterEntity());

        assertAll("Monster entity",
                () -> assertEquals(MONSTER_NAME, result.getName()),
                () -> assertEquals(VIABILITY, result.getViability()),
                () -> assertEquals(ATTACK, result.getAttack()),
                () -> assertEquals(DEFENCE, result.getDefence()),
                () -> assertEquals(ENDURANCE, result.getEndurance()),
                () -> assertEquals(DIMENSION, result.getDimension()),
                () -> assertEquals(VULNERABILITY, result.getVulnerability()),
                () -> assertEquals(MOVEABILITY, result.getMoveability()),
                () -> assertEquals(INTELLIGENCE, result.getIntelligence()),
                () -> assertEquals(TREASURE, result.getTreasure()),
                () -> assertEquals(EXPERIENCE, result.getExperience())
        );
    }

    @Test
    void mapToEntity_null() {
      var result = monsterMapper.mapToEntity(null);
      assertNull(result);
    }
    
    @Test
    void mapToEntity() {
        var result = monsterMapper.mapToEntity(TestUtils.createMonster());

        assertAll("Monster dto",
                () -> assertEquals(MONSTER_NAME, result.getName()),
                () -> assertEquals(VIABILITY, result.getViability()),
                () -> assertEquals(ATTACK, result.getAttack()),
                () -> assertEquals(DEFENCE, result.getDefence()),
                () -> assertEquals(ENDURANCE, result.getEndurance()),
                () -> assertEquals(DIMENSION, result.getDimension()),
                () -> assertEquals(VULNERABILITY, result.getVulnerability()),
                () -> assertEquals(MOVEABILITY, result.getMoveability()),
                () -> assertEquals(INTELLIGENCE, result.getIntelligence()),
                () -> assertEquals(TREASURE, result.getTreasure()),
                () -> assertEquals(EXPERIENCE, result.getExperience())
        );
    }
}
