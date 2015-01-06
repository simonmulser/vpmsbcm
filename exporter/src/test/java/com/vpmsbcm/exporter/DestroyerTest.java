package com.vpmsbcm.exporter;

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

import com.vpmsbcm.common.model.NormalRocket;
import com.vpmsbcm.common.model.State;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("test-context.xml")
public class DestroyerTest {

	@GigaSpaceContext(name = "warehouseSpace")
	private GigaSpace warehouseSpace;

	@GigaSpaceContext(name = "trashSpace")
	private GigaSpace trashSpace;

	@After
	public void teardown() {
		warehouseSpace.clear(null);
	}

	@Before
	public void setUp() {
		warehouseSpace.clear(null);
	}

	@Test
	public void testDefectRocket() {
		NormalRocket rocket = new NormalRocket(null, null, null, 45, null, 45);
		rocket.setState(State.DEFECT);
		warehouseSpace.write(rocket);

		rocket = new NormalRocket();
		assertNotNull(trashSpace.take(new NormalRocket(), 100));
		assertNull(warehouseSpace.take(rocket, 100));
	}
}
