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

public class Timer {
    private final String component;
    private final String activity;
    private final long start;

    public Timer(String component, String activity) {
        this.component = component;
        this.activity = activity;
        start = System.currentTimeMillis();
        log("start");
    }

    public Timer() {
        this("", "");
    }

    public Timer(Class<?> cls, String activity) {
        this(cls.getSimpleName(), activity);
    }

    public Timer(Object obj, String activity) {
        this(obj.getClass().getSimpleName(), activity);
    }

    public void done(String msg) {
        long end = System.currentTimeMillis();
        log("duration: " + msg, end - start);
    }

    public void done() {
        long end = System.currentTimeMillis();
        log("duration", end - start);
    }
    
    public long getDuration() {
        return System.currentTimeMillis() - start;
    }
    
    private void log(String what, long time) {
        Log.i(component, activity + ": " + what + ": " + time);
    }
    
    private void log(String what) {
        Log.i(component, activity + ": " + what);
    }
}
