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

package org.incava.xumoqi.query;

import java.util.ArrayList;
import java.util.List;

import org.incava.xumoqi.lang.StringUtil;
import org.incava.xumoqi.words.Word;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable {
    public static final Parcelable.Creator<Response> CREATOR = new Parcelable.Creator<Response>() {
        public Response createFromParcel(Parcel parcel) {
            return new Response(parcel);
        }
        
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    private final List<String> strs;

    public Response(Word queryWord, String str) {
        strs = StringUtil.split(str.trim(), "[\\s,]+");
        updateWithFullWords(queryWord);
    }
    
    protected Response(Parcel parcel) {
        strs = new ArrayList<String>();
        parcel.readStringList(strs);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
        
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeStringList(strs);
    }

    public boolean contains(String str) {
        return strs.contains(str);    
    }
    
    public List<String> getAll() {
        return strs;
    }
    
    public String toString() {
        return strs.toString();
    }
    
    private void updateWithFullWords(Word queryWord) {
        for (int idx = 0; idx < strs.size(); ++idx) {
            updateWithFullWord(queryWord, idx);
        }
    }

    private void updateWithFullWord(Word queryWord, int idx) {
        String s = strs.get(idx);
        if (s.length() == 1) {
            char ch = s.charAt(0);
            String t = queryWord.sub(ch);
            strs.set(idx, t);
        }
    }
}
