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

package cz.masci.drd.ui.battle.service.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class BattleSlidePropertiesDTO {

  @Getter
  private final BooleanProperty prevDisableProperty = new SimpleBooleanProperty(false);
  @Getter
  private final BooleanProperty nextDisableProperty = new SimpleBooleanProperty(false);
  @Getter
  private final StringProperty prevTextProperty = new SimpleStringProperty("Zpět");
  @Getter
  private final StringProperty nextTextProperty = new SimpleStringProperty("Další");
  @Getter
  private final StringProperty titleProperty = new SimpleStringProperty("Skupiny");
}
