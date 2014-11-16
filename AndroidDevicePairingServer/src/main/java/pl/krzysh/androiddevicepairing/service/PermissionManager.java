package pl.krzysh.androiddevicepairing.service;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.model.PermissionLevel;

public class PermissionManager {
	public static class PermissionLevels {
		public static final PermissionLevel admin = new PermissionLevel("admin");
		{
			admin.addPermission("device.admin");
			admin.addPermission("plan.read");
			admin.addPermission("plan.edit");
		}
		
		public static final PermissionLevel editor = new PermissionLevel("editor");
		{
			editor.addPermission("plan.read");
			editor.addPermission("plan.edit");
		}
		
		public static final PermissionLevel user = new PermissionLevel("user");
		{
			user.addPermission("plan.read");
		}
		
		public static PermissionLevel fromString(String name)
		{
			switch(name) {
				case "admin": return admin;
				case "editor": return editor;
				case "user": return user;
				default: return null;
			}
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
