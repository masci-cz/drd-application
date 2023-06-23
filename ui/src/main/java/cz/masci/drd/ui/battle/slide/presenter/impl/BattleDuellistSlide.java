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

package cz.masci.drd.ui.battle.slide.presenter.impl;

import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.manager.dto.BattleSlidePropertiesDTO;
import cz.masci.drd.ui.battle.slide.impl.BattleDuellistSlideController;
import cz.masci.drd.ui.battle.slide.presenter.BattleSlide;
import cz.masci.drd.ui.util.iterator.ObservableListIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BattleDuellistSlide implements BattleSlide<BattleDuellistSlideController> {

  private final FxWeaver fxWeaver;
  private final BattleService battleService;

  private final List<BattleDuellistSlideController> controllers = new ArrayList<>();
  private ObservableListIterator<BattleDuellistSlideController> iterator;

  @Override
  public void initProperties(BattleSlidePropertiesDTO properties) {
    properties.getPrevDisableProperty().set(false);
    properties.getPrevTextProperty().set(hasPrevious() ? "Předchozí" : "Zrušit bitvu");
    properties.getNextDisableProperty().bind(Bindings.size(iterator.getCurrent().getDuellists()).lessThan(1));
    properties.getNextTextProperty().set(hasNext() ? "Další" : "Výběr akcí");
    properties.getTitleProperty().set("Bojovníci skupiny " + iterator.getCurrent().getGroup().getName());
  }

  @Override
  public void doBeforeSlide() {
    // Nothing
  }

  @Override
  public void reset() {
    controllers.clear();
    iterator = null;
  }

  @Override
  public void init() {
    for (GroupDTO group : battleService.getGroups()) {
      var controller = fxWeaver.loadController(BattleDuellistSlideController.class);
      controller.setGroup(group);
      controllers.add(controller);
    }
    iterator = new ObservableListIterator<>(controllers);
  }

  @Override
  public BattleDuellistSlideController getController() {
    return iterator.getCurrent();
  }

  @Override
  public Node getCurrentView() {
    return Optional.ofNullable(iterator.getCurrent()).map(BattleDuellistSlideController::getRoot).orElse(null);
  }

  @Override
  public Node getPreviousView() {
    return iterator.previous().getRoot();
  }

  @Override
  public Node getNextView() {
    return iterator.next().getRoot();
  }

  @Override
  public boolean hasPrevious() {
    return iterator.hasPrevious();
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

}
