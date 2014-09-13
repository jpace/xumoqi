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

package org.incava.xumoqi.querytype;

import java.util.ArrayList;
import java.util.List;

import org.incava.xumoqi.android.ResourceUtil;
import org.incava.xumoqi.util.Lo;
import org.incava.xumoqi.words.Word;

import android.content.res.Resources;

public class CustomQuery implements QueryType {
    private final String pattern;
    private final String hint;
    private final List<String> words;
    
    public CustomQuery(Resources resources, int res) {
        List<String> lines = ResourceUtil.getTextResource(resources, res);
        String type = getFieldValue(lines, 0);
        Lo.g("type", type);
        this.pattern = getFieldValue(lines, 1);
        Lo.g("pattern", pattern);
        this.hint = getFieldValue(lines, 2);
        Lo.g("hint", hint);
        this.words = lines.subList(3, lines.size());
        Lo.g("words", words);
    }

    public Word getQueryWord() {
        int index = pattern.indexOf('.');
        return new Word(pattern, index);
    }

    public ArrayList<String> getMatching(Word queryWord) {
        return new ArrayList<String>(words);
    }
    
    public String getHint() {
        return hint;
    }
    
    private String getFieldValue(List<String> lines, int lineNum) {
        return lines.get(lineNum).replaceFirst(".*:\\s*", "");
    }
}
