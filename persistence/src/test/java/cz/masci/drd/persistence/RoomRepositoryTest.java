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
import cz.masci.drd.model.Room;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
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
public class RoomRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private RoomRepository roomRepository;

  @Test
  void findById() {
    Adventure adventure = entityManager.persist(createAdventureEntity());
    Room room = entityManager.persist(createRoomEntity(adventure));
    
    Optional<Room> foundRoom = roomRepository.findById(room.getId());
    
    assertTrue(foundRoom.isPresent());
  }
  
  @Test
  void findByAdventure() {
    Adventure adventure = entityManager.persist(createAdventureEntity());
    Room room1 = entityManager.persist(createRoomEntity(adventure));
    Room room2 = entityManager.persist(createRoomEntity(adventure));
    
    List<Room> rooms = roomRepository.findAllByAdventureId(adventure.getId());
    
    assertThat(rooms)
        .hasSize(2)
        .containsExactly(room1, room2);
  }
  
  
  private Adventure createAdventureEntity() {
    var adventure = new Adventure();
    adventure.setName("Adventure name");
    
    return adventure;
  }
  
  private Room createRoomEntity(Adventure adventure) {
    var room = new Room();
    room.setName("Room name");
    room.setAdventure(adventure);
    
    return room;
  }
  
}
