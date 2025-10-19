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

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.drd.dto.RoomDTO;
import cz.masci.drd.model.Room;
import cz.masci.drd.persistence.RoomRepository;
import cz.masci.drd.service.mapper.RoomMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 *
 * @author Daniel
 */
@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

  @Mock
  private RoomRepository roomRepository;

  @Mock
  private RoomMapper roomMapper;

  @InjectMocks
  private RoomServiceImpl roomService;

  @Test
  @SneakyThrows
  void list_noAdventure() {
    var result = roomService.list();

    assertThat(result)
        .isEmpty();
  }

  @Test
  @SneakyThrows
  void list() {
    var roomEntity = mock(Room.class);
    var room = mock(RoomDTO.class);
    
    when(roomRepository.findAllByAdventureId(any())).thenReturn(List.of(roomEntity));
    when(roomMapper.mapToDto(any())).thenReturn(room);
    
    var adventure = TestUtils.createAdventure();
    roomService.setAdventure(adventure);
    
    var result = roomService.list();
    
    assertThat(result)
        .hasSize(1)
        .containsExactly(room);
  }

  @Test
  @SneakyThrows
  void save() {
    var roomToSave = TestUtils.createRoomWithoutAdventure();
    var roomEntity = TestUtils.createRoomEntityWithoutAdventure();
    var savedRoom = mock(RoomDTO.class);
    ArgumentCaptor<RoomDTO> roomCaptor = ArgumentCaptor.forClass(RoomDTO.class);
    
    when(roomRepository.save(any())).thenReturn(roomEntity);
    when(roomMapper.mapToEntity(roomCaptor.capture())).thenReturn(mock(Room.class));
    when(roomMapper.mapToDto(eq(roomEntity))).thenReturn(savedRoom);
    
    var adventure = TestUtils.createAdventure();
    roomService.setAdventure(adventure);
    
    var result = roomService.save(roomToSave);
    
    assertEquals(savedRoom, result);
    
    var capturedRoom = roomCaptor.getValue();
    assertNotNull(capturedRoom);
    assertEquals(adventure.getId(), capturedRoom.getId());
  }

  @Test
  @SneakyThrows
  void delete() {
    var roomToDelete = mock(RoomDTO.class);
    var roomEntity = mock(Room.class);
    ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
    
    when(roomMapper.mapToEntity(eq(roomToDelete))).thenReturn(roomEntity);
    
    roomService.delete(roomToDelete);
    
    verify(roomRepository).delete(roomCaptor.capture());
    
    var capturedRoom = roomCaptor.getValue();
    assertNotNull(capturedRoom);
    assertEquals(roomEntity, capturedRoom);
  }

  @Test
  void adventureProperty() {
    var property = roomService.adventureProperty();

    assertNotNull(property);
  }

  @Test
  void getAdventure() {
    var adventure = roomService.getAdventure();

    assertNull(adventure);
  }

  @Test
  void setAdventure() {
    var adventure = mock(AdventureDTO.class);
    roomService.setAdventure(adventure);

    var result = roomService.getAdventure();

    assertEquals(adventure, result);
  }

}
