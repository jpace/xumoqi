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

import android.os.Parcel;
import android.os.Parcelable;

public class GameParams implements Parcelable {
	public static final Parcelable.Creator<GameParams> CREATOR = new Parcelable.Creator<GameParams>() {
		public GameParams createFromParcel(Parcel parcel) {
			return new GameParams(parcel);
		}
		
		public GameParams[] newArray(int size) {
			return new GameParams[size];
		}
	};
	
	private final int wordLength;
	private final String gameType;
	
	public GameParams(int wordLength, String gameType) {
		this.wordLength = wordLength;
		this.gameType = gameType;
	}

	private GameParams(Parcel parcel) {
		this(parcel.readInt(), parcel.readString());
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
