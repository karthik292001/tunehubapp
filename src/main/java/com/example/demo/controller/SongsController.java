package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Songs;
import com.example.demo.entities.Users;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class SongsController {
	
	@Autowired
	SongService songsrv;
	
	@Autowired
	UserService usersrv;
	
	@Autowired
	PlayListService plylstsrv;
	
	
	@PostMapping("/addsong")
	public String addSong(@ModelAttribute Songs song,HttpSession session,Model model) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		
		boolean isNameExist=songsrv.isNameExist(song.getName());
		if(isNameExist) {
			return "addsongfail";
		}else {
			songsrv.addSong(song);
			return "addsongsuccess";
		}	
	}
	
	
	@GetMapping("/readsongs")
	public String readAllSongs(Model model,HttpSession session) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		List<Songs> listsongs=songsrv.readAllSongs();
		model.addAttribute("songs", listsongs);
		return "listofsongs";
	}
	
	@PostMapping("/deletesong")
	public String deleteSong(@RequestParam int id)
	{
		songsrv.deleteSong(id);
		return "deletesong";
	}

}