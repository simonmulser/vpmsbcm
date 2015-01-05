package com.vpmsbcm.exporter;

import static org.junit.Assert.assertEquals;
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

import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.Parcel;
import com.vpmsbcm.common.model.Rocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.Wood;
import com.vpmsbcm.common.model.order.Order;

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
	public void setUp() throws InterruptedException {
		warehouseSpace.clear(null);

		// sleep shot time amount to be sure that space is empty
		Thread.sleep(100);

		for (int i = 0; i < 4; i++) {
			Rocket rocket = new Rocket(null, null, null, 0, null, 0);
			rocket.setState(State.CLASS_A);
			warehouseSpace.write(rocket);
		}

		for (int i = 0; i < 4; i++) {
			Rocket rocket = new Rocket(null, null, null, 0, null, 0);
			rocket.setState(State.CLASS_B);
			warehouseSpace.write(rocket);
		}

		warehouseSpace.write(new Wood("DHL 1"));

		Order order1 = new Order("ID1", 2, 2, 0, 1, "testSpace1");
		order1.getRockets().add(new OrderRocket(null, null, null, 0, null, 0, "ID1"));
		Order order2 = new Order("2", 2, 2, 0, 1, "testSpace2");

		warehouseSpace.write(order1);
		warehouseSpace.write(order2);
	}

	@Test
	public void testCreateParcelClassA() {
		assertNull(warehouseSpace.take(new Parcel(), 500));

		Rocket rocket = new Rocket(null, null, null, 0, null, 0);
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		Parcel parcel = warehouseSpace.take(new Parcel(), 500);
		assertNotNull(parcel);
		for (Rocket exportedRocket : parcel.getRockets()) {
			assertEquals(State.CLASS_A, exportedRocket.getState());
		}
	}

	@Test
	public void testReceiveLastOrderRocket() {
		OrderRocket rocket = new OrderRocket(null, null, null, 0, null, 0, "ID1");
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		assertNull(warehouseSpace.take(new Parcel(), 1500));
	}

	@Test
	public void testCreateParcelClassB() {
		assertNull(warehouseSpace.take(new Parcel(), 500));

		Rocket rocket = new Rocket(null, null, null, 0, null, 0);
		rocket.setState(State.CLASS_B);
		warehouseSpace.write(rocket);

		Parcel parcel = warehouseSpace.take(new Parcel(), 500);
		assertNotNull(parcel);
		for (Rocket exportedRocket : parcel.getRockets()) {
			assertEquals(State.CLASS_B, exportedRocket.getState());
		}
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
