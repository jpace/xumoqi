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

  This program includes code from the GPL program:
  http://sourceforge.net/projects/scrabbledict/
*/

package org.incava.xumoqi.game;

import org.incava.xumoqi.query.Query;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

public class GameType implements Parcelable {
    public static final Parcelable.Creator<GameType> CREATOR = new Parcelable.Creator<GameType>() {
        public GameType createFromParcel(Parcel parcel) {
            return new GameType(parcel);
        }
        
        public GameType[] newArray(int size) {
            return new GameType[size];
        }
    };

    private final int wordLength;
    private final GameQueryTypes queryTypes;
    private boolean showNumMatching;
    
    public GameType(int wordLength, String gameType, boolean showNumMatching) {
        this.wordLength = wordLength;
        this.queryTypes = GameQueryTypesFactory.create(gameType);
        this.showNumMatching = showNumMatching;
    }

    private GameType(Parcel parcel) {
        this.wordLength = parcel.readInt();
        this.queryTypes = parcel.readParcelable(GameQueryTypes.class.getClassLoader());
        this.showNumMatching = (Boolean)parcel.readValue(GameQueryTypes.class.getClassLoader());
    }
    
    public Query createQuery(Resources resources) {
        return queryTypes.createQuery(resources, wordLength);
    }

    public boolean isShowNumMatching() {
        return showNumMatching;
    }
    
    public int getWordLength() {
        return wordLength;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(wordLength);
        parcel.writeParcelable(queryTypes, flags);
        parcel.writeValue(showNumMatching);
    }
    
    public String toString() {
        return "wordLength: " + wordLength + "; queryTypes: " + queryTypes + "; showNumMatching: " + showNumMatching;
    }
}
