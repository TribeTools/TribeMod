package org.frantictools.franticmod.util;

import com.google.gson.Gson;

public class JSON {

	    public static String Encode(Object AObject) {
	        Gson gson = new Gson();
	        String json = gson.toJson(AObject);
	        return json;
	    }

	    public static Object Decode(String AString) {
	        Gson gson = new Gson();
	        Object obj = gson.fromJson(AString, Object.class);
	        return obj;
	    }
}
