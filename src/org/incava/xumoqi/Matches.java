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
}
