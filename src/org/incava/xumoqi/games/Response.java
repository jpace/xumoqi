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
import java.util.Arrays;
import java.util.List;

import org.incava.xumoqi.utils.Util;
import org.incava.xumoqi.words.Word;

public class Response {
	private final List<String> strs;

	public Response(Word queryWord, String str) {
		strs = str.isEmpty() ? new ArrayList<String>() : Arrays.asList(str.split("[\\s,]+"));
		Util.log("RESPONSE", "strs", this.strs);
		for (int idx = 0; idx < strs.size(); ++idx) {
			String s = strs.get(idx);
			Util.log("RESPONSE", "response[" + idx + "]", s);
			if (s.length() < queryWord.toString().length()) {
				String t = queryWord.sub(s.charAt(0));
				Util.log("RESPONSE", "t", t);
				strs.set(idx, t);
			}
		}
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
}
