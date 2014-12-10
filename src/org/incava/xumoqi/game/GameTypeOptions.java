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

  This program includes code from the GPL'd program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.game;

import org.incava.xumoqi.R;
import org.incava.xumoqi.util.Util;

public class GameTypeOptions {
    public int getMax() {
        return Util.type ? 14 : 5;
    }
    
    public int getGameTypes() {
        return Util.type ? R.array.pro_game_types : R.array.free_game_types;
    }
    
    public int getDefaultLength() {
        return Util.type ? 4 : 2;
    }
    
    public int getDefaultGameTypeIndex() {
        // 2: random blank; 4: q-no-u
        return Util.type ? 2 : 0;
    }
}
