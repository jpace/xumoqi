package org.incava.xumoqi;

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
