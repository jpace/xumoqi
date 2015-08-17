/*
  XuMoQi - word game program
  Copyright (C) 2014  Jeff Pace

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along
  with this program; if not, write to the Free Software Foundation, Inc.,
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

  See https://raw.github.com/jpace/xumoqi/master/LICENSE for the full license.

  The full source code for this program is available at:
  http://github.com/jpace/xumoqi

  This program includes code from the GPL program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.game;

import java.util.List;

import org.incava.xumoqi.querytype.custom.CustomQueryFactory;
import org.incava.xumoqi.util.Lo;

public class GameQueryTypesFactory {
    public static GameQueryTypes create(String gameType) {
        Lo.g("gameType", gameType);
        if (gameType.equals("With hints")) {
            CustomQueryFactory cqf = new CustomQueryFactory();
            List<String> queryTypes = cqf.getTypes();
            return new GameQueryTypes(queryTypes);
        }
        else {
            return new GameQueryTypes(gameType);
        }
    }
}
