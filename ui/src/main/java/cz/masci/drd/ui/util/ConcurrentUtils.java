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

package cz.masci.drd.ui.util;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConcurrentUtils {

  /**
   * Starts a background task with the given supplier and post-fetch GUI action.
   *
   * @param backgroundTask  the supplier representing the background task to be executed
   * @param postFetchGuiStuff  the runnable representing the action to be executed on the GUI thread after the background task completes
   * @param <T>  the type of the background task result
   */
  public static <T> void startBackgroundTask(Callable<T> backgroundTask, Runnable postFetchGuiStuff) {
    startBackgroundTask(backgroundTask, postFetchGuiStuff, null, null);
  }

  /**
   * Starts a background task with the given supplier, post-fetch GUI action, and success action.
   *
   * @param backgroundTask  the supplier representing the background task to be executed
   * @param postFetchGuiStuff  the runnable representing the action to be executed on the GUI thread after the background task completes
   * @param onSucceeded  the consumer representing the action to be executed with the result of the background task
   * @param <T>  the type of the background task result
   */
  public static <T> void startBackgroundTask(Callable<T> backgroundTask, Runnable postFetchGuiStuff, Consumer<T> onSucceeded) {
    startBackgroundTask(backgroundTask, postFetchGuiStuff, onSucceeded, null);
  }

  /**
   * Starts a background task with the given supplier, post-fetch GUI action, success action, and failure action.
   *
   * @param backgroundTask  the supplier representing the background task to be executed
   * @param postFetchGuiStuff  the runnable representing the action to be executed on the GUI thread after the background task completes
   * @param onSucceeded  the consumer representing the action to be executed with the result of the background task
   * @param onFailed  the runnable representing the action to be executed if the background task fails
   * @param <T>  the type of the background task result
   */
  public static <T> void startBackgroundTask(Callable<T> backgroundTask, Runnable postFetchGuiStuff, Consumer<T> onSucceeded, Runnable onFailed) {
    requireNonNull(backgroundTask);
    requireNonNull(postFetchGuiStuff);

    Task<T> task = new Task<>() {
      @Override
      protected T call() throws Exception {
        return backgroundTask.call();
      }
    };

    task.setOnSucceeded(evt -> {
      if (onSucceeded != null) {
        onSucceeded.accept(task.getValue());
      }
      postFetchGuiStuff.run();
    });

    if (onFailed != null) {
      task.setOnFailed(evt -> onFailed.run());
    }

    Thread thread = new Thread(task);
    thread.start();
  }

  /**
   * Runs the given GUI-related code in the JavaFX Application Thread.
   *
   * @param guiStuff the runnable representing the GUI-related code to be executed
   */
  public static void runInFXThread(Runnable guiStuff) {
    Platform.runLater(guiStuff);
  }
}
