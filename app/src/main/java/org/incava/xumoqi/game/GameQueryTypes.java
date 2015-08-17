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

import java.util.ArrayList;
import java.util.List;

import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.querytype.QueryType;
import org.incava.xumoqi.querytype.QueryTypeFactory;
import org.incava.xumoqi.util.ListUtil;
import org.incava.xumoqi.util.Lo;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

public class GameQueryTypes implements Parcelable {
    private final List<String> queryTypes;
    
    public static final Parcelable.Creator<GameQueryTypes> CREATOR = new Parcelable.Creator<GameQueryTypes>() {
        public GameQueryTypes createFromParcel(Parcel parcel) {
            return new GameQueryTypes(parcel);
        }
        
        public GameQueryTypes[] newArray(int size) {
            return new GameQueryTypes[size];
        }
    };

    public GameQueryTypes(String gameType) {
        this.queryTypes = new ArrayList<String>();
        queryTypes.add(gameType);
    }

    public GameQueryTypes(List<String> queryTypes) {
        this.queryTypes = queryTypes;
        Lo.g("queryTypes", queryTypes);
    }

    private GameQueryTypes(Parcel parcel) {
        this.queryTypes = new ArrayList<String>();
        parcel.readStringList(queryTypes);
    }
    
    public Query createQuery(Resources resources, int wordLength) {
        Lo.g("queryTypes", queryTypes);
        String queryTypeStr = ListUtil.getRandomElement(queryTypes);
        Lo.g("queryTypeStr", queryTypeStr);
        QueryType queryType = QueryTypeFactory.createQueryType(resources, queryTypeStr, wordLength);
        return new Query(queryType);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringList(queryTypes);
    }
    
    public String toString() {
        return queryTypes.toString();
    }
}
