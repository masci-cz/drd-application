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

import cz.masci.drd.ui.battle.action.ActionController;
import cz.masci.drd.ui.battle.action.ActionService;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {

  private final List<FxControllerAndView<ActionController, Node>> actions = new ArrayList<>();

//  public ActionServiceImpl(ListableBeanFactory beanFactory) {
//    var loadedActions = Stream.of(beanFactory.getBeanNamesForAnnotation(Action.class))
//        .map(beanName -> beanFactory.getBean(beanName, ActionDTO.class))
//        .toList();
//    actions.addAll(loadedActions);
//  }

  public ActionServiceImpl(FxWeaver fxWeaver) {
    var fxControllerAndView = fxWeaver.load(ActionController.class);
    actions.add(fxControllerAndView);
  }

  @Override
  public List<FxControllerAndView<ActionController, Node>> getActions() {
    return this.actions;
  }
}
