package pl.krzysh.androiddevicepairing.service;

import java.util.HashSet;
import java.util.Set;

import pl.krzysh.androiddevicepairing.model.Device;

public class DeviceManager {
	private Set<Device> devices = new HashSet<Device>();

	public Set<Device> getDevices() {
		return devices;
	}
	
	public Device getDeviceById(String id) {
		for(Device device : devices) {
			if(device.getId().equals(id)) {
				return device;
			}
		}
		return null;
	}
	
	public boolean isDeviceRegistered(String id) {
		return getDeviceById(id) != null;
	}
	
	public void addDevice(Device device) {
		if(isDeviceRegistered(device.getId())) return;
		devices.add(device);
	}
	
	public void removeDevice(Device device) {
		devices.remove(device);
	}
	
	public int getDevicesCount() {
		return devices.size();
	}
}
