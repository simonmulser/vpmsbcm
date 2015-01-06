package com.vpmsbcm.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.context.GigaSpaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vpmsbcm.common.model.order.Order;
import com.vpmsbcm.common.model.order.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-context.xml")
public class ServiceTest {

	@GigaSpaceContext(name = "warehouseSpace")
	GigaSpace warehouseSpace;

	@Autowired
	Service service;

	@After
	public void teardown() {
		warehouseSpace.clear(null);
	}

	@Before
	public void setUp() throws InterruptedException {
		warehouseSpace.clear(null);

		Order order1 = new Order("test:1", 2, 2, 0, 1, "testSpace1");
		order1.setState(State.FHINISHED);
		Order order2 = new Order("test:2", 2, 2, 0, 1, "testSpace1");
		order2.setState(State.FHINISHED);
		Order order3 = new Order("otherId:1", 2, 2, 0, 1, "testSpace1");
		order3.setState(State.FHINISHED);
		Order order4 = new Order("test:3", 2, 2, 0, 1, "testSpace1");
		order4.setState(State.DELIVERED);

		warehouseSpace.write(order1);
		warehouseSpace.write(order2);
		warehouseSpace.write(order3);
		warehouseSpace.write(order4);
	}

	@Test
	public void testReceiveRocketForOrder() {
		service.init("test");

		Order order = new Order();
		order.setId("test:1");
		order = service.getSpace().read(order, 200);
		assertNotNull(order);
		assertEquals(State.DELIVERED, order.getState());

		order = new Order();
		order.setId("test:2");
		order = service.getSpace().read(order, 200);
		assertNotNull(order);
		assertEquals(State.DELIVERED, order.getState());

		int count = service.getSpace().count(new Order());
		assertEquals(2, count);
	}
}
