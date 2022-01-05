/*
 * Copyright (C) 2022 Daniel
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
import cz.masci.drd.service.RoomMapper;
import cz.masci.drd.service.RoomService;
import cz.masci.springfx.exception.CrudException;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl extends AbstractService<Room, RoomDTO> implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper mapper;

  private ObjectProperty<AdventureDTO> adventureProperty;
  
  @Override
  public final ObjectProperty<AdventureDTO> adventureProperty() {
    if (adventureProperty == null) {
      adventureProperty = new SimpleObjectProperty();
    }
    return adventureProperty;
  }
  
  @Override
  public AdventureDTO getAdventure() {
    return adventureProperty().get();
  }
  
  @Override
  public void setAdventure(AdventureDTO adventure) {
    adventureProperty().set(adventure);
  }
  
  @Override
  public List<RoomDTO> list() throws CrudException {
    return getAdventure() == null ? List.of() : list(() -> roomRepository.findAllByAdventureId(getAdventure().getId()));
  }

  @Override
  public RoomDTO save(RoomDTO item) throws CrudException {
    if (getAdventure() == null) {
      throw new CrudException("No adventure selected");
    }
    item.setAdventure(getAdventure());
    
    return apply(item, roomRepository::save);
  }

  @Override
  public void delete(RoomDTO item) throws CrudException {
    accept(item, roomRepository::delete);
  }

  @Override
  protected Room mapToEntity(RoomDTO item) {
    return mapper.mapToEntity(item);
  }

  @Override
  protected RoomDTO mapToDto(Room item) {
    return mapper.mapToDto(item);
  }
  
}
