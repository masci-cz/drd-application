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
package cz.masci.drd.ui.adventure.control;

import cz.masci.commons.springfx.fxml.annotation.FxmlRoot;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * FXML Controller class
 *
 * @author Daniel
 */
@Service
@Scope("prototype")
@FxmlRoot("fxml/adventure-detail.fxml")
public class AdventureDetailControl extends GridPane {

  private ValidationSupport validationSupport;

  @FXML
  private TextField name;

  /**
   * Initializes the controller class.
   */
  public void initialize() {
    validationSupport = new ValidationSupport();
    validationSupport.registerValidator(name, Validator.createEmptyValidator("Název dobrodružství je povinný"));
  }

  public final ReadOnlyBooleanProperty invalidProperty() {
    return validationSupport.invalidProperty();
    
  }
  
  public Boolean isInvalid() {
    return validationSupport.isInvalid();
  }
  
  public final StringProperty nameProperty() {
    return name.textProperty();
  }

  public String getName() {
    return nameProperty().get();
  }

  public void setName(String name) {
    nameProperty().set(name);
  }

}
