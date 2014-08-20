package org.incava.xumoqi.games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.incava.xumoqi.utils.ListUtil;
import org.incava.xumoqi.utils.Lo;
import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.words.Word;
import org.incava.xumoqi.words.WordList;

public class GameQNoU extends GameDottedWords {
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
    private final int maxLength;
    private final String re = "twl(\\d).txt:(\\d+): (\\w+)";
    private final Pattern pattern = Pattern.compile(re);
    
    // @TODO: hard-coded word list for now (see above);
    // will be a new word list and/or index list (into elements in word list).
    // so this wordList is not currently used.
    public GameQNoU(WordList wordList, int length, int nDots) {
        super(wordList, length);
        random = new Random();
        maxLength = length;
    }
    
    // @TODO: eliminate this -- this shouldn't subclass GameDottedWords.
    public int getBlankIndex(int length) {
        return -1;
    }

    @Override
    public Word getQueryWord() {
        Timer t = new Timer("Q^U", "getQueryWord()");
        
        List<String> possibles = getWordsUpToMaxLength();
        Lo.g("possibles", possibles);

        String qword = ListUtil.getRandomElement(possibles);
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

    private List<String> getWordsUpToMaxLength() {
        List<String> possibles = new ArrayList<String>();
        
        for (String location : locations) {
            String word = parseLine(location);
            if (word != null) {
                possibles.add(word);
            }
        }
        return possibles;
    }
    
    private String parseLine(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            String lenstr = matcher.group(1);
            Integer len = Integer.valueOf(lenstr);

            if (len <= maxLength) {
                // String line = matcher.group(2);
                // Util.log(this, "line", line);
                String word = matcher.group(3);
                Lo.g("word", word);
                return word;
            }
        }
        return null;
    }
}
