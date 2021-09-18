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
package cz.masci.drd.service.impl;

import cz.masci.drd.persistence.MonsterRepository;
import cz.masci.drd.service.MonsterMapper;
import cz.masci.drd.service.MonsterMapperImpl;
import static cz.masci.drd.service.impl.TestConstants.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author Daniel
 */
public class MonsterServiceImplTest {

    private MonsterMapper monsterMapper;

    private MonsterRepository monsterRepository;

    private MonsterServiceImpl monsterService;

    @BeforeEach
    void init() {
        monsterMapper = new MonsterMapperImpl();
        monsterRepository = mock(MonsterRepository.class);
        monsterService = new MonsterServiceImpl(monsterRepository, monsterMapper);
    }

    @Test
    void testSomeMethod() {
        when(monsterRepository.findById(any())).thenReturn(Optional.of(TestUtils.createMonsterEntity()));

        var result = monsterService.getMonster(1l);

        assertTrue(result.isPresent());
        
        var monster = result.get();
        
        assertAll("Monster dto",
                () -> assertEquals(MONSTER_NAME, monster.getName()),
                () -> assertEquals(VIABILITY, monster.getViability()),
                () -> assertEquals(ATTACK, monster.getAttack()),
                () -> assertEquals(DEFENCE, monster.getDefence()),
                () -> assertEquals(ENDURANCE, monster.getEndurance()),
                () -> assertEquals(DIMENSION, monster.getDimension()),
                () -> assertEquals(VULNERABILITY, monster.getVulnerability()),
                () -> assertEquals(MOVEABILITY, monster.getMoveability()),
                () -> assertEquals(INTELLIGENCE, monster.getIntelligence()),
                () -> assertEquals(TREASURE, monster.getTreasure()),
                () -> assertEquals(EXPERIENCE, monster.getExperience())
        );

    }

}
