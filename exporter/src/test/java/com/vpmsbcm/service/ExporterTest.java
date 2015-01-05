package com.vpmsbcm.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.Wood;

/**
 * Integration test for the Processor. Uses similar xml definition file
 * (ProcessorIntegrationTest-context.xml) to the actual pu.xml. Writs an
 * unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ExporterTest {

	@GigaSpaceContext(name = "warehouseSpace")
	GigaSpace warehouseSpace;

	@GigaSpaceContext(name = "trashSpace")
	GigaSpace trashSpace;

	@After
	public void teardown() {
		warehouseSpace.clear(null);
	}

	@Before
	public void setUp() {
		warehouseSpace.clear(null);

		for (int i = 0; i < 4; i++) {
			Rocket rocket = new Rocket(null, null, null, 0, null, 0);
			rocket.setState(State.CLASS_A);
			warehouseSpace.write(rocket);
		}

		warehouseSpace.write(new Wood("DHL 1"));
	}

	@Test
	public void testCreateParcel() {
		assertNull(warehouseSpace.take(new Parcel(), 500));

		Rocket rocket = new Rocket(null, null, null, 0, null, 0);
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		assertNotNull(warehouseSpace.take(new Parcel(), 500));
	}

	@Test
	public void testNotEnoughParcel() {
		assertNull(warehouseSpace.take(new Parcel(), 500));

		Rocket rocket = new Rocket(null, null, null, 0, null, 0);
		rocket.setState(State.DEFECT);
		warehouseSpace.write(rocket);

		assertNull(warehouseSpace.take(new Parcel(), 500));
	}
}
