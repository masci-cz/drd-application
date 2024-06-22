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
import io.github.palexdev.materialfx.validation.MFXValidator;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class SelectedActionModel implements ValidModel {
  @Getter
  private final MFXValidator validator = new MFXValidator();
  private final ListProperty<DuellistDTO> duellists = new SimpleListProperty<>(FXCollections.observableArrayList());
  private final ObjectProperty<DuellistDTO> actor = new SimpleObjectProperty<>();
  private final ObjectProperty<DuellistDTO> consumer = new SimpleObjectProperty<>();
  private final StringProperty spell = new SimpleStringProperty();
  private final StringProperty comment = new SimpleStringProperty();

  public SelectedActionModel(DuellistDTO actor, List<DuellistDTO> duellists) {
    setActor(actor);
    this.duellists.addAll(duellists);
//    validator.constraint(Constraint.of("Vyberte bojovníka", selectedDefender.isNotNull()));
  }

  public DuellistDTO getActor() {
    return actor.get();
  }

  public void setActor(DuellistDTO actor) {
    this.actor.set(actor);
  }

  public ObservableList<DuellistDTO> duellistsProperty() {
    return duellists;
  }

  public DuellistDTO getConsumer() {
    return consumer.get();
  }

  public ObjectProperty<DuellistDTO> consumerProperty() {
    return consumer;
  }

  public StringProperty spellProperty() {
    return spell;
  }

  public String getSpell() {
    return spell.get();
  }

  public String getComment() {
    return comment.get();
  }

  public StringProperty commentProperty() {
    return comment;
  }

  @Override
  public String toString() {
    return "SelectedActionModel{" +
        "actor=" + actor +
        ", consumer=" + consumer +
        ", spell=" + spell +
        '}';
  }
}
