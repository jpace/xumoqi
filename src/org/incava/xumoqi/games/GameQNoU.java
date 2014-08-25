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

package org.incava.xumoqi.games;

import java.util.ArrayList;
import java.util.Random;

import org.incava.xumoqi.utils.Lo;
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.words.GrepList;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public class GameQNoU implements Game {
    // output from grep -n 'q[^u]{,4}$' twl*
    private final String[] locations = new String[] {
        "twl2.txt:80: qi", 
        "twl3.txt:719: qat", 
        "twl3.txt:720: qis", 
        "twl3.txt:834: suq", 
        "twl4.txt:2822: qadi", 
        "twl4.txt:2823: qaid", 
        "twl4.txt:2824: qats", 
        "twl4.txt:2825: qoph", 
        "twl4.txt:3358: suqs", 
        "twl5.txt:1103: burqa", 
        "twl5.txt:2524: faqir", 
        "twl5.txt:6036: qadis", 
        "twl5.txt:6037: qaids", 
        "twl5.txt:6038: qanat", 
        "twl5.txt:6039: qophs", 
        "twl5.txt:8013: tranq", 
        "twl5.txt:8199: umiaq", 
        "twl6.txt:1861: buqsha", 
        "twl6.txt:1893: burqas", 
        "twl6.txt:4676: faqirs", 
        "twl6.txt:12278: sheqel", 
        "twl6.txt:14209: tranqs", 
        "twl6.txt:14496: umiaqs", 
        "twl7.txt:2821: buqshas", 
        "twl7.txt:18845: sheqels", 
        "twl8.txt:15259: mbaqanga", 
        "twl8.txt:23244: sheqalim",
    };
    
    private final Random random;
    private ArrayList<String> matching;
    private final GrepList grepList;
    
    // @TODO: hard-coded word list for now (see above)
    public GameQNoU(WordList wordList, int length) {
        random = new Random();
        grepList = new GrepList(locations, length);
    }
    
    @Override
    public Word getQueryWord() {
        Timer t = new Timer("Q^U", "getQueryWord()");
        String qword = grepList.getRandomWord();
        Lo.g("qword", qword);

        int blankIdx = getBlankIndex(qword);
        
        matching = new ArrayList<String>();
        matching.add(qword);
        t.done();
        
        return new Word(qword, blankIdx);
    }

    @Override
    public ArrayList<String> getMatching(Word queryWord) {
        return matching;
    }
    
    private int getBlankIndex(String str) {
        while (true) {
            int chIdx = random.nextInt(str.length());
            Lo.g("chIdx", chIdx);
            if (str.charAt(chIdx) != 'q') {
                return chIdx;
            }
        }
    }
}
