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

import com.j_spaces.core.client.SQLQuery;
import com.vpmsbcm.common.model.OrderRocket;
import com.vpmsbcm.common.model.State;
import com.vpmsbcm.common.model.order.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-context.xml")
public class OrderExporterTest {

	@GigaSpaceContext(name = "warehouseSpace")
	GigaSpace warehouseSpace;

	@GigaSpaceContext(name = "trashSpace")
	GigaSpace trashSpace;

	@GigaSpaceContext(name = "testSpace1")
	GigaSpace testSpace1;

	@After
	public void teardown() {
		warehouseSpace.clear(null);
	}

	@Before
	public void setUp() throws InterruptedException {
		warehouseSpace.clear(null);

		Order order1 = new Order("ID1", 2, 2, 0, 1, "testSpace1");
		order1.getRockets().add(new OrderRocket(null, null, null, 0, null, 0, "ID1"));
		order1.decrementMissingRockets();

		Order order2 = new Order("ID2", 2, 2, 0, 1, "testSpace2");

		warehouseSpace.write(order1);
		warehouseSpace.write(order2);
	}

	@Test
	public void testReceiveRocketForOrder() {
		OrderRocket rocket = new OrderRocket(null, null, null, 0, null, 45, "ID2");
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		Order order = warehouseSpace.take(new SQLQuery<Order>(Order.class, "id = 'ID2' AND missing = 1"), 500);

		assertNotNull(order);
		assertEquals(1, order.getRockets().size());
		assertEquals(45, (int) order.getRockets().get(0).getProducerID());
	}

	@Test
	public void testReceiveLastRocketForOrder() {
		OrderRocket rocket = new OrderRocket(null, null, null, 0, null, 40, "ID1");
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		Order order = warehouseSpace.take(new SQLQuery<Order>(Order.class, "id = 'ID1' AND state = 'DELIVERED'"), 500);
		assertNotNull(order);
		assertEquals(2, order.getRockets().size());

		rocket = warehouseSpace.take(new OrderRocket(), 500);
		assertNull(rocket);

		order = testSpace1.take(new Order(), 500);
		assertNotNull(order);
		assertEquals(2, order.getRockets().size());
	}

	@Test
	public void testReceiveLastRocketForOrderSpaceNotPresent() {
		OrderRocket rocket = new OrderRocket(null, null, null, 0, null, 40, "ID2");
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);
		rocket = new OrderRocket(null, null, null, 0, null, 40, "ID2");
		rocket.setState(State.CLASS_A);
		warehouseSpace.write(rocket);

		Order order = warehouseSpace.take(new SQLQuery<Order>(Order.class, "id = 'ID2' AND state = 'FHINISHED'"), 500);
		assertNotNull(order);
		assertEquals(2, order.getRockets().size());
	}
}
