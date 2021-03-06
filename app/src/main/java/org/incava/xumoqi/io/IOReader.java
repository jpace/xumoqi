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

package org.incava.xumoqi.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOReader {
    public void readStream(InputStream is, Integer maxLength) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                int len = line.length();
                if (maxLength != null && len > maxLength) {
                    break;
                }
                else {
                    onRead(line, len);
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void readStream(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                onRead(line);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void onRead(String str, Integer len) {
    }
    
    public void onRead(String str) {
    }

    public static List<String> readTextStream(InputStream is) {
        final List<String> list = new ArrayList<String>();
        IOReader ior = new IOReader() {
            public void onRead(String str) {
                list.add(str);
            }
        };
        ior.readStream(is);
        return list;
    }
}
