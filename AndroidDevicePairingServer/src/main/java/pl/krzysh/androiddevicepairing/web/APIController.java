package pl.krzysh.androiddevicepairing.web;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.krzysh.androiddevicepairing.model.Device;
import pl.krzysh.androiddevicepairing.service.DeviceManager;
import pl.krzysh.androiddevicepairing.util.Passwords;

@Controller
public class APIController {
	@Autowired
	public DeviceManager deviceManager;
	
	public class Challenge {
		private Device device;
		private String key;
		private boolean finished;
		private String nextPassword;
		
		public Challenge(Device device, String key) {
			this.device = device;
			this.key = key;
			this.finished = false;
		}
		
		public Device getDevice() {
			return device;
		}
		
		public String getKey() {
			return key;
		}
		
		public boolean isFinished() {
			return finished;
		}
		
		public void finish(String nextPassword) {
			this.finished = true;
			this.nextPassword = nextPassword;
		}
		
		public String getNextPassword() {
			return nextPassword;
		}
	}
	private Map<String, Challenge> challenges = new TreeMap<String, Challenge>();
	
	@RequestMapping("/api/devices/register")
	@ResponseBody
	public String handleDeviceRegister(@RequestParam("deviceid") String deviceid) {
		if(!deviceManager.isDeviceRegistered(deviceid)) {
			String password = Passwords.generatePassword();
			Device device = new Device(deviceid);
			device.setPassword(password);
			deviceManager.addDevice(device);
			return "OK, your password is "+password; //TODO: JSON?
		} else {
			return "Already registered!"; //TODO: JSON?
		}
	}
	
	@RequestMapping("/api/devices/login")
	@ResponseBody
	public String handleDeviceLogin(@RequestParam("deviceid") String deviceid) {
		if(deviceManager.isDeviceRegistered(deviceid)) {
			//TODO: Flood protection
			String challengeid = Passwords.generatePassword();
			String key = Passwords.generatePassword();
			Challenge challenge = new Challenge(deviceManager.getDeviceById(deviceid), key);
			challenges.put(challengeid, challenge);
			return "OK, please respond to /api/devices/loginchallenge with challengeid="+challengeid+", response=SHA256(your_password+"+key+")"; //TODO: JSON?
		} else {
			return "Please register first!"; //TODO: JSON?
		}
	}

	@RequestMapping("/api/devices/loginchallenge")
	@ResponseBody
	public String handleDeviceLoginChallenge(@RequestParam("challengeid") String challengeid, @RequestParam("response") String response) {
		if(challenges.containsKey(challengeid)) {
			Challenge challenge = challenges.get(challengeid);
			if(!challenge.isFinished()) {
				if(Passwords.hashPassword(challenge.getDevice().getPassword()+challenge.getKey()).equals(response)) {
					String nextPassword = Passwords.generatePassword();
					challenge.finish(nextPassword);
					return "OK, your next password is "+nextPassword+", please ACK to /api/devices/loginack?challengeid="+challengeid;
				} else {
					challenges.remove(challengeid);
					return "Bad response";
				}
			} else {
				return "Challenge already finished";
			}
		} else {
			return "No such challenge found";
		}
	}
	
	@RequestMapping("/api/devices/loginack")
	@ResponseBody
	public String handleDeviceLoginAck(@RequestParam("challengeid") String challengeid) {
		if(challenges.containsKey(challengeid)) {
			Challenge challenge = challenges.get(challengeid);
			if(challenge.isFinished()) {
				Device device = challenge.getDevice();
				device.setPassword(challenge.getNextPassword());
				challenges.remove(challengeid);
				return "OK";
			} else {
				return "Challenge not yet finished";
			}
		} else {
			return "No such challenge found";
		}
	}
}
