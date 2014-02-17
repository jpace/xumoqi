package org.incava.xumoqi;

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
	
	private Word(Parcel parcel) {
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
	
	private String sub(char ch) {
		if (dotIdx == NO_INDEX) {
			return str;
		}
		else {
			return str.substring(0, dotIdx) + ch + str.substring(dotIdx + 1, str.length());
		}
	}
}	
