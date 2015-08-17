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

package org.incava.xumoqi.util;

import java.util.Arrays;
import java.util.List;

import org.incava.xumoqi.lang.Inspectable;

import android.util.Log;

public class Lo {
    enum LogType { VERBOSE, DEBUG, INFO, WARN, ERROR }

    public static void g(String msg, Object obj) {
        log(LogType.INFO, msg, obj);
    }
    
    public static void g(String msg, Inspectable insp) {
        log(LogType.INFO, msg, insp == null ? null : insp.inspect());
    }
    
    public static void g(String msg, String str) {
        log(LogType.INFO, msg, "'" + str + "'");
    }
    
    public static void g(String msg) {
        log(LogType.INFO, msg, null);
    }
    
    public static void v(String msg, Object obj) {
        log(LogType.VERBOSE, msg, obj);
    }
    
    public static void v(String msg, Inspectable insp) {
        log(LogType.VERBOSE, msg, insp == null ? null : insp.inspect());
    }
    
    public static void v(String msg, String str) {
        log(LogType.VERBOSE, msg, "'" + str + "'");
    }
    
    public static void v(String msg) {
        log(LogType.VERBOSE, msg, null);
    }
    
    public static void d(String msg, Object obj) {
        log(LogType.DEBUG, msg, obj);
    }
    
    public static void d(String msg, Inspectable insp) {
        log(LogType.DEBUG, msg, insp == null ? null : insp.inspect());
    }
    
    public static void d(String msg, String str) {
        log(LogType.DEBUG, msg, "'" + str + "'");
    }
    
    public static void d(String msg) {
        log(LogType.DEBUG, msg, null);
    }
    
    public static void i(String msg, Object obj) {
        log(LogType.INFO, msg, obj);
    }
    
    public static void i(String msg, Inspectable insp) {
        log(LogType.INFO, msg, insp == null ? null : insp.inspect());
    }
    
    public static void i(String msg, String str) {
        log(LogType.INFO, msg, "'" + str + "'");
    }
    
    public static void i(String msg) {
        log(LogType.INFO, msg, null);
    }
    
    public static void w(String msg, Object obj) {
        log(LogType.WARN, msg, obj);
    }
    
    public static void w(String msg, Inspectable insp) {
        log(LogType.WARN, msg, insp == null ? null : insp.inspect());
    }
    
    public static void w(String msg, String str) {
        log(LogType.WARN, msg, "'" + str + "'");
    }
    
    public static void w(String msg) {
        log(LogType.WARN, msg, null);
    }
    
    public static void e(String msg, Object obj) {
        log(LogType.ERROR, msg, obj);
    }
    
    public static void e(String msg, Inspectable insp) {
        log(LogType.ERROR, msg, insp == null ? null : insp.inspect());
    }
    
    public static void e(String msg, String str) {
        log(LogType.ERROR, msg, "'" + str + "'");
    }
    
    public static void e(String msg) {
        log(LogType.ERROR, msg, null);
    }
    
    public static void time(String msg) {
        long currTime = System.currentTimeMillis();
        Lo.g(msg, currTime);
    }

    private static StackTraceElement getCaller() {
        List<String> skipClasses = Arrays.asList("org.incava.xumoqi.util.Lo");
        return StackTraceUtil.getCaller(skipClasses);
    }
    
    private static String getMessage(String msg, Object obj) {
        return msg + (obj == null ? "" : ": " + obj);
    }

    public static void log(LogType logType, String msg, Object obj) {
        StackTraceElement caller = getCaller();
        String logMsg = StackTraceUtil.getClassMethod(caller) + " " + getMessage(msg, obj);
        String fileLine = StackTraceUtil.getFileLine(caller);
        int priority = getPriority(logType);
        Log.println(priority, fileLine, logMsg);
    }

    private static int getPriority(LogType logType) {
        switch (logType) {
            case VERBOSE: return Log.VERBOSE;
            case DEBUG:   return Log.DEBUG;
            case INFO:    return Log.INFO;
            case WARN:    return Log.WARN;
            case ERROR:   return Log.ERROR;
            default:      return -1;
        }
    }
}
