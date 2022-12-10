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
package cz.masci.drd.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cz.masci.drd.model.Monster;
import java.util.Optional;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author Daniel
 */
@DataJpaTest
@FlywayTest(locationsForMigrate = {Migrations.TEST_MIGRATIONS, Migrations.TEST_DATA})
public class MonsterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MonsterRepository monsterRepository;

    @Test
    public void testSomeMethod() {
        Monster monster = entityManager.persist(createMonsterEntity());
        Optional<Monster> foundMonster = monsterRepository.findById(monster.getId());

        assertTrue(foundMonster.isPresent());
    }

    private Monster createMonsterEntity() {
        var monster = new Monster();
        monster.setName("Monster name");
        monster.setViability("Viability");
        monster.setAttack("Attack");
        monster.setDefence("Defence");
        monster.setEndurance(10);
        monster.setDimension("A");
        monster.setVulnerability("Vulnerability");
        monster.setMoveability("Moveability");
        monster.setIntelligence(10);
        monster.setTreasure("Treasure");
        monster.setExperience("Experience");

        return monster;
    }

}
