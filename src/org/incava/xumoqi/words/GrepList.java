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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.incava.xumoqi.utils.ListUtil;
import org.incava.xumoqi.utils.Lo;

public class GrepList {
    private final String re = "twl(\\d).txt:(\\d+): (\\w+)";
    private final Pattern pattern = Pattern.compile(re);
    
    private final String[] lines;
    private final int maxLength;

    public GrepList(String[] lines, int maxLength) {
        this.lines = lines;
        this.maxLength = maxLength;
    }

    public String getRandomWord() {
        List<String> possibles = getWordsUpToMaxLength();
        Lo.g("possibles", possibles);

        String qword = ListUtil.getRandomElement(possibles);
        Lo.g("qword", qword);

        return qword;
    }

    private List<String> getWordsUpToMaxLength() {
        List<String> possibles = new ArrayList<String>();
        
        for (String line : lines) {
            String word = parseLine(line);
            if (word != null) {
                possibles.add(word);
            }
        }
        return possibles;
    }

    private String getMatchWord(Matcher matcher) {
        String lenstr = matcher.group(1);
        Integer len = Integer.valueOf(lenstr);

        if (len <= maxLength) {
            // String line = matcher.group(2);
            // Util.log(this, "line", line);
            String word = matcher.group(3);
            Lo.g("word", word);
            return word;
        }
        else {
            return null;
        }
    }
    
    private String parseLine(String line) {
        Matcher matcher = pattern.matcher(line);
        return matcher.matches() ? getMatchWord(matcher) : null;
    }
}
