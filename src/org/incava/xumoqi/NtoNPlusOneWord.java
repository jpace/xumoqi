package org.incava.xumoqi;

import android.os.Parcel;
import android.os.Parcelable;

public class NtoNPlusOneWord extends Word {
	public static final Parcelable.Creator<NtoNPlusOneWord> CREATOR = new Parcelable.Creator<NtoNPlusOneWord>() {
		public NtoNPlusOneWord createFromParcel(Parcel parcel) {
			return new NtoNPlusOneWord(parcel);
		}
		
		public NtoNPlusOneWord[] newArray(int size) {
			return new NtoNPlusOneWord[size];
		}
	};
	
	public NtoNPlusOneWord(String str) {
		super(str, -1);
	}
	
	private NtoNPlusOneWord(Parcel parcel) {
		super(parcel);
	}

	@Override
	public String asQuery() {
		String str = toString();
		return "_" + str + ", " + str + "_";  
	}
}
