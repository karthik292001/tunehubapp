package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Songs;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	UserService userv;
	
	@Autowired
	SongService songsrv;
	
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		boolean isUserExist=userv.isEmailExists(user.getEmail());
		if(isUserExist) {
			return "registerfail";
		}else {
			userv.addUser(user);
			return "registersuccess";
		}
	}
	
	@PostMapping("/login")
	public String validateUser(@RequestParam String email,@RequestParam String password, HttpSession session, Model model) {
		boolean loginStatus=userv.validateUser(email, password);
		
		if(loginStatus) {
			
			session.setAttribute("email", email);
			Users user=userv.getUser(email);
			model.addAttribute("User", user);
			
			String userRole=userv.getUserRole(email);
			if(userRole.equalsIgnoreCase("admin")) {
				return "adminhomepage";
			}else {
				return "userhomepage";
			}
		}else {
			return "loginfail";
		}
	}
	
	
	@GetMapping("/exploresongs")
	public String getUser(Model model, HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		
		Users user = userv.getUser(email);
		boolean premiumStatus = user.isPremium();
		model.addAttribute("User", user);
		
		if(premiumStatus) {
			List<Songs> listsongs=songsrv.readAllSongs();
			model.addAttribute("songs", listsongs);
			return "usersongs";
		}else {
			return "payment";
		}
	}
	
	@PostMapping("/updatepassword")
	public String updateUser(@RequestParam String email, @RequestParam String password) {
		Users user = userv.getUser(email);
		if(user==null) {
			return "updatepasswordfail";
		}
		else {
			user.setPassword(password);
			userv.updateUser(user);	
			return "login";
		}
	}
	
}
