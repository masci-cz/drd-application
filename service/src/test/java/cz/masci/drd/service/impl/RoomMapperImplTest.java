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

import cz.masci.drd.service.RoomMapperImpl;
import static cz.masci.drd.service.impl.TestConstants.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

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
