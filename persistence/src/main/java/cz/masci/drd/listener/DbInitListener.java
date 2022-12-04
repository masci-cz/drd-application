package cz.masci.drd.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Initiate derby system home folder based on properties {@code db.folder.home} and {@code db.folder.app}
 */
@Component
@Slf4j
public class DbInitListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  /** Configuration variable to set home folder of database. */
  public static final String DB_FOLDER_HOME = "db.folder.home";
  /** Configuration variable to set */
  public static final String DB_FOLDER_APP = "db.folder.app";
  /** Derby system variable used for setting home database folder */
  private static final String DERBY_SYSTEM_HOME = "derby.system.home";

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    var dbFolderHome = event.getEnvironment().getProperty(DB_FOLDER_HOME, ".");
    var dbFolderApp = event.getEnvironment().getProperty(DB_FOLDER_APP, "");
    System.getProperties().setProperty(DERBY_SYSTEM_HOME, dbFolderHome + "/" + dbFolderApp);
    log.debug("Set {} to {}/{}", DERBY_SYSTEM_HOME, dbFolderHome, dbFolderApp);
  }

}
