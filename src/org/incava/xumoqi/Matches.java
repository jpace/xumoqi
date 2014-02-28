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

package org.incava.xumoqi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Matches {
    public enum StatusType { CORRECT, MISSED, INVALID };

    private final Map<String, StatusType> matchStatus;

    public Matches(List<String> matching, String response) {
        matchStatus = new TreeMap<String, StatusType>();

        List<String> responseList = response.isEmpty() ? new ArrayList<String>() : Arrays.asList(response.split("[\\s,]+"));

        Set<String> all = new TreeSet<String>(matching);
        all.addAll(responseList);

        for (String x : all) {
            StatusType status;
            if (matching.contains(x)) {
                if (responseList.contains(x)) {
                    status = StatusType.CORRECT;
                }
                else {
                    status = StatusType.MISSED;
                }
            }
            else {
                status = StatusType.INVALID;
            }
            matchStatus.put(x, status);
        }
    }

    /**
     * Returns a TreeSet (i.e., sorted) of all words.
     */
    public Set<String> getAllWords() {
        return matchStatus.keySet();
    }

    public StatusType getStatus(String word) {
        return matchStatus.get(word);
    }
    
    public TreeSet<String> getForStatus(StatusType statusType) {
    	TreeSet<String> forStatus = new TreeSet<String>();
    	
    	for (Map.Entry<String, StatusType> entry : matchStatus.entrySet()) {
    		if (entry.getValue().equals(statusType)) {
    			forStatus.add(entry.getKey());
    		}
    	}
    	return forStatus;
    }
}
