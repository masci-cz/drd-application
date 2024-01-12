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

package cz.masci.drd.ui.common.view;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListDetailViewExtendedBuilder implements Builder<Region> {

  private final Region left;
  private final Region center;
  private final Region top;
  private final Region bottom;

  @Override
  public Region build() {
    BorderPane result = new BorderPane();
    result.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));

    Optional.ofNullable(left).ifPresent(result::setLeft);
    Optional.ofNullable(center).ifPresent(result::setCenter);
    Optional.ofNullable(top).ifPresent(result::setTop);
    Optional.ofNullable(bottom).ifPresent(result::setBottom);

    return result;
  }
}
