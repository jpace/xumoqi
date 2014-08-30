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

import org.incava.xumoqi.util.ListUtil;
import org.incava.xumoqi.util.Lo;
import org.incava.xumoqi.util.StringListUtil;

public class GrepList {
    private final List<String> words;

    public GrepList(List<String> lines, String lineRe) {
        this.words = new ArrayList<String>();
        
        List<Matcher> matchers = StringListUtil.getMatchers(lines, lineRe);
        for (Matcher m : matchers) {
            String word = m.group(1);
            this.words.add(word);
        }
    }

    public String getRandomWord() {
        String qword = ListUtil.getRandomElement(words);
        Lo.g("qword", qword);
        return qword;
    }
}
