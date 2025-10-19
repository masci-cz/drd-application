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

import org.junit.jupiter.api.Test;

import static cz.masci.drd.service.impl.TestConstants.*;
import static cz.masci.drd.service.impl.TestUtils.createCharacterDto;
import static cz.masci.drd.service.impl.TestUtils.createGameCharacterEntity;
import static org.junit.jupiter.api.Assertions.*;

class CharacterMapperImplTest {

    private final CharacterMapper characterMapper = new CharacterMapperImpl();

    @Test
    void mapToDto_null() {
        var result = characterMapper.mapToDto(null);
        assertNull(result);
    }

    @Test
    void mapToDto() {
        var result = characterMapper.mapToDto(createGameCharacterEntity());

        assertNotNull(result);
        assertAll("GameCharacter to DTO mapping",
                () -> assertEquals(CHARACTER_NAME, result.getName()),
                () -> assertEquals(CHARACTER_CLASS, result.getCharacterClass()),
                () -> assertEquals(LEVEL_VALUE, result.getLevel()),
                () -> assertEquals(EXPERIENCE_VALUE, result.getExperience())
        );

        assertNotNull(result.getAbility());
        assertAll("Ability to AbilityDto mapping",
                () -> assertEquals(STRENGTH_VALUE, result.getAbility().getStrength()),
                () -> assertEquals(DEXTERITY_VALUE, result.getAbility().getDexterity()),
                () -> assertEquals(CONSTITUTION_VALUE, result.getAbility().getConstitution()),
                () -> assertEquals(INTELLIGENCE_VALUE, result.getAbility().getIntelligence()),
                () -> assertEquals(CHARISMA_VALUE, result.getAbility().getCharisma())
        );
    }

    @Test
    void mapToEntity_null() {
        var result = characterMapper.mapToEntity(null);
        assertNull(result);
    }

    @Test
    void mapToEntity() {
        var result = characterMapper.mapToEntity(createCharacterDto());

        assertNotNull(result);
        assertAll("DTO to GameCharacter mapping",
                () -> assertEquals(CHARACTER_NAME, result.getName()),
                () -> assertEquals(CHARACTER_CLASS.name(), result.getCharacterClass()),
                () -> assertEquals(LEVEL_VALUE, result.getLevel()),
                () -> assertEquals(EXPERIENCE_VALUE, result.getExperience())
        );

        assertNotNull(result.getAbility());
        assertAll("AbilityDto to Ability mapping",
                () -> assertEquals(STRENGTH_VALUE, result.getAbility().getStrength()),
                () -> assertEquals(DEXTERITY_VALUE, result.getAbility().getDexterity()),
                () -> assertEquals(CONSTITUTION_VALUE, result.getAbility().getConstitution()),
                () -> assertEquals(INTELLIGENCE_VALUE, result.getAbility().getIntelligence()),
                () -> assertEquals(CHARISMA_VALUE, result.getAbility().getCharisma())
        );
    }

}