package pl.krzysh.androiddevicepairing.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.service.DeviceManager;

public class TestDeviceManager {
	DeviceManager deviceManager;

	@Before
	public void setUp() {
		deviceManager = new DeviceManager();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testAddGetRemove() {
		assertEquals(deviceManager.getDevicesCount(), 0);
		assertNull(deviceManager.getDeviceById("testDevice"));
		assertFalse(deviceManager.isDeviceRegistered("testDevice"));
		
		Device testDevice = new Device("testDevice");
		deviceManager.addDevice(testDevice);
		assertEquals(deviceManager.getDevicesCount(), 1);
		assertEquals(deviceManager.getDeviceById("testDevice"), testDevice);
		assertTrue(deviceManager.isDeviceRegistered("testDevice"));
		
		deviceManager.removeDevice(testDevice);
		assertEquals(deviceManager.getDevicesCount(), 0);
		assertNull(deviceManager.getDeviceById("testDevice"));
		assertFalse(deviceManager.isDeviceRegistered("testDevice"));
	}
	
	@Test
	public void testDeviceCompare() {
		Device dev1 = new Device("test");
		Device dev2 = new Device("test");
		assertTrue(dev1.equals(dev2));
	}
	
	@Test
	public void testMultipleAdd() {
		assertEquals(deviceManager.getDevicesCount(), 0);
		Device dev1 = new Device("test");
		deviceManager.addDevice(dev1);
		assertEquals(deviceManager.getDevicesCount(), 1);
		Device dev2 = new Device("test");
		deviceManager.addDevice(dev2);
		assertEquals(deviceManager.getDevicesCount(), 1);
		
		deviceManager.removeDevice(dev1);
		assertEquals(deviceManager.getDevicesCount(), 0);
		deviceManager.removeDevice(dev2);
		assertEquals(deviceManager.getDevicesCount(), 0);
	}
}
