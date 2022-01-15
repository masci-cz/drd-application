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
package cz.masci.springfx.exception;

/**
 *
 * @author Daniel
 */
public class CrudException extends Exception {

  private static final String READ_EXCEPTION_TEXT = "Read error";
  private static final String WRITE_EXCEPTION_TEXT = "Write error";

  public CrudException(String message, Throwable cause) {
    super(message, cause);
  }

  public CrudException(String message) {
    super(message);
  }
  
  public static final CrudException createReadException(Throwable throwable) {
    return new CrudException(READ_EXCEPTION_TEXT, throwable);
  }

  public static final CrudException createWriteException(Throwable throwable) {
    return new CrudException(WRITE_EXCEPTION_TEXT, throwable);
  }

}
