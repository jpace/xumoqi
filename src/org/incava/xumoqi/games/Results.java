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
import java.util.TreeSet;

import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

import android.os.Parcel;
import android.os.Parcelable;

public class Results {
    public enum StatusType { CORRECT, MISSED, INVALID };

    private final TreeSet<String> correct;
    private final TreeSet<String> missed;
    private final TreeSet<String> invalid;

    public Results(List<String> matching, Word queryWord, String responseStr) {
        Util.log("RESULTS", "matching", matching);

        Response response = new Response(queryWord, responseStr);
        Util.log("RESULTS", "response", response);

        correct = new TreeSet<String>();
        missed = new TreeSet<String>();
        invalid = new TreeSet<String>();

        Set<String> all = new TreeSet<String>(matching);
        all.addAll(response.getAll());

        for (String x : all) {
        	Util.log("RESULTS", "x", x);
            Set<String> set = getStatusSet(response, matching, x);
            Util.log("RESULTS", "set", set);
            set.add(x);
        }
    }
    
    private Set<String> getStatusSet(Response response, List<String> matching, String str) {
        return matching.contains(str) ? (response.contains(str) ? correct : missed) : invalid;
    }

    public TreeSet<String> getForStatus(StatusType statusType) {
        switch (statusType) {
            case CORRECT:
                return correct;
            case MISSED:
                return missed;
            case INVALID:
                return invalid;
        }
        return null;
    }
}
