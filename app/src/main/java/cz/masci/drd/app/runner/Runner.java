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
package cz.masci.drd.app.runner;

import cz.masci.drd.dto.MonsterDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import cz.masci.drd.service.MonsterService;
import java.util.Random;

/**
 *
 * @author Daniel
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

    public static final String EXPERIENCE = "Experience";
    public static final String TREASURE = "Treasure";
    public static final String MOVEABILITY = "Moveability";
    public static final String VULNERABILITY = "Vulnerability";
    public static final String DIMENSION = "A";
    public static final String DEFENCE = "Defence";
    public static final String ATTACK = "Attack";
    public static final String VIABILITY = "Viability";
    public static final String MONSTER_NAME = "Monster name";
    public static final String STAMINA = "Stamina";
    public static final String DESCRIPTION = "Description";

    private final MonsterService monsterService;
    
    private Random random = new Random();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Running DrD application");
        for (int i = 0; i < 10; i++) {
            var monster = saveMonster();
            printTest(monster.getId());
        }
    }

    private void printTest(long id) {
        var test = monsterService.getById(id);
        
        if (test.isPresent()) {
            log.info("Found record: {}", test.get());
        } else {
            log.info("Record id = {} not found", id);
        }
    }

    private MonsterDTO saveMonster() {
        var index = random.nextInt();
        
        var monster = new MonsterDTO();
        monster.setName(MONSTER_NAME + "_" + index);
        monster.setViability(VIABILITY + "_" + index);
        monster.setAttack(ATTACK + "_" + index);
        monster.setDefence(DEFENCE + "_" + index);
        monster.setDimension(DIMENSION + "_" + index);
        monster.setVulnerability(VULNERABILITY + "_" + index);
        monster.setMoveability(MOVEABILITY + "_" + index);
        monster.setStamina(STAMINA + "_" + index);
        monster.setTreasure(TREASURE + "_" + index);
        monster.setExperience(EXPERIENCE + "_" + index);
        monster.setDescription(DESCRIPTION + "_" + index);
        monster.setEndurance(random.nextInt(21));
        monster.setCombativeness(random.nextInt(21));
        monster.setIntelligence(random.nextInt(21));
        monster.setConviction(random.nextInt(21));

        return monsterService.save(monster);
    }
}
