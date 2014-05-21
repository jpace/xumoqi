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

    private final Word word;
    private final Results results;

    public Query(Word word) {
        this(word, null);
    }
    
    public Query(Word word, Results results) {
        this.word = word;
        this.results = results;
    }
    
    protected Query(Parcel parcel) {
        this((Word)parcel.readParcelable(Word.class.getClassLoader()),
             (Results)parcel.readParcelable(Results.class.getClassLoader()));
    }
    
    public Word getWord() {
        return word;
    }

    public Results getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(word, flags);
        parcel.writeParcelable(results, flags);
    }
    
    public String toString() {
        return "word: " + word + "; results: " + results;
    }
}
