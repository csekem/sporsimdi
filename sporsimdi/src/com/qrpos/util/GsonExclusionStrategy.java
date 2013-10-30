package com.qrpos.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class GsonExclusionStrategy implements ExclusionStrategy {

	public boolean shouldSkipField(FieldAttributes fa) {
		return fa.getAnnotation(GsonIgnore.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> type) {
		// never skips any class
		return false;
	}

}