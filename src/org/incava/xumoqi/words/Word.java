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

package org.incava.xumoqi.words;

import org.incava.xumoqi.util.StringUtil;
import org.incava.xumoqi.util.Util;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        public Word createFromParcel(Parcel parcel) {
            return new Word(parcel);
        }
        
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public static final int NO_INDEX = -1;
    
    private final String str;
    private final String asQuery;
    private final String asPattern;
    private final int dotIdx;

    public Word(String str, int dotIdx) {
        this.str = str;
        this.dotIdx = dotIdx;
        this.asQuery = sub('_');
        this.asPattern = sub('.');
    }
    
    public Word(String str, int dotIdx, String asQuery, String asPattern) {
        this.str = str;
        this.dotIdx = dotIdx;
        this.asQuery = asQuery;
        this.asPattern = asPattern;
    }
    
    protected Word(Parcel parcel) {
        this(parcel.readString(), parcel.readInt(), parcel.readString(), parcel.readString());
    }
    
    public int length() {
        return str.length();
    }

    public String toString() {
        return str;
    }
    
    public char getChar(int index) {
        return StringUtil.charAt(str, index);
    }
    
    public boolean startsWithBlank() {
        return dotIdx == 0;
    }
    
    public boolean equals(Object obj) {
        return obj instanceof Word && equals((Word)obj);
    }
    
    public boolean equals(Word other) {
        return other.str.equals(this.str);
    }
    
    public String asQuery() {
        return asQuery;
    }
    
    public String asPattern() {
        return asPattern;
    }
    
    public final String sub(char ch) {
        return dotIdx == NO_INDEX ? str : Util.replaceAt(str, dotIdx, ch);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(str);
        parcel.writeInt(dotIdx);
        parcel.writeString(asQuery);
        parcel.writeString(asPattern);
    }
}   
