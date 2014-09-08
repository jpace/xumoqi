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

import org.incava.xumoqi.gui.ParcelUtil;
import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.query.QueryList;
import org.incava.xumoqi.util.*;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Current iteration and history of games (queries) executed.
 * Game:
 *     gameType:
 *         type (e.g., random blanks)
 *         word length
 *     queries: [
 *         query pattern: {
 *             matching
 *             iterations: [
 *                 response
 *                 score (response v. matching)
 *             ]
 *         }
 *         history: [ query patterns ]
 *     ]
 * 
 * @author me
 */
public class Game implements Parcelable, Inspectable {
    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        public Game createFromParcel(Parcel parcel) {
            return new Game(parcel);
        }
        
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    private final GameType gameType;
    private final QueryList queries;
    private final List<Integer> queryIndices;
    
    public Game(GameType gameType) {
        this.gameType = gameType;
        this.queries = new QueryList();
        this.queryIndices = new ArrayList<Integer>();
    }

    private Game(Parcel parcel) {
        this.gameType = parcel.readParcelable(GameType.class.getClassLoader());
        this.queries = parcel.readParcelable(QueryList.class.getClassLoader());
        this.queryIndices = ParcelUtil.readIntegerList(parcel);
    }

    public Query getCurrentQuery() {
        int queryIndex = ListUtil.get(queryIndices, -1);
        return queries.getQuery(queryIndex);
    }

    public Query getNextQuery(Resources resources) {
        Query randomQuery = getRandomQuery();
        
        if (randomQuery == null) {
            return getNewQuery(resources);
        }
        else {
            addQueryIndex(randomQuery);
            return randomQuery;
        }
    }
    
    public int getLength() {
        return gameType.getWordLength();
    }
    
    public String toString() {
        return "gameType: " + gameType;
    }
    
    public String inspect() {
        return "gameType: " + gameType + "\n\t" + queries.inspect();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(gameType, flags);
        parcel.writeParcelable(queries, flags);
        ParcelUtil.writeIntegerList(parcel, queryIndices);
    }
    
    private void addQueryIndex(Query query) {
        int queryIndex = queries.indexOf(query);
        queryIndices.add(queryIndex);
    }
    
    private Query getNewQuery(Resources resources) {
        Query newQuery = gameType.createQuery(resources);
        queries.addQuery(newQuery);
        addQueryIndex(newQuery);
        return newQuery;
    }

    private Query getRandomQuery() {
        Query randomQuery = queries.getRandomQuery();
        int qIdx = queries.indexOf(randomQuery);
        List<Integer> lastThree = ListUtil.getEndOfList(queryIndices, 3);
        return lastThree.contains(qIdx) ? null : randomQuery;
    }
}
