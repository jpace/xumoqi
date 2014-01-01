package org.incava.xumoqi;

import java.util.*;

public class Matches {
    private final Map<String, StatusType> matchStatus;

    public enum StatusType { CORRECT, MISSED, INVALID };

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

    public Set<String> getAllWords() {
        return matchStatus.keySet();
    }

    public StatusType getStatus(String word) {
        return matchStatus.get(word);
    }
}
