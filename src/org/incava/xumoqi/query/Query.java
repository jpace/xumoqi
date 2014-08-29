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
import java.util.List;
import java.util.Random;

import org.incava.xumoqi.querytype.QueryType;
import org.incava.xumoqi.util.*;
import org.incava.xumoqi.words.Word;

import android.os.Parcel;
import android.os.Parcelable;

public class Query implements Parcelable, Inspectable {
    public static final Parcelable.Creator<Query> CREATOR = new Parcelable.Creator<Query>() {
        public Query createFromParcel(Parcel parcel) {
            return new Query(parcel);
        }
        
        public Query[] newArray(int size) {
            return new Query[size];
        }
    };

    public final static int MAX_RESULTS = 5;
    private final static Random random = new Random();

    private final Word word;
    private final ArrayList<Results> results;
    private ArrayList<String> matching = null;

    public Query(final QueryType game) {
        this.word = game.getQueryWord();
        this.results = new ArrayList<Results>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer("Query", "init");
                Query.this.matching = game.getMatching(Query.this.word);
                timer.done();
                Lo.g("matching", matching);
            }
        });
        thread.start();
    }

    protected Query(Parcel parcel) {
        this.word = parcel.readParcelable(Word.class.getClassLoader());
        this.results = new ArrayList<Results>();
        parcel.readList(this.results, Results.class.getClassLoader());
        this.matching = new ArrayList<String>();
        parcel.readStringList(matching);
    }
    
    public ArrayList<String> getMatching() {
        // wait until game.getMatching(...) is done:
        while (matching == null) {
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
            }
        }
        return matching;
    }
    
    public String getQueryString() {
        return word.asQuery();
    }

    public Results addResults(String inputText) {
        Response response = new Response(word, inputText);
        ArrayList<String> matching = getMatching();
        Lo.g("matching", matching);
        Results results = new Results(matching, response.getAll());
        addResults(results);
        return results;
    }

    private void addResults(Results res) {
        this.results.add(res);
        while (this.results.size() >= MAX_RESULTS) {
            this.results.remove(0);
        }
    }
    
    public String toString() {
        return "word: " + word + "; results: " + results;
    }
    
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("word: ").append(word).append('\n');
        sb.append(ListUtil.inspect(results, "results"));
        sb.append(ListUtil.inspect(matching, "matching"));
        return sb.toString();
    }
    
    public ArrayList<Integer> getScores() {
        ArrayList<Integer> scores = new ArrayList<Integer>();
        for (Results r : results) {
            scores.add(r.getScore());
        }
        return scores;
    }
    
    public int getScore() {
        final int maxRecent = 3;
        ArrayList<Integer> scores = getScores();
        List<Integer> recent = ListUtil.getEndOfList(scores, maxRecent);
        return recent.get(random.nextInt(recent.size()));
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(word, flags);
        parcel.writeList(results);
        // save it after it's been read:
        parcel.writeStringList(getMatching());
    }
}
