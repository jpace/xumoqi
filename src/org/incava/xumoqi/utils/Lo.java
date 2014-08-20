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

import android.util.Log;

public class Lo {
    public static void g(String component, String msg, Object obj) {
        Log.i(component, msg + ": " + obj);
    }
    
    public static void g(String component, String msg, Inspectable insp) {
        Log.i(component, msg + ": " + insp.inspect());
    }
    
    public static void g(String component, String msg, String str) {
        Log.i(component, msg + ": '" + str + "'");
    }
    
    public static void g(String component, String msg) {
        Log.i(component, msg);
    }
    
    public static void g(Class<?> cls, String msg, Object obj) {
        g(cls.getSimpleName(), msg, obj);
    }
    
    public static void g(Class<?> cls, String msg, Inspectable insp) {
        g(cls.getSimpleName(), msg, insp.inspect());
    }
    
    public static void g(Class<?> cls, String msg, String str) {
        g(cls.getSimpleName(), msg, str);
    }
    
    public static void g(Class<?> cls, String msg) {
        g(cls.getSimpleName(), msg);
    }
    
    public static void g(Object whence, String msg, Object obj) {
        g(whence.getClass().getSimpleName(), msg, obj);
    }
    
    public static void g(Object whence, String msg, Inspectable insp) {
        g(whence.getClass().getSimpleName(), msg, insp.inspect());
    }
    
    public static void g(Object whence, String msg, String str) {
        g(whence.getClass().getSimpleName(), msg, str);
    }

    public static void g(Object whence, String msg) {
        g(whence.getClass().getSimpleName(), msg);
    }
}
