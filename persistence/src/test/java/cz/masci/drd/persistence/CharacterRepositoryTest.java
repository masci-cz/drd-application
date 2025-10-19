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

package cz.masci.drd.persistence;

import cz.masci.drd.model.Ability;
import cz.masci.drd.model.GameCharacter;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@FlywayTest(locationsForMigrate = {Migrations.TEST_MIGRATIONS, Migrations.TEST_DATA})
class CharacterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void testSomeMethod() {
        GameCharacter character = entityManager.persist(createCharacterEntity());
        Optional<GameCharacter> foundCharacter = characterRepository.findById(character.getId());

        assertTrue(foundCharacter.isPresent());
    }

    private GameCharacter createCharacterEntity() {
        var character = new GameCharacter();
        character.setName("Hero Name");
        character.setCharacterClass("ALCHEMIST");
        character.setLevel(1);
        character.setExperience(100);
        var ability = new Ability();
        ability.setStrength(10);
        ability.setDexterity(12);
        ability.setIntelligence(15);
        ability.setConstitution(10);
        ability.setCharisma(12);
        character.setAbility(ability);
        return character;
    }
}