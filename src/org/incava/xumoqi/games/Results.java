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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

public class Results {
    public enum StatusType { CORRECT, MISSED, INVALID };

    private final Map<String, StatusType> wordToStatus;
    private final List<String> matching;
    private final Word queryWord;
    private final Response response;

    public Results(List<String> matching, Word queryWord, String responseStr) {
        this.wordToStatus = new TreeMap<String, StatusType>();
        this.matching = matching;
        this.queryWord = queryWord;
        
        Util.log("RESULTS", "matching", this.matching);
        Util.log("RESULTS", "queryWord", this.queryWord);

        this.response = new Response(queryWord, responseStr);
        Util.log("RESULTS", "response", this.response);

        Set<String> all = new TreeSet<String>(matching);
        all.addAll(response.getAll());

        for (String x : all) {
        	Util.log("RESULTS", "x", x);
            StatusType status = getStatusType(x);
            Util.log("RESULTS", "status", status);
            wordToStatus.put(x, status);
        }
    }
    
    private StatusType getStatusType(String str) {
        return matching.contains(str) ? (response.contains(str) ? StatusType.CORRECT : StatusType.MISSED) : StatusType.INVALID;    
    }

    public TreeSet<String> getForStatus(StatusType statusType) {
    	TreeSet<String> forStatus = new TreeSet<String>();
        Util.findByValue(wordToStatus, forStatus, statusType);
    	return forStatus;
    }
}
