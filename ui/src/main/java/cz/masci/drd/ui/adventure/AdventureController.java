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
package cz.masci.drd.ui.adventure;

import cz.masci.commons.springfx.controller.AbstractMFXMasterController;
import cz.masci.commons.springfx.fxml.annotation.FxmlController;
import cz.masci.commons.springfx.service.CrudService;
import cz.masci.drd.dto.AdventureDTO;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.stereotype.Component;

/**
 *
 * @author Daniel
 */
@Component
@Slf4j
@FxmlController
public class AdventureController extends AbstractMFXMasterController<AdventureDTO> {

  public AdventureController(FxWeaver fxWeaver, CrudService<AdventureDTO> itemService) {
    super(fxWeaver, itemService, AdventureDetailDialogController.class);
  }

  @Override
  protected void init() {
    MFXTableColumn<AdventureDTO> name = new MFXTableColumn<>("Název");
    name.setPrefWidth(250);
    name.setRowCellFactory(createMFXTableRowCell(AdventureDTO::getName));
    
    addColumns(name);
    
    setDetailController(AdventureDetailEditorController.class);
    setRowFactory("edited-row");
  }

  private <T> Function<T, MFXTableRowCell<T, ?>> createMFXTableRowCell(Function<T, ?> tableRowCellConverter) {
    return tableRowType -> new MFXTableRowCell<>(tableRowCellConverter);
  }
}
