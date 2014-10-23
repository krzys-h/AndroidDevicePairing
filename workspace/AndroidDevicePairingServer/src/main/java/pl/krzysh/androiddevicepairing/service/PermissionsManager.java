package pl.krzysh.androiddevicepairing.service;

import pl.krzysh.androiddevicepairing.model.Device;

public class PermissionsManager {
	public void setAccessLevel(Device device, int level)
	{
		device.setAccessLevel(level);
	}
	
	public boolean hasAccessTo(Device device, int level)
	{
		return device.getAccessLevel() >= level;
	}
}
