package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entities.Users;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class NavContoller {
	
	@Autowired
	UserService usersrv;

	@GetMapping("/map-registerpage")
	public String registerPage() {
		return "register";
	}
	
	@GetMapping("/map-loginPage")
	public String loginPage(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	@GetMapping("/map-addsongs")
	public String addSongs(HttpSession session,Model model) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		return "addsongs";
	}
	
	@GetMapping("/map-adminhome")
	public String adminHomePage(HttpSession session, Model model) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		return "adminhomepage";
	}
	
	@GetMapping("/map-userhome")
	public String userHomePage(HttpSession session, Model model) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		return "userhomepage";
	}
	
	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "forgotpassword";
	}
}
