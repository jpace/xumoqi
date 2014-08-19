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

import org.incava.xumoqi.utils.ListUtil;
import org.incava.xumoqi.words.Word;

import android.os.Parcel;
import android.os.Parcelable;

public class Query implements Parcelable {
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

    public Query(Word word) {
        this.word = word;
        this.results = new ArrayList<Results>();
    }

    public Query(Word word, Results results) {
        this(word);
        this.results.add(results);
    }
    
    protected Query(Parcel parcel) {
        this.word = parcel.readParcelable(Word.class.getClassLoader());
        this.results = new ArrayList<Results>();
        parcel.readList(this.results, Results.class.getClassLoader());
    }
    
    public Word getWord() {
        return word;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void addResults(Results res) {
        this.results.add(res);
        while (this.results.size() >= MAX_RESULTS) {
            this.results.remove(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(word, flags);
        parcel.writeList(results);
    }
    
    public String toString() {
        return "word: " + word + "; results: " + results;
    }
    
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("word: ").append(word).append('\n');
        for (Results r : results) {
            sb.append(r.inspect());
        }
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
}
