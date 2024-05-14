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

import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectActionCloseCombatModel {
  private final ListProperty<String> duellists = new SimpleListProperty<>(FXCollections.observableArrayList());
  private final StringProperty attacker = new SimpleStringProperty();
  private final StringProperty selectedDefender = new SimpleStringProperty();

  public SelectActionCloseCombatModel(String attacker, List<String> duellists) {
    setAttacker(attacker);
    this.duellists.addAll(duellists);
  }

  public String getAttacker() {
    return attacker.get();
  }

  public StringProperty attackerProperty() {
    return attacker;
  }

  public void setAttacker(String attacker) {
    this.attacker.set(attacker);
  }

  public ObservableList<String> duellistsProperty() {
    return duellists;
  }

  public String getSelectedDefender() {
    return selectedDefender.get();
  }

  public StringProperty selectedDefenderProperty() {
    return selectedDefender;
  }

  public void setSelectedDefender(String selectedDefender) {
    this.selectedDefender.set(selectedDefender);
  }
}
