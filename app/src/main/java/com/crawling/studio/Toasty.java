package com.crawling.studio;

import java.util.Collection;

public class Toasty {
	
	public static void success(CharSequence cs) {
		es.dmoral.toasty.Toasty.success(MyApp.mApp, cs, es.dmoral.toasty.Toasty.LENGTH_SHORT, true).show();
	}
	
	public static void info(CharSequence cs) {
		es.dmoral.toasty.Toasty.info(MyApp.mApp, cs, es.dmoral.toasty.Toasty.LENGTH_SHORT, true).show();
	}
	
	//集合是否是空的
	public static boolean isEmptyArray(Collection list) {
		return list == null || list.size() == 0;
	}
	
	public static <T> boolean isEmptyArray(T[] list) {
		return list == null || list.length == 0;
	}
}
