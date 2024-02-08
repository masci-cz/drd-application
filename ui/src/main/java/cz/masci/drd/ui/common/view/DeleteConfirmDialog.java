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

import cz.masci.springfx.mvci.view.builder.ButtonBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import java.util.function.Consumer;
import javafx.scene.Node;
import javafx.stage.Modality;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DeleteConfirmDialog {

  private static MFXGenericDialog dialogContent;
  private static MFXStageDialog dialog;

  public static void show(String title, String confirmationText, Consumer<Runnable> deleteAction) {
    var deleteConfirmDialog = getDialog();
    deleteConfirmDialog.setTitle(title);
    var deleteConfirmDialogContent = getDialogContent();
    deleteConfirmDialogContent.setContentText(confirmationText);
    deleteConfirmDialogContent.clearActions();
    deleteConfirmDialogContent.addActions(
        (Node) ButtonBuilder.builder().text("Smazat").command(deletePostGuiStuff -> deleteAction.accept(() -> {
          deletePostGuiStuff.run();
          dialog.close();
        })).build(MFXButton::new),
        ButtonBuilder.builder().text("Zrušit").command(dialog::close).build(MFXButton::new)
    );
    deleteConfirmDialogContent.setMaxSize(400, 150);

    dialog.showAndWait();
  }

  private static MFXStageDialog getDialog() {
    if (dialog == null) {
      dialog = MFXGenericDialogBuilder.build(getDialogContent())
          .toStageDialogBuilder()
          .setScrimOwner(false)
          .setCenterInOwnerNode(false)
          .initModality(Modality.APPLICATION_MODAL)
          .get();
    }

    return dialog;
  }

  private static MFXGenericDialog getDialogContent() {
    if (dialogContent == null) {
      dialogContent = MFXGenericDialogBuilder.build()
          .setShowAlwaysOnTop(false)
          .setShowMinimize(false)
          .get();
    }
    return dialogContent;
  }
}
