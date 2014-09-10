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

package org.incava.xumoqi.query;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import org.incava.xumoqi.lang.Inspectable;
import org.incava.xumoqi.util.*;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryList implements Parcelable, Inspectable {
    private final static Random random = new Random();
    
    public static final Parcelable.Creator<QueryList> CREATOR = new Parcelable.Creator<QueryList>() {
        public QueryList createFromParcel(Parcel parcel) {
            return new QueryList(parcel);
        }
        
        public QueryList[] newArray(int size) {
            return new QueryList[size];
        }
    };

    private final ArrayList<Query> queries;

    public QueryList() {
        this.queries = new ArrayList<Query>();
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
        return ListUtil.get(queries, idx);
    }

    public void addQuery(Query query) {
        queries.add(query);
    }
    
    public int size() {
        return queries.size();
    }
    
    public String toString() {
        return "queries: " + queries;
    }
    
    public String inspect() {
        return ListUtil.inspect(queries, "query");
    }

    public Query getRandomQuery() {
        // This is sorted so lower scores are processed first,
        // thus being more likely to be "matched" earlier. 
        TreeMap<Integer, ArrayList<Query>> byScore = getByScore();
        for (Integer score : byScore.keySet()) {
            int rnd = random.nextInt(Results.MAX_SCORE);
            if (rnd > score) {
                ArrayList<Query> forScore = byScore.get(score);
                return ListUtil.getRandomElement(forScore);
            }
        }
        return null;
    }
    
    public int indexOf(Query query) {
        return queries.indexOf(query);
    }

    private TreeMap<Integer, ArrayList<Query>> getByScore() {
        TreeMap<Integer, ArrayList<Query>> queriesByScore = new TreeMap<Integer, ArrayList<Query>>();
        for (Query query : queries) {
            int score = query.getScore();
            MapUtil.putMultiMap(queriesByScore, score, query);
        }
        return queriesByScore;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeList(queries);
    }
}
