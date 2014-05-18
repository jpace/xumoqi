package org.incava.xumoqi.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.incava.xumoqi.utils.Timer;
import org.incava.xumoqi.utils.Util;
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
	
	public GameQNoU(int length, int nDots) {
		this(getWordList(length), length, nDots);
	}
	
	public GameQNoU(WordList wordList, int length, int nDots) {
		super(wordList, length, nDots, 2);
    	random = new Random();
    	maxLength = length;
	}

	@Override
	public Word getQueryWord() {
		Timer t = new Timer("Q^U", "getQueryWord()");
		
		String re = "twl(\\d).txt:(\\d+): (\\w+)";
		Pattern pat = Pattern.compile(re);
		
		List<String> possibles = new ArrayList<String>();
		
		for (String loc : locations) {
			Matcher matcher = pat.matcher(loc);
			if (matcher.matches()) {
				String lenstr = matcher.group(1);
				Integer len = Integer.valueOf(lenstr);

				if (len <= maxLength) {
					// String line = matcher.group(2);
					// Util.log(getClass(), "line", line);
					String word = matcher.group(3);
					Util.log(getClass(), "word", word);
					possibles.add(word);
				}
			}
		}

		Util.log(getClass(), "possibles", possibles);

		int idx = random.nextInt(possibles.size());
		String qword = possibles.get(idx);
		Util.log(getClass(), "qword", qword);

		int blankIdx = random.nextInt(qword.length());
		
		matching = new ArrayList<String>();
		matching.add(qword);
		t.done();
		
		return new Word(qword, blankIdx);
	}

	@Override
	public ArrayList<String> getMatching(Word queryWord) {
		return matching;
	}

	@Override
	public String getAsQuery(Word word) {
		return word.asQuery();
	}
}
