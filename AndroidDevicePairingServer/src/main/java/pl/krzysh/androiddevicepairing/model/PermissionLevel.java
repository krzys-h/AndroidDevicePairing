package pl.krzysh.androiddevicepairing.model;

import java.util.ArrayList;
import java.util.List;

public class PermissionLevel {
	private List<String> permissions = new ArrayList<String>();
	
	public void addPermission(String permission)
	{
		permissions.add(permission);
	}
	
	public void removePermission(String permission)
	{
		permissions.remove(permission);
	}
	
	public boolean hasPermission(String permission)
	{
		return permissions.contains(permission);
	}
}
