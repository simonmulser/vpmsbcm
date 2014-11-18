package com.vpmsbcm.common.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Rocket extends Good {

	private static AtomicInteger ID = new AtomicInteger(0);

	public Rocket() {
		this.id = ID.incrementAndGet();
	}
}
