package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Songs;
import com.example.demo.entities.Users;
import com.example.demo.services.PlayListService;
import com.example.demo.services.SongService;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PlayListController {

	@Autowired
	SongService songSrv;
	
	@Autowired
	PlayListService playlistsrv;
	
	@Autowired
	UserService usersrv;
	
	
	@GetMapping("/createplaylist")
	public String createPlayList(Model model,HttpSession session) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		
		List<Songs> songslist = songSrv.readAllSongs();
		model.addAttribute("songsList",songslist);
		return "createplaylist";
	}
	
	@PostMapping("/addplaylist")
	public String addPlayList(@ModelAttribute PlayList playlist,Model model,HttpSession session) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		
		boolean isNameExist=playlistsrv.isNameExist(playlist);
		if(!isNameExist) {
			playlistsrv.addPlayList(playlist);
			List<Songs> songList= playlist.getSongs();
			for(Songs song:songList) {
				song.getPlaylist().add(playlist);
				songSrv.updatesong(song);
			}
			return "addplaylistsuccess";
		}else {
			List<Songs> songslist = songSrv.readAllSongs();
			model.addAttribute("songsList",songslist);
			return "addplaylistfail";
		}
	}
	
	@GetMapping("/viewplaylist")
	public String viewPlayList(Model model,HttpSession session) {
		Users user= usersrv.getUser((String)session.getAttribute("email"));
		model.addAttribute("User", user);
		
		List<PlayList> playList=playlistsrv.viewPlayList();
		model.addAttribute("playlist", playList);
		return "viewplaylist";
	}
	
	@PostMapping("/deleteplaylist")
	public String deletePlaylist(@RequestParam int id) {
		playlistsrv.deletePlaylist(id);
		return "deleteplaylist";
	}
	
}
