package com.vpmsbcm.common.util;

public class Util {

	public static String splitId(String id) {
		id = id.replace("^", "-");
		String[] splittedId = id.split("-");
		return splittedId[splittedId.length - 1];
	}

}
