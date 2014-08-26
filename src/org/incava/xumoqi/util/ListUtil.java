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

package org.incava.xumoqi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtil {
    private static final Random random = new Random();
    
    public static <T> List<T> getEndOfList(List<T> list, int num) {
        int listSize = list.size();
        int fromIdx = listSize - Math.min(listSize, num);
        return list.subList(fromIdx, listSize);
    }
    
    public static <T> T getRandomElement(List<T> list) {
        int idx = random.nextInt(list.size());
        return list.get(idx);
    }

    public static <T> String inspect(List<T> list, String name) {
        StringBuilder sb = new StringBuilder();
        if (list.isEmpty()) {
            sb.append("{0} ").append(name).append('\n');
        }
        else {
            for (int idx = 0; idx < list.size(); ++idx) {
                sb.append(name).append('[').append(idx).append("]: ");
                T element = list.get(idx);
                sb.append(element instanceof Inspectable ? ((Inspectable)element).inspect() : element.toString());
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public static List<Integer> toIntegerList(int[] ary) {
        List<Integer> list = new ArrayList<Integer>();
        for (int idx = 0; idx < ary.length; ++idx) {
            list.add(ary[idx]);
        }
        return list;
    }
    
    public static int[] toIntArray(List<Integer> list) {
        int[] ary = new int[list.size()];
        for (int idx = 0; idx < list.size(); ++idx) {
            ary[idx] = list.get(idx);
        }
        return ary;
    }
    
    /**
     * Returns the nth element in the list. If <code>index</code> is
     * negative, the nth from the last element is returned, where -1
     * is the last element in the list. <code>defaultValue<code> is
     * returned if <code>index</code> is out of range of the list.
     */
    public static <T> T get(List<T> list, int index, T defaultValue) {
        int size = list.size();
        return size == 0 ? defaultValue : list.get(index < 0 ? list.size() + index : index);
    }
    
    /**
     * As above, returning null if <code>index</code> is out of the
     * list range.
     */
    public static <T> T get(List<T> list, int index) {
        return get(list, index, null);
    }
}
