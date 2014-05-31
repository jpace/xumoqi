package org.incava.xumoqi.utils;

public class Lo {
    public static void g(String component, String msg, Object obj) {
    	android.util.Log.i(component, msg + ": " + obj);
    }
    
    public static void g(String component, String msg, Inspectable insp) {
    	android.util.Log.i(component, msg + ": " + insp.inspect());
    }
    
    public static void g(String component, String msg, String str) {
    	android.util.Log.i(component, msg + ": '" + str + "'");
    }
    
    public static void g(Class<?> cls, String msg, Object obj) {
    	g(cls.getSimpleName(), msg, obj);
    }
    
    public static void g(Class<?> cls, String msg, Inspectable insp) {
    	g(cls.getSimpleName(), msg, insp.inspect());
    }
    
    public static void g(Class<?> cls, String msg, String str) {
    	g(cls.getSimpleName(), msg, str);
    }
    
    public static void g(Object whence, String msg, Object obj) {
    	g(whence.getClass().getSimpleName(), msg, obj);
    }
    
    public static void g(Object whence, String msg, Inspectable insp) {
    	g(whence.getClass().getSimpleName(), msg, insp.inspect());
    }
    
    public static void g(Object whence, String msg, String str) {
    	g(whence.getClass().getSimpleName(), msg, str);
    }
}
