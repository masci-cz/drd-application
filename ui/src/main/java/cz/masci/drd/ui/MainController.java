/*
 * Copyright (C) 2021 Daniel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.masci.drd.ui;

import cz.masci.drd.service.MonsterService;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@FxmlView("main-scene.fxml")
@RequiredArgsConstructor
public class MainController {
    
    private final MonsterService monsterService;
    private final Random random = new Random();
    
    @FXML
    private Label monsterLabel;
    
    public void loadNextMonster(ActionEvent actionEvent) {
        var index = random.nextInt(10);
        var monster = monsterService.getById(Integer.valueOf(index).longValue());
        
        monsterLabel.setText(monster.isPresent() ? monster.get().getName() : "Monster not found");
    }
}
