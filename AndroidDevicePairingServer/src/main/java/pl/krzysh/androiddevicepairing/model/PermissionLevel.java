package pl.krzysh.androiddevicepairing.model;

import java.util.ArrayList;
import java.util.List;

public class PermissionLevel {
	private String name;
	private List<String> permissions = new ArrayList<String>();
	
	public PermissionLevel(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
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
