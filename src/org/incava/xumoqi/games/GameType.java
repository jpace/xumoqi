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
import java.util.List;

import org.incava.xumoqi.R;
import org.incava.xumoqi.android.ResourceUtil;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.querytype.EndsWithDots;
import org.incava.xumoqi.querytype.NToNPlusOne;
import org.incava.xumoqi.querytype.QNoU;
import org.incava.xumoqi.querytype.QueryType;
import org.incava.xumoqi.querytype.RandomDots;
import org.incava.xumoqi.querytype.StartsWithDots;
import org.incava.xumoqi.words.WordList;
import org.incava.xumoqi.words.WordLists;

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
    private final String gameType;
    
    public GameType(int wordLength, String gameType) {
        this.wordLength = wordLength;
        this.gameType = gameType;
    }

    private GameType(Parcel parcel) {
        this(parcel.readInt(), parcel.readString());
    }
    
    public Query createQuery(Resources resources) {
        QueryType game = createGame(resources);
        return new Query(game);
    }

    private QueryType createGame(Resources resources) {
        if (gameType.contains("Starting")) {
            return new StartsWithDots(getWordList(resources, wordLength), wordLength);
        }
        else if (gameType.contains("Random")) {
            return new RandomDots(getWordList(resources, wordLength), wordLength);
        }
        else if (gameType.contains("Ending")) {
            return new EndsWithDots(getWordList(resources, wordLength), wordLength);
        }
        else if (gameType.contains("to-make")) {
            return new NToNPlusOne(getWordList(resources, wordLength), getWordList(resources, wordLength + 1));
        }
        else if (gameType.equals("Q without U")) {
            List<WordList> wordLists = new ArrayList<WordList>();
            for (int len = 2; len <= wordLength; ++len) {
                wordLists.add(getWordList(resources, len));
            }
            List<String> grepLines = ResourceUtil.getTextResource(resources, R.raw.qnou);
            return new QNoU(grepLines, wordLists, wordLength);
        }
        return null;
    }

    private WordList getWordList(Resources resources, int length) {
        return WordLists.getInstance().getWordList(resources, length);
    }

    public int getWordLength() {
        return wordLength;
    }

    public String getGameType() {
        return gameType;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(wordLength);
        parcel.writeString(gameType);
    }
    
    public String toString() {
        return "wordLength: " + wordLength + "; gameType: " + gameType;
    }
}
