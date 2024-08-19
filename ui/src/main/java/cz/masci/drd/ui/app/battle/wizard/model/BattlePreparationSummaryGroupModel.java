/*
 * Copyright (c) 2024
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

package cz.masci.drd.ui.app.battle.wizard.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BattlePreparationSummaryGroupModel {
  private final StringProperty name = new SimpleStringProperty();
  private final ListProperty<BattlePreparationSummaryDuellistModel> duellists = new SimpleListProperty<>(FXCollections.observableArrayList());

  public String getName() {
    return name.get();
  }

  public StringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public ObservableList<BattlePreparationSummaryDuellistModel> getDuellists() {
    return duellists.get();
  }

  public ListProperty<BattlePreparationSummaryDuellistModel> duellistsProperty() {
    return duellists;
  }

  public void setDuellists(ObservableList<BattlePreparationSummaryDuellistModel> duellists) {
    this.duellists.set(duellists);
  }
}
