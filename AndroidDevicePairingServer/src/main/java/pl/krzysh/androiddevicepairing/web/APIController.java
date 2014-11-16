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
@RequestMapping("/api")
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
	
	
	protected class Response {
		protected boolean success;
		public boolean getSuccess() {
			return success;
		}
	}
	protected class ResponseError extends Response {
		protected String message;
		ResponseError(String message) {
			this.success = false;
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
	}
	protected class ResponseSuccess extends Response {
		ResponseSuccess() {
			this.success = true;
		}
	}
	
	protected class ResponseDeviceRegister extends ResponseSuccess {
		protected String password;
		ResponseDeviceRegister(String password) {
			super();
			this.password = password;
		}
		public String getPassword() {
			return password;
		}
	}
	@RequestMapping(value = "/devices/register")
	@ResponseBody
	public Response handleDeviceRegister(@RequestParam("deviceid") String deviceid) {
		if(!deviceManager.isDeviceRegistered(deviceid)) {
			String password = Passwords.generatePassword();
			Device device = new Device(deviceid);
			device.setPassword(password);
			deviceManager.addDevice(device);
			return new ResponseDeviceRegister(password);
		} else {
			return new ResponseError("Already registered!");
		}
	}
	
	protected class ResponseDeviceLogin extends ResponseSuccess {
		protected String challengeid;
		protected String key;
		ResponseDeviceLogin(String challengeid, String key) {
			super();
			this.challengeid = challengeid;
			this.key = key;
		}
		public String getChallengeid() {
			return challengeid;
		}
		public String getKey() {
			return key;
		}
	}
	@RequestMapping("/devices/login")
	@ResponseBody
	public Response handleDeviceLogin(@RequestParam("deviceid") String deviceid) {
		if(deviceManager.isDeviceRegistered(deviceid)) {
			//TODO: Flood protection
			String challengeid = Passwords.generatePassword();
			String key = Passwords.generatePassword();
			Challenge challenge = new Challenge(deviceManager.getDeviceById(deviceid), key);
			challenges.put(challengeid, challenge);
			// /api/devices/loginchallenge <- challengeid="+challengeid+", response=SHA256(your_password+"+key+")"
			return new ResponseDeviceLogin(challengeid, key);
		} else {
			return new ResponseError("Not registered");
		}
	}

	protected class ResponseDeviceLoginChallenge extends ResponseSuccess {
		protected String challengeid;
		protected String password;
		ResponseDeviceLoginChallenge(String challengeid, String password) {
			super();
			this.challengeid = challengeid;
			this.password = password;
		}
		public String getChallengeid() {
			return challengeid;
		}
		public String getPassword() {
			return password;
		}
	}
	@RequestMapping("/devices/loginchallenge")
	@ResponseBody
	public Response handleDeviceLoginChallenge(@RequestParam("challengeid") String challengeid, @RequestParam("response") String response) {
		if(challenges.containsKey(challengeid)) {
			Challenge challenge = challenges.get(challengeid);
			if(!challenge.isFinished()) {
				if(Passwords.hashPassword(challenge.getDevice().getPassword()+challenge.getKey()).equals(response)) {
					String nextPassword = Passwords.generatePassword();
					challenge.finish(nextPassword);
					//please ACK to /api/devices/loginack?challengeid="+challengeid
					return new ResponseDeviceLoginChallenge(challengeid, nextPassword);
				} else {
					challenges.remove(challengeid);
					return new ResponseError("Bad response");
				}
			} else {
				return new ResponseError("Challenge already finished");
			}
		} else {
			return new ResponseError("No such challenge found");
		}
	}
	
	@RequestMapping("/devices/loginack")
	@ResponseBody
	public Response handleDeviceLoginAck(@RequestParam("challengeid") String challengeid) {
		if(challenges.containsKey(challengeid)) {
			Challenge challenge = challenges.get(challengeid);
			if(challenge.isFinished()) {
				Device device = challenge.getDevice();
				device.setPassword(challenge.getNextPassword());
				challenges.remove(challengeid);
				return new ResponseSuccess();
			} else {
				return new ResponseError("Challenge not yet finished");
			}
		} else {
			return new ResponseError("No such challenge found");
		}
	}
}
