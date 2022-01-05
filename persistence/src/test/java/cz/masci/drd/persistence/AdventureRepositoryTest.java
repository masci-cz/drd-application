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

import cz.masci.drd.model.Adventure;
import java.util.Optional;
import javax.transaction.Transactional;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author Daniel
 */
@DataJpaTest
@Transactional
@FlywayTest(locationsForMigrate = {Migrations.TEST_MIGRATIONS, Migrations.TEST_DATA})
public class AdventureRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private AdventureRepository adventureRepository;

  @Test
  public void findById() {
    Adventure adventure = entityManager.persist(createAdventureEntity());

    Optional<Adventure> foundAdventure = adventureRepository.findById(adventure.getId());

    assertTrue(foundAdventure.isPresent());
  }

  @Test
  void findById_rooms() {
    var adventure = createAdventureEntity();
    entityManager.persist(adventure);

    Optional<Adventure> foundAdventure = adventureRepository.findById(adventure.getId());

    assertTrue(foundAdventure.isPresent());
  }

  private Adventure createAdventureEntity() {
    var adventure = new Adventure();
    adventure.setName("Adventure name");

    return adventure;
  }

}
