package com.springboot_practice.Spring.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@ResponseBody
public class HomeController {
	@GetMapping("/home")
	public String getHomePage() {
		return "Welcome to Home page";
	}
	@GetMapping("/dashboard")
	public String getDashboardPage() {
		return "login successfull";
		
		}
}
