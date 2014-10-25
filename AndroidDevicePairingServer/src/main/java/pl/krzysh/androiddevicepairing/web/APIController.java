package pl.krzysh.androiddevicepairing.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.service.DeviceManager;

@Controller
public class APIController {
	@Autowired
	public DeviceManager deviceManager;
	
	@RequestMapping("/api/devices/register")
	@ResponseBody
	public String handleDeviceRegister(@RequestParam("deviceid") String deviceid) {
		if(!deviceManager.isDeviceRegistered(deviceid)) {
			Device device = new Device(deviceid);
			deviceManager.addDevice(device);
			return "OK"; //TODO: JSON?
		} else {
			return "Already registered!"; //TODO: JSON?
		}
	}
	
	@RequestMapping("/api/devices/login")
	@ResponseBody
	public String handleDeviceLogin(@RequestParam("deviceid") String deviceid) {
		if(deviceManager.isDeviceRegistered(deviceid)) {
			//TODO: Find some good way to verify it's actually that device, and not a hacker (random password stored on the device?)
			return "OK";
		} else {
			return "Please register first!"; //TODO: JSON?
		}
	}
}
