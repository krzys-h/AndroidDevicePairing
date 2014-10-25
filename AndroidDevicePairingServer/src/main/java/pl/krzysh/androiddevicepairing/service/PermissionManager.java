package pl.krzysh.androiddevicepairing.service;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.model.PermissionLevel;

public class PermissionManager {
	public static class PermissionLevels {
		public static final PermissionLevel admin = new PermissionLevel();
		{
			admin.addPermission("device.admin");
			admin.addPermission("plan.read");
			admin.addPermission("plan.edit");
		}
		
		public static final PermissionLevel editor = new PermissionLevel();
		{
			editor.addPermission("plan.read");
			editor.addPermission("plan.edit");
		}
		
		public static final PermissionLevel user = new PermissionLevel();
		{
			user.addPermission("plan.read");
		}
	}
	
	public void setPermissionLevel(Device device, PermissionLevel level)
	{
		device.setPermissionLevel(level);
	}
	
	public boolean hasPermission(Device device, String permission)
	{
		return device.getPermissionLevel().hasPermission(permission);
	}
}
