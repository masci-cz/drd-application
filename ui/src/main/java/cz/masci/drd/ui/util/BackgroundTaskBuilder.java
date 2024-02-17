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

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import javafx.concurrent.Task;
import org.apache.commons.lang3.function.FailableFunction;

/**
 * Starts a background task with the given supplier, post-fetch GUI action, success action, and failure action.
 *
 * the supplier representing the background task to be executed
 * the runnable representing the action to be executed on the GUI thread after the background task completes
 * the consumer representing the action to be executed with the result of the background task
 * the runnable representing the action to be executed if the background task fails
 * @param <T>  the type of the background task result
 */
public class BackgroundTaskBuilder<T> {
  private Callable<T> callableTask;
  private FailableFunction<Task<T>, T, ? extends Exception> functionalTask;
  private Runnable postGuiCall;
  private Consumer<Task<T>> onCancelled;
  private Consumer<Task<T>> onFailed;
  private Consumer<Task<T>> onRunning;
  private Consumer<Task<T>> onScheduled;
  private Consumer<T> onSucceeded;

  private BackgroundTaskBuilder() {}

  private BackgroundTaskBuilder(Callable<T> callableTask) {
    this.callableTask = callableTask;
  }

  private BackgroundTaskBuilder(FailableFunction<Task<T>, T, ? extends Exception> functionalTask) {
    this.functionalTask = functionalTask;
  }

  public static <T> BackgroundTaskBuilder<T> task(Callable<T> callableTask) {
    return new BackgroundTaskBuilder<>(callableTask);
  }

  public static <T> BackgroundTaskBuilder<T> task(FailableFunction<Task<T>, T, ? extends Exception> functionalTask) {
    return new BackgroundTaskBuilder<>(functionalTask);
  }

  public void start() {
    Task<T> task = build();

    Thread thread = new Thread(task);
    thread.start();
  }

  public Task<T> build() {
    Task<T> task = new Task<>() {
      @Override
      protected T call() throws Exception {
        if (callableTask != null) {
          return callableTask.call();
        }
        if (functionalTask != null) {
          return functionalTask.apply(this);
        }
        return null;
      }

    };

    setOnCancelled(task);
    setOnFailed(task);
    setOnRunning(task);
    setOnScheduled(task);
    setOnSucceeded(task);

    return task;
  }

  private void setOnCancelled(Task<T> task) {
    task.setOnCancelled(evt -> {
      if (onCancelled != null) {
        onCancelled.accept(task);
      }
      if (postGuiCall != null) {
        postGuiCall.run();
      }
    });
  }

  private void setOnFailed(Task<T> task) {
    task.setOnFailed(evt -> {
      if (onFailed != null) {
        onFailed.accept(task);
      }
      if (postGuiCall != null) {
        postGuiCall.run();
      }
    });
  }

  private void setOnRunning(Task<T> task) {
    task.setOnRunning(evt -> {
      if (onRunning != null) {
        onRunning.accept(task);
      }
    });
  }

  private void setOnScheduled(Task<T> task) {
    task.setOnScheduled(evt -> {
      if (onScheduled != null) {
        onScheduled.accept(task);
      }
    });
  }

  private void setOnSucceeded(Task<T> task) {
    task.setOnSucceeded(evt -> {
      if (onSucceeded != null) {
        onSucceeded.accept(task.getValue());
      }
      if (postGuiCall != null) {
        postGuiCall.run();
      }
    });
  }

  public BackgroundTaskBuilder<T> postGuiCall(Runnable postGuiCall) {
    this.postGuiCall = postGuiCall;

    return this;
  }

  public BackgroundTaskBuilder<T> onCancelled(Consumer<Task<T>> onCancelled) {
    this.onCancelled = onCancelled;

    return this;
  }

  public BackgroundTaskBuilder<T> onFailed(Consumer<Task<T>> onFailed) {
    this.onFailed = onFailed;

    return this;
  }

  public BackgroundTaskBuilder<T> onRunning(Consumer<Task<T>> onRunning) {
    this.onRunning = onRunning;

    return this;
  }

  public BackgroundTaskBuilder<T> onScheduled(Consumer<Task<T>> onScheduled) {
    this.onScheduled = onScheduled;

    return this;
  }

  public BackgroundTaskBuilder<T> onSucceeded(Consumer<T> onSucceeded) {
    this.onSucceeded = onSucceeded;

    return this;
  }
}
