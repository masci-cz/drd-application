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

package cz.masci.drd.ui.battle.action.impl;

import cz.masci.drd.ui.battle.action.ActionService;
import cz.masci.drd.ui.battle.action.ActionSelectionControl;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {

  @Getter
  private final List<ActionSelectionControl> actions = new ArrayList<>();

  public ActionServiceImpl(ListableBeanFactory beanFactory) {
    var loadedActions = beanFactory.getBeansOfType(ActionSelectionControl.class).values();
    actions.addAll(loadedActions);
  }

}
