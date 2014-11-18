package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Work extends Good {

	private static AtomicInteger ID = new AtomicInteger(0);

	public Work() {
		this.id = ID.incrementAndGet();
	}

}
