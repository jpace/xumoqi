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

package org.incava.xumoqi.words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WordMapByChar {
    private final Map<Character, ArrayList<String>> byChar;
    
    public WordMapByChar() {
        byChar = new HashMap<Character, ArrayList<String>>();
    }

    public void addWord(Character ch, String word) {
        ArrayList<String> currList = byChar.get(ch);
        if (currList == null) {
            currList = new ArrayList<String>();
            byChar.put(ch, currList);
        }
        currList.add(word);
    }

    public List<String> getForChar(Character ch) {
        return byChar.get(ch);
    }
    
    public ArrayList<String> getMatches(Character ch, String str) {
        ArrayList<String> matches = new ArrayList<String>();
        List<String> words = getForChar(ch);
        if (words == null) {
            return matches;
        }
        for (String word : words) {
            if (isMatch(word, str)) {
                matches.add(word);
            }
        }
        return matches;
    }

    public abstract boolean isMatch(String word, String str);
}
