package pl.krzysh.androiddevicepairing.model;

public class Device {
	private String id;
	private String password;
	private PermissionLevel permissionLevel;
	
	public Device(String id)
	{
		this.id = id;
		this.password = null;
		permissionLevel = null;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(PermissionLevel permissionLevel) {
		this.permissionLevel = permissionLevel;
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
