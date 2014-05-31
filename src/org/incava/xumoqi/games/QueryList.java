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
import java.util.TreeMap;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryList implements Parcelable {
	private final static Random random = new Random();
	
    public static final Parcelable.Creator<QueryList> CREATOR = new Parcelable.Creator<QueryList>() {
        public QueryList createFromParcel(Parcel parcel) {
            return new QueryList(parcel);
        }
        
        public QueryList[] newArray(int size) {
            return new QueryList[size];
        }
    };

    public final static int MAX_QUERIES = 5;

    // TODO: keep this to a fixed size
    private final ArrayList<Query> queries;

    public QueryList() {
        this.queries = new ArrayList<Query>(MAX_QUERIES);
    }

    public QueryList(Query query) {
    	this();
        this.queries.add(query);
    }
    
    protected QueryList(Parcel parcel) {
    	this();
        parcel.readList(this.queries, Query.class.getClassLoader());
    }
    
    public ArrayList<Query> getQueries() {
        return queries;
    }
    
    public Query getQuery(int idx) {
    	return queries.get(idx < 0 ? queries.size() + idx : idx);
    }

    public void addQuery(Query query) {
        queries.add(query);
        while (queries.size() > MAX_QUERIES) {
        	queries.remove(0);
        }
    }
    
    public int size() {
    	return queries.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeList(queries);
    }
    
    public String toString() {
        return "queries: " + queries;
    }
    
    public String inspect() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("#queries: " + queries.size() + "\n");
    	for (int idx = 0; idx < queries.size(); ++idx) {
    		sb.append("query[" + idx + "]: " + queries.get(idx) + "\n");
    	}
    	return sb.toString();
    }

    public Query getRandomQuery() {
    	// this is sorted so lower scores are processed first:
    	TreeMap<Integer, ArrayList<Query>> byScore = getByScore();
		for (Integer score : byScore.keySet()) {
			int rnd = random.nextInt(Results.MAX_SCORE);
			if (rnd > score) {
				ArrayList<Query> forScore = byScore.get(score);
				int sz = forScore.size();
				int rIdx = random.nextInt(sz);
	    		return forScore.get(rIdx);
			}
		}
		return null;
    }
    
    public int indexOf(Query query) {
    	return queries.indexOf(query);
    }

    private TreeMap<Integer, ArrayList<Query>> getByScore() {
    	TreeMap<Integer, ArrayList<Query>> queriesByScore = new TreeMap<Integer, ArrayList<Query>>();
    
    	for (Query q : queries) {
    		int score = q.getScore();
    		ArrayList<Query> current = queriesByScore.get(score);
    		if (current == null) {
				current = new ArrayList<Query>();
				queriesByScore.put(score, current);
			}
    		current.add(q);
    	}
    	return queriesByScore;
    }
}
