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

package cz.masci.drd.ui.battle.slide.impl;

import static cz.masci.drd.ui.util.PropertyUtility.TRUE_PROPERTY;

import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.drd.dto.GroupDTO;
import cz.masci.drd.service.BattleService;
import cz.masci.drd.ui.battle.slide.BattleSlideController;
import cz.masci.drd.ui.util.slide.SlideService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@FxmlView("fxml/battle-duellist-slide.fxml")
@FxmlController
@Data
@Slf4j
public class BattleDuellistSlideController extends BasicBattleSlideController implements BattleSlideController {

  @Getter
  private BooleanProperty lastGroup = new SimpleBooleanProperty();
  @Getter
  private BooleanProperty firstGroup = new SimpleBooleanProperty();

  private GroupDTO group;

  @Override
  public void onAfterPrev(BattleService battleService, SlideService<BattleSlideController, Node> slideService) {
    if (isFirstGroup()) {
      List<FxControllerAndView<?, ?>> slidesToRemove = new ArrayList<>();
      slideService.getSlides().forEach(nodeFxControllerAndView -> {
        var controller = nodeFxControllerAndView.getController();
        if (controller instanceof BattleDuellistSlideController) {
          slidesToRemove.add(nodeFxControllerAndView);
        }
      });
      slidesToRemove.forEach(slide -> slideService.getSlides().remove(slide));
      battleService.exitBattle();
    }
  }

  @Override
  public String getTitle() {
    return "Bojovníci skupiny: " + Objects.requireNonNullElse(group, new GroupDTO("UNDEFINED")).getName();
  }

  @Override
  public String getPrevTitle() {
    return isFirstGroup() ? "Zrušit bitvu" : "Předchozí";
  }

  @Override
  public String getNextTitle() {
    return isLastGroup() ? "Spustit bitvu" : "Další";
  }

  @Override
  public ObservableBooleanValue validPrevProperty() {
    return TRUE_PROPERTY;
  }

  @Override
  public ObservableBooleanValue validNextProperty() {
    return Bindings.not(lastGroup);
  }

  public boolean isLastGroup() {
    return lastGroup.get();
  }

  public void setLastGroup(boolean value) {
    lastGroup.set(value);
  }

  public boolean isFirstGroup() {
    return firstGroup.get();
  }

  public void setFirstGroup(boolean value) {
    firstGroup.set(value);
  }
}
