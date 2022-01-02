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
package cz.masci.drd.service;

import cz.masci.drd.dto.AdventureDTO;
import cz.masci.drd.dto.RoomDTO;
import cz.masci.drd.model.Adventure;
import cz.masci.drd.model.Room;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mapstruct.Mapper;

/**
 *
 * @author Daniel
 */
@Mapper(componentModel = "spring")
public interface AdventureMapper {

  AdventureDTO mapToDto(Adventure entity);

  Adventure mapToEntity(AdventureDTO adventure);

  RoomDTO roomToRoomDTO(Room room);

  default ObservableList<RoomDTO> roomListToRoomDTOObservableList(List<Room> list) {
    if (list == null) {
      return null;
    }

    ObservableList<RoomDTO> observableList = FXCollections.observableArrayList();
    observableList.addAll(list.stream().map(this::roomToRoomDTO).collect(Collectors.toList()));

    return observableList;
  }
}
