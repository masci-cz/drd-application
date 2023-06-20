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
import cz.masci.drd.ui.battle.action.SelectAction;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for preparation of list of {@link SelectAction}.<br>
 * Because the list is used on every {@link cz.masci.drd.ui.battle.slide.impl.BattleSelectActionSlideController} it always returns new list with
 * new instances.
 */
@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

  private final ListableBeanFactory beanFactory;

  /**
   * Always returns new list with new {@link SelectAction} instances.
   *
   * @return New list of SelectActionControl
   */
  @Override
  public Collection<SelectAction> initSelectActionControls() {
    return beanFactory.getBeansOfType(SelectAction.class).values();
  }
}
