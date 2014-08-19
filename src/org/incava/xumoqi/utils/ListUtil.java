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

package org.incava.xumoqi.utils;

import java.util.List;
import java.util.Random;

public class ListUtil {
    private static final Random random = new Random();
    
    public static <T> List<T> getEndOfList(List<T> list, int num) { 
        int fromIdx = list.size() - Math.min(list.size(), num);
        return list.subList(fromIdx, list.size());
    }
    
    public static <T> T getRandomElement(List<T> list) {
        int idx = random.nextInt(list.size());
        return list.get(idx);
    }

    public static <T> String inspect(List<T> list, String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("#" + name + "(s): " + list.size() + "\n");
        for (int idx = 0; idx < list.size(); ++idx) {
            sb.append(name + "[" + idx + "]: " + list.get(idx) + "\n");
        }
        return sb.toString();
    }
}
