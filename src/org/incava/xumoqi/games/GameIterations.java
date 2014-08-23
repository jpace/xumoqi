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

import org.incava.xumoqi.query.Query;
import org.incava.xumoqi.query.QueryList;
import org.incava.xumoqi.utils.*;

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
public class GameIterations implements Parcelable, Inspectable {
    public static final Parcelable.Creator<GameIterations> CREATOR = new Parcelable.Creator<GameIterations>() {
        public GameIterations createFromParcel(Parcel parcel) {
            return new GameIterations(parcel);
        }
        
        public GameIterations[] newArray(int size) {
            return new GameIterations[size];
        }
    };

    private final GameType gameType;
    private final QueryList queries;
    private final List<Integer> queryIndices;
    
    public GameIterations(GameType gameType) {
        this.gameType = gameType;
        this.queries = new QueryList();
        this.queryIndices = new ArrayList<Integer>();
    }

    private GameIterations(Parcel parcel) {
        this.gameType = parcel.readParcelable(GameType.class.getClassLoader());
        this.queries = parcel.readParcelable(QueryList.class.getClassLoader());
        int[] ary = parcel.createIntArray();
        this.queryIndices = ListUtil.toIntegerList(ary);
    }

    public Query getCurrentQuery() {
        int queryIndex = ListUtil.get(queryIndices, -1);
        return queries.getQuery(queryIndex);
    }

    public Query getNextQuery(Resources resources) {
        Query randomQuery = getRandomQuery();
        
        if (randomQuery == null) {
            Game game = gameType.createGame(resources);
            Query newQuery = new Query(game);
            addQuery(newQuery);
            return newQuery;
        }
        else {
            int queryIndex = queries.indexOf(randomQuery);
            queryIndices.add(queryIndex);
            return randomQuery;
        }
    }

    private Query getRandomQuery() {
        Query randomQuery = queries.getRandomQuery();
        Lo.g("randomQuery", randomQuery);
        
        int qIdx = queries.indexOf(randomQuery);
        Lo.g("qIdx", qIdx);
        
        Lo.g("queryIndices", queryIndices);
        
        // don't repeat the previous one:
        int prevQueryIndex = ListUtil.get(queryIndices, -1, -1);
        Lo.g("prevQueryIndex", prevQueryIndex);
        
        return prevQueryIndex == qIdx ? null : randomQuery;
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
        int[] ary = ListUtil.toIntArray(queryIndices);
        parcel.writeIntArray(ary);
    }

    private void addQuery(Query query) {
        queries.addQuery(query);
        int queryIndex = queries.size() - 1;
        queryIndices.add(queryIndex);
    }
}
