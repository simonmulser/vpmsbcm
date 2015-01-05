package com.vpmsbcm.common.model;

import java.util.List;

import com.gigaspaces.annotation.pojo.FifoSupport;
import com.gigaspaces.annotation.pojo.SpaceClass;

@SpaceClass(fifoSupport = FifoSupport.OPERATION)
public class NormalRocket extends Rocket {

	public NormalRocket() {
		super();
	}

	public NormalRocket(Wood wood, Detonator detonator, List<Charge> charges, Integer chargeAmount, List<Load> loades, Integer producerID) {
		super(wood, detonator, charges, chargeAmount, loades, producerID);

	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
