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

package cz.masci.drd.ui.util.model;

import cz.masci.drd.ui.util.model.lib.Removable;
import cz.masci.drd.ui.util.model.lib.Selectable;
import cz.masci.drd.ui.util.model.lib.Updatable;

/**
 * This list groups {@code Selectable}, {@code Updatable} and {@code Removable} interfaces
 * @param <E>
 */
public interface ListModel<E> extends Selectable<E>, Updatable, Removable<E> {}
