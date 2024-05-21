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

import cz.masci.drd.dto.DuellistDTO;
import cz.masci.springfx.mvci.model.detail.ValidModel;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class SelectActionCloseCombatModel implements ValidModel {
  @Getter
  private final MFXValidator validator = new MFXValidator();
  private final ListProperty<DuellistDTO> duellists = new SimpleListProperty<>(FXCollections.observableArrayList());
  private final ObjectProperty<DuellistDTO> attacker = new SimpleObjectProperty<>();
  private final ObjectProperty<DuellistDTO> selectedDefender = new SimpleObjectProperty<>();

  public SelectActionCloseCombatModel(DuellistDTO attacker, List<DuellistDTO> duellists) {
    setAttacker(attacker);
    this.duellists.addAll(duellists);
    validator.constraint(Constraint.of("Vyberte bojovnika", selectedDefender.isNotNull()));
  }

  public DuellistDTO getAttacker() {
    return attacker.get();
  }

  public ObjectProperty<DuellistDTO> attackerProperty() {
    return attacker;
  }

  public void setAttacker(DuellistDTO attacker) {
    this.attacker.set(attacker);
  }

  public ObservableList<DuellistDTO> duellistsProperty() {
    return duellists;
  }

  public DuellistDTO getSelectedDefender() {
    return selectedDefender.get();
  }

  public ObjectProperty<DuellistDTO> selectedDefenderProperty() {
    return selectedDefender;
  }

  public void setSelectedDefender(DuellistDTO selectedDefender) {
    this.selectedDefender.set(selectedDefender);
  }

  @Override
  public String toString() {
    return "SelectActionCloseCombatModel{" + "selectedDefender=" + selectedDefender.get() + '}';
  }
}
