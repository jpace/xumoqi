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
import java.util.Random;

import org.incava.xumoqi.android.ResourceUtil;
import org.incava.xumoqi.util.Timer;
import org.incava.xumoqi.words.GrepList;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

import android.content.res.Resources;

public class Letter implements QueryType {
    private final Random random;
    private final GrepList grepList;
    private final List<WordList> wordLists;
    private final char rootLetter;
  
    public Letter(Resources resources, List<WordList> wordLists, int maxLength, int resource, char rootLetter) {
        List<String> grepLines = ResourceUtil.getTextResource(resources, resource);
        // index 0 == wordList of length 2:
        this.wordLists = wordLists;
        random = new Random();
        String lineRe = getLineRegexp(maxLength);
        grepList = new GrepList(grepLines, lineRe);
        this.rootLetter = rootLetter;
    }
  
    @Override
    public Word getQueryWord() {
        String qword = grepList.getRandomWord();
        int blankIdx = getBlankIndex(qword);
        return new Word(qword, blankIdx);
    }

    @Override
    public ArrayList<String> getMatching(Word queryWord) {
        int length = queryWord.length();
        WordList wordList = wordLists.get(length - 2);
        Timer t = new Timer(this, "length" + length);
        ArrayList<String> matching = wordList.getMatching(queryWord);
        t.done();
        return matching;
    }
  
    private int getBlankIndex(String str) {
        int len = str.length();
        while (true) {
            int chIdx = random.nextInt(len);
            if (str.charAt(chIdx) != rootLetter) {
                return chIdx;
            }
        }
    }

    private String getLineRegexp(int maxLength) {
        // output from grep -N 'q[^u]' twl*
        StringBuilder sb = new StringBuilder("twl(?:");
        sb.append(maxLength >= 10 ? "[0-9]|1\\d+" : "[0-" + maxLength + "]");
        sb.append(").txt:(\\w+)");
        return sb.toString();
    }
}
