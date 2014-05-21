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
    private final int dotIdx;

    public Word(String str, int dotIdx) {
        this.str = str;
        this.dotIdx = dotIdx;
    }
    
    public Word(String str) {
        this(str, NO_INDEX);
    }
    
    protected Word(Parcel parcel) {
        this(parcel.readString(), parcel.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(str);
        parcel.writeInt(dotIdx);
    }

    public String toString() {
        return str;
    }
    
    public int getDotIndex() {
        return dotIdx;
    }
    
    public boolean equals(Object obj) {
        return obj instanceof Word && ((Word)obj).str.equals(this.str);
    }
    
    public String asQuery() {
        return sub('_');
    }
    
    public String asPattern() {
        return sub('.');
    }
    
    public String sub(char ch) {
        if (dotIdx == NO_INDEX) {
            return str;
        }
        else {
            return str.substring(0, dotIdx) + ch + str.substring(dotIdx + 1, str.length());
        }
    }
}   
