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
package cz.masci.drd.service.impl;

import cz.masci.drd.dto.*;
import cz.masci.drd.model.*;

import static cz.masci.drd.service.impl.TestConstants.*;

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
    
    public static AdventureDTO createAdventure() {
      var adventure = new AdventureDTO();
      adventure.setId(LONG_ID);
      adventure.setName(ADVENTURE_NAME);
      
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
      var room = createRoomEntityWithoutAdventure();
      room.setAdventure(createAdventureEntity());

      return room;
    }
    
    public static Room createRoomEntityWithoutAdventure() {
      var room = new Room();
      room.setId(LONG_ID);
      room.setName(ROOM_NAME);

      return room;
    }
    
    public static RoomDTO createRoom() {
      var room = createRoomWithoutAdventure();
      room.setAdventure(createAdventure());
      
      return room;
    }
    
    public static RoomDTO createRoomWithoutAdventure() {
      var room = new RoomDTO();
      room.setId(LONG_ID);
      room.setName(ROOM_NAME);
      
      return room;
    }

    public static GameCharacter createGameCharacterEntity() {
        var ability = new Ability();
        ability.setStrength(STRENGTH_VALUE);
        ability.setDexterity(DEXTERITY_VALUE);
        ability.setConstitution(CONSTITUTION_VALUE);
        ability.setIntelligence(INTELLIGENCE_VALUE);
        ability.setCharisma(CHARISMA_VALUE);

        var character = new GameCharacter();
        character.setName(CHARACTER_NAME);
        character.setCharacterClass(CHARACTER_CLASS.name());
        character.setLevel(LEVEL_VALUE);
        character.setExperience(EXPERIENCE_VALUE);
        character.setAbility(ability);
        return character;
    }

    public static CharacterDto createCharacterDto() {
        var abilityDto = new AbilityDto();
        abilityDto.setStrength(STRENGTH_VALUE);
        abilityDto.setDexterity(DEXTERITY_VALUE);
        abilityDto.setConstitution(CONSTITUTION_VALUE);
        abilityDto.setIntelligence(INTELLIGENCE_VALUE);
        abilityDto.setCharisma(CHARISMA_VALUE);

        var characterDto = new CharacterDto();
        characterDto.setName(CHARACTER_NAME);
        characterDto.setCharacterClass(CHARACTER_CLASS);
        characterDto.setLevel(LEVEL_VALUE);
        characterDto.setExperience(EXPERIENCE_VALUE);
        characterDto.setAbility(abilityDto);
        return characterDto;
    }

}
