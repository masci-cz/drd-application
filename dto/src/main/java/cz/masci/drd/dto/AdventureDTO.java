/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.masci.drd.dto;

import cz.masci.springfx.data.Modifiable;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

/**
 *
 * @author Daniel
 */
public class AdventureDTO implements Modifiable {

  /**
   * Adventure id
   */
  private LongProperty idProperty;
  /**
   * Adventure name
   */
  private StringProperty nameProperty;  
  /**
   * Rooms in adventure
   */
  private ReadOnlyListProperty<RoomDTO> roomsProperty;
  
  public final LongProperty idProperty() {
    if (idProperty == null) {
      idProperty = new SimpleLongProperty();
    }
    return idProperty;
  }
  
  public Long getId() {
    return idProperty().get();
  }
  
  public void setId(Long id) {
    idProperty().set(id);
  }
  
  public final StringProperty nameProperty() {
    if (nameProperty == null) {
      nameProperty = new SimpleStringProperty();
    }
    return nameProperty;
  }
  
  public String getName() {
    return nameProperty().get();
  }
  
  public void setName(String name) {
    nameProperty().set(name);
  }
  
  public final ReadOnlyListProperty<RoomDTO> roomsProperty() {
    if (roomsProperty == null) {
      roomsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    return roomsProperty;
  }
  
  public ObservableList<RoomDTO> getRooms() {
    return roomsProperty().get();
  }
  
}
