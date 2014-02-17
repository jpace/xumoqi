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

	private final String str;

	public Word(String str) {
		this.str = str;
	}
	
	private Word(Parcel parcel) {
		this(parcel.readString());
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(str);
	}

	public String toString() {
		return str;
	}
	
	public boolean equals(Object obj) {
		return obj instanceof Word && ((Word)obj).str.equals(this.str);
	}
	
	public String asQuery() {
		return str.replace('.', '_');
	}
	
	public String asPattern() {
		return str;
	}
}
