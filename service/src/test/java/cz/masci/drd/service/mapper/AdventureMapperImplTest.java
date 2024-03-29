/*
 * Copyright (c) 2023
 *
 * This file is part of DrD.
 *
 * DrD is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option)
 *   any later version.
 *
 * DrD is distributed in the hope that it will be useful, but WITHOUT ANY
 *  WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 *    License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *  along with Foobar. If not, see <https://www.gnu.org/licenses/>.
 */
package cz.masci.drd.service.mapper;

import static cz.masci.drd.service.impl.TestConstants.ADVENTURE_NAME;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cz.masci.drd.service.impl.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Daniel
 */
public class AdventureMapperImplTest {

    private AdventureMapperImpl adventureMapper;

    @BeforeEach
    void init() {
        adventureMapper = new AdventureMapperImpl();
    }

    @Test
    void mapToDto_null() {
        var result = adventureMapper.mapToDto(null);
        assertNull(result);
    }

    @Test
    void mapToDto() {
        var result = adventureMapper.mapToDto(TestUtils.createAdventureEntity());

        assertAll("Adventure entity",
                () -> assertEquals(ADVENTURE_NAME, result.getName())
        );
    }

    @Test
    void mapToEntity_null() {
      var result = adventureMapper.mapToEntity(null);
      assertNull(result);
    }
    
    @Test
    void mapToEntity() {
        var result = adventureMapper.mapToEntity(TestUtils.createAdventure());

        assertAll("Adventure dto",
                () -> assertEquals(ADVENTURE_NAME, result.getName())
        );
    }
}
