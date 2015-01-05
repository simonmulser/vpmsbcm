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

/**
 * Integration test for the Processor. Uses similar xml definition file
 * (ProcessorIntegrationTest-context.xml) to the actual pu.xml. Writs an
 * unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
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
