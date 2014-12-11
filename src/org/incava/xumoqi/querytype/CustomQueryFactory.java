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

package org.incava.xumoqi.querytype;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.incava.xumoqi.R;

import android.content.res.Resources;

public class CustomQueryFactory {
    private final Map<String, Integer> typeToResource;

    public CustomQueryFactory() {
        typeToResource = new TreeMap<String, Integer>();
        typeToResource.put("BA_", R.raw.ba_);
        typeToResource.put("AB_", R.raw.ab_);
        typeToResource.put("KA_", R.raw.ka_);
        typeToResource.put("_AE", R.raw._ae);
    }

    public List<String> getTypes() {
        return new ArrayList<String>(typeToResource.keySet());
    }
    
    public Integer getResource(String type) {
        return typeToResource.get(type);
    }
    
    public QueryType createQueryType(Resources resources, String type) {
        Integer resource = getResource(type);
        return resource == null ? null : new CustomQuery(resources, resource);
    }
}
