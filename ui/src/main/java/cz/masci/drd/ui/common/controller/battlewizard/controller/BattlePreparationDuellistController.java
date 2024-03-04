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

package cz.masci.drd.ui.common.controller.battlewizard.controller;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.beans.property.IntegerProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BattlePreparationDuellistController extends CompositeStep {

  private final IntegerProperty groupCount;

  @Override
  protected void initBeforeFirstNext() {
    super.initBeforeFirstNext();

    var groups = IntStream.range(0, groupCount.get())
                          .mapToObj(i -> String.format("Skupina %d", i + 1))
                          .map(BattlePreparationDuellistChildController::new)
                          .collect(Collectors.toList());

    setChildren(groups);
  }
}
