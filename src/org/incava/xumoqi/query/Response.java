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

package org.incava.xumoqi.query;

import java.util.Arrays;
import java.util.List;

import org.incava.xumoqi.utils.Lo;
import org.incava.xumoqi.words.Word;

public class Response {
    private final List<String> strs;

    public Response(Word queryWord, String str) {
        String trimmed = str.trim();
        String[] words = trimmed.split("[\\s,]+");
        strs = Arrays.asList(words);
        updateWithFullWords(queryWord);
    }

    public boolean contains(String str) {
        return strs.contains(str);    
    }
    
    public List<String> getAll() {
        return strs;
    }
    
    public String toString() {
        return strs.toString();
    }
    
    private void updateWithFullWords(Word queryWord) {
        for (int idx = 0; idx < strs.size(); ++idx) {
            String s = strs.get(idx);
            if (s.length() == 1) {
                replaceWithFullWord(queryWord, idx, s.charAt(0));
            }
        }
    }
    
    private void replaceWithFullWord(Word queryWord, int idx, char ch) {
    	Lo.g(this, "queryWord", queryWord);
        String t = queryWord.sub(ch);
    	Lo.g(this, "t", t);
        strs.set(idx, t);
    }
}
