package pl.krzysh.androiddevicepairing.model;

public class Device {
	private String id;
	private int accessLevel;
	
	public Device(String id)
	{
		this.id = id;
		accessLevel = 0;
	}
	

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
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