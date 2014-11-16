package pl.krzysh.androiddevicepairing.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.model.PermissionLevel;
import pl.krzysh.androiddevicepairing.service.PermissionManager;

public class TestPermissionManager {
	PermissionManager permissionManager;

	@Before
	public void setUp() {
		permissionManager = new PermissionManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testSetLevels() {
		Device device = new Device("device");
		assertNull(device.getPermissionLevel());
		permissionManager.setPermissionLevel(device, PermissionManager.PermissionLevels.admin);
		assertEquals(device.getPermissionLevel(), PermissionManager.PermissionLevels.admin);
	}
	
	@Test
	public void testHasPermission() {
		PermissionLevel testPermissions = new PermissionLevel("test");
		testPermissions.addPermission("testing.test1");
		Device device = new Device("device");
		permissionManager.setPermissionLevel(device, testPermissions);
		assertTrue(permissionManager.hasPermission(device, "testing.test1"));
		assertFalse(permissionManager.hasPermission(device, "testing.test2"));
	}
}
