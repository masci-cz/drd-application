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

import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.model.Monster;
import static cz.masci.drd.service.impl.TestConstants.*;

/**
 *
 * @author Daniel
 */
public class TestUtils {
    
    public static Monster createMonsterEntity() {
        var monster = new Monster();
        monster.setId(ID);
        monster.setName(MONSTER_NAME);
        monster.setViability(VIABILITY);
        monster.setAttack(ATTACK);
        monster.setDefence(DEFENCE);
        monster.setEndurance(ENDURANCE);
        monster.setDimension(DIMENSION);
        monster.setVulnerability(VULNERABILITY);
        monster.setMoveability(MOVEABILITY);
        monster.setIntelligence(INTELLIGENCE);
        monster.setTreasure(TREASURE);
        monster.setExperience(EXPERIENCE);

        return monster;
    }

    public static MonsterDTO createMonster() {
        var monster = new MonsterDTO();
        monster.setName(MONSTER_NAME);
        monster.setViability(VIABILITY);
        monster.setAttack(ATTACK);
        monster.setDefence(DEFENCE);
        monster.setEndurance(ENDURANCE);
        monster.setDimension(DIMENSION);
        monster.setVulnerability(VULNERABILITY);
        monster.setMoveability(MOVEABILITY);
        monster.setIntelligence(INTELLIGENCE);
        monster.setTreasure(TREASURE);
        monster.setExperience(EXPERIENCE);

        return monster;
    }

}
