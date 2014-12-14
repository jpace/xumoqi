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

import org.incava.xumoqi.R;
import org.incava.xumoqi.words.WordList;
import org.incava.xumoqi.words.WordLists;

import android.content.res.Resources;

public class QueryTypeFactory {
    public static QueryType createQueryType(Resources resources,
            String queryTypeStr, int wordLength) {
        if (queryTypeStr.contains("Starting")) {
            return new StartsWithDots(getWordList(resources, wordLength), wordLength);
        }
        else if (queryTypeStr.contains("Random")) {
            return new RandomDots(getWordList(resources, wordLength), wordLength);
        }
        else if (queryTypeStr.contains("Ending")) {
            return new EndsWithDots(getWordList(resources, wordLength), wordLength);
        }
        else if (queryTypeStr.contains("to-make")) {
            return new NToNPlusOne(getWordList(resources, wordLength), getWordList(resources, wordLength + 1));
        }
        else if (queryTypeStr.equals("Q without U")) {
            return createLetterQueryType(resources, R.raw.qnou, wordLength, 'q');
        }
        else if (queryTypeStr.equals("Containing 'Z'")) {
            return createLetterQueryType(resources, R.raw.zs, wordLength, 'z');
        }
        else {
            return createCustomQuery(resources, queryTypeStr);
        }
    }

    private static QueryType createLetterQueryType(Resources resources,
            int res, int wordLength, char letter) {
        List<WordList> wordLists = getWordListList(resources, wordLength);
        return new Letter(resources, wordLists, wordLength, res, letter);
    }

    private static List<WordList> getWordListList(Resources resources,
            int wordLength) {
        List<WordList> wordLists = new ArrayList<WordList>();
        for (int len = 2; len <= wordLength; ++len) {
            wordLists.add(getWordList(resources, len));
        }
        return wordLists;
    }

    private static WordList getWordList(Resources resources, int length) {
        return WordLists.getInstance().getWordList(resources, length);
    }

    private static QueryType createCustomQuery(Resources resources,
            String queryTypeStr) {
        CustomQueryFactory cqf = new CustomQueryFactory();
        QueryType queryType = cqf.createQueryType(resources, queryTypeStr);
        if (queryType == null) {
            throw new RuntimeException("Not handled: queryTypeStr: '"
                    + queryTypeStr + "'");
        } else {
            return queryType;
        }
    }
}
