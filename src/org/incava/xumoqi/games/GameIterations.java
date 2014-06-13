package org.incava.xumoqi.games;

import android.os.Parcel;
import android.os.Parcelable;

public class GameIterations implements Parcelable {
    public static final Parcelable.Creator<GameIterations> CREATOR = new Parcelable.Creator<GameIterations>() {
        public GameIterations createFromParcel(Parcel parcel) {
            return new GameIterations(parcel);
        }
        
        public GameIterations[] newArray(int size) {
            return new GameIterations[size];
        }
    };

	public GameIterations() {
	}

    private GameIterations(Parcel parcel) {
        this();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
    }
}
