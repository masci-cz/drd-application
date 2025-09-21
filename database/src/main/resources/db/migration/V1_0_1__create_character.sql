/*
 * Copyright (C) 2025 Daniel Masek
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
/**
 * Author:  Daniel
 * Created: 20. 9. 2025
 */
CREATE TABLE ABILITY (
    ABILITY_ID BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    STRENGTH SMALLINT NOT NULL,
    DEXTERITY SMALLINT NOT NULL,
    CONSTITUTION SMALLINT NOT NULL,
    INTELLIGENCE SMALLINT NOT NULL,
    CHARISMA SMALLINT NOT NULL
);

CREATE TABLE "CHARACTER" (
    CHARACTER_ID BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    CLASS VARCHAR(20) NOT NULL,
    LEVEL SMALLINT NOT NULL,
    EXPERIENCE INTEGER NOT NULL,
    ABILITY_ID BIGINT CONSTRAINT ability_foreign_key REFERENCES ABILITY
);
