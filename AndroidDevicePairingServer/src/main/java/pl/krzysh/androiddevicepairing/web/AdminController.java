package pl.krzysh.androiddevicepairing.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.krzysh.androiddevicepairing.service.DeviceManager;
import pl.krzysh.androiddevicepairing.service.PermissionManager;

@Controller
public class AdminController {
	@Autowired
	public DeviceManager deviceManager;
	@Autowired
	public PermissionManager permissionManager;

	@RequestMapping("/admin")
	public ModelAndView handleIndex() throws ServletException, IOException {
		return new ModelAndView("admin", "page", "index");
	}

	@RequestMapping("/admin/devices")
	public ModelAndView handleDevices() throws ServletException, IOException {
		return new ModelAndView("admin", "page", "devices");
	}
}
