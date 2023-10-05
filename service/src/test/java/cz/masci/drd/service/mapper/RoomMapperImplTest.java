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

import static cz.masci.drd.service.impl.TestConstants.ROOM_NAME;
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
public class RoomMapperImplTest {

    private RoomMapperImpl roomMapper;

    @BeforeEach
    void init() {
        roomMapper = new RoomMapperImpl();
    }

    @Test
    void mapToDto_null() {
        var result = roomMapper.mapToDto(null);
        assertNull(result);
    }

    @Test
    void mapToDto() {
        var result = roomMapper.mapToDto(TestUtils.createRoomEntity());

        assertAll("Adventure entity",
                () -> assertEquals(ROOM_NAME, result.getName())
        );
    }

    @Test
    void mapToEntity_null() {
      var result = roomMapper.mapToEntity(null);
      assertNull(result);
    }
    
    @Test
    void mapToEntity() {
        var result = roomMapper.mapToEntity(TestUtils.createRoom());

        assertAll("Adventure dto",
                () -> assertEquals(ROOM_NAME, result.getName())
        );
    }
}
