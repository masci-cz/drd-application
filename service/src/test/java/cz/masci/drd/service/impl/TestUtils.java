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

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.drd.dto.MonsterDTO;
import cz.masci.drd.dto.RoomDTO;
import cz.masci.drd.dto.WeaponDTO;
import cz.masci.drd.model.Adventure;
import cz.masci.drd.model.Monster;
import cz.masci.drd.model.Room;
import cz.masci.drd.model.Weapon;
import static cz.masci.drd.service.impl.TestConstants.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class TestUtils {
    
    public static Monster createMonsterEntity() {
        var monster = new Monster();
        monster.setId(LONG_ID);
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

    public static Adventure createAdventureEntity() {
      var adventure = new Adventure();
      adventure.setId(LONG_ID);
      adventure.setName(ADVENTURE_NAME);
      
      return adventure;
    }
    
    public static Adventure createAdventureEntity(Room room) {
      var adventure = createAdventureEntity();
      adventure.setRooms(new ArrayList(List.of(room)));
      
      return adventure;
    }
    
    public static AdventureDTO createAdventure() {
      var adventure = new AdventureDTO();
      adventure.setName(ADVENTURE_NAME);
      
      return adventure;
    }
    
    public static AdventureDTO createAdventure(RoomDTO room) {
      var adventure = createAdventure();
      adventure.setRooms(List.of(room));
      
      return adventure;
    }
    
    public static Weapon createWeaponEntity() {
      var weapon = new Weapon();
      weapon.setId(LONG_ID);
      weapon.setName(WEAPON_NAME);
      weapon.setStrength(WEAPON_STRENGTH);
      weapon.setDamage(WEAPON_DAMAGE);
      
      return weapon;
    }
    
    public static WeaponDTO createWeapon() {
      var weapon = new WeaponDTO();
      weapon.setId(LONG_ID);
      weapon.setName(WEAPON_NAME);
      weapon.setStrength(WEAPON_STRENGTH);
      weapon.setDamage(WEAPON_DAMAGE);
      
      return weapon;
    }
    
    public static Room createRoomEntity() {
      var room = new Room();
      room.setId(LONG_ID);
      room.setName(ROOM_NAME);
      room.setAdventure(createAdventureEntity(room));

      return room;
    }
    
    public static RoomDTO createRoom() {
      var room = new RoomDTO();
      room.setId(LONG_ID);
      room.setName(ROOM_NAME);
      room.setAdventure(createAdventure(room));
      
      return room;
    }
}
