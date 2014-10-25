package pl.krzysh.androiddevicepairing.model;

import pl.krzysh.androiddevicepairing.service.PermissionManager;

public class Device {
	private String id;
	private PermissionLevel permissionLevel;
	
	public Device(String id)
	{
		this.id = id;
		permissionLevel = PermissionManager.PermissionLevels.user;
	}
	

	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(PermissionLevel level) {
		permissionLevel = level;
	}

	public String getId() {
		return id;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (!id.equals(other.id))
			return false;
		return true;
	}
}
