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

import java.util.Arrays;
import java.util.List;

import android.util.Log;

public class Lo {
    public static void g(String msg, Object obj) {
        gobj(msg, obj);
    }
    
    public static void g(String msg, Inspectable insp) {
        gobj(msg, insp == null ? null : insp.inspect());
    }
    
    public static void g(String msg, String str) {
        gobj(msg, "'" + str + "'");
    }
    
    public static void g(String msg) {
        gobj(msg, null);
    }
    
    public static void time(String msg) {
        long currTime = System.currentTimeMillis();
        Lo.g(msg, currTime);
    }

    private static void gobj(String msg, Object obj) {
        StackTraceElement caller = getCaller();
        Log.i(StackTraceUtil.getFileLine(caller), StackTraceUtil.getClassMethod(caller) + " " + getMessage(msg, obj));
    }

    private static StackTraceElement getCaller() {
        List<String> skipClasses = Arrays.asList("org.incava.xumoqi.util.Lo");
        return StackTraceUtil.getCaller(skipClasses);
    }
    
    private static String getMessage(String msg, Object obj) {
        return msg + (obj == null ? "" : ": " + obj);
    }
}
