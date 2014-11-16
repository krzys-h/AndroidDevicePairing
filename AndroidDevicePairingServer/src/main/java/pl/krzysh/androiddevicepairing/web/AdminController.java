package pl.krzysh.androiddevicepairing.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.service.DeviceManager;
import pl.krzysh.androiddevicepairing.service.PermissionManager;

@Controller
public class AdminController {
	@Autowired
	public DeviceManager deviceManager;
	@Autowired
	public PermissionManager permissionManager;

	// Main admin page
	@RequestMapping("/admin")
	public ModelAndView handleIndex() throws ServletException, IOException {
		return new ModelAndView("admin", "page", "index");
	}

	// Devices page
	@RequestMapping("/admin/devices")
	public ModelAndView handleDevices(@RequestParam(required = false) String message) throws ServletException, IOException {
		ModelAndView view = new ModelAndView("admin", "page", "devices");
		view.addObject("devices", deviceManager.getDevices());
		view.addObject("message", message);
		return view;
	}

	@RequestMapping("/admin/devices/remove")
	public String handleDeviceRemove(@RequestParam String deviceid) {
		Device device = deviceManager.getDeviceById(deviceid);
		if(device == null)
			throw new RuntimeException("No such device!"); //TODO: Error page?
		deviceManager.removeDevice(device);
		return "redirect:/admin/devices?message=removed";
	}
	
	// Devices - permissions page
	@RequestMapping(value = "/admin/devices/permissions", method = RequestMethod.GET)
	public ModelAndView handleDevicePermissions(@RequestParam String deviceid) {
		ModelAndView view = new ModelAndView("admin", "page", "device_permissions");
		
		Device device = deviceManager.getDeviceById(deviceid);
		if(device == null)
			throw new RuntimeException("No such device!"); //TODO: Error page?
		view.addObject("device", device);
		
		view.addObject("permissions", device.getPermissionLevel());
		
		return view;
	}

	@RequestMapping(value = "/admin/devices/permissions", method = RequestMethod.POST)
	public String handleDevicePermissionsSave(@RequestParam String deviceid, @RequestParam String permissionLevel) {
		Device device = deviceManager.getDeviceById(deviceid);
		if(device == null)
			throw new RuntimeException("No such device!"); //TODO: Error page?
		
		permissionManager.setPermissionLevel(device, PermissionManager.PermissionLevels.fromString(permissionLevel));
		
		return "redirect:/admin/devices?message=permissions_updated";
	}
}
