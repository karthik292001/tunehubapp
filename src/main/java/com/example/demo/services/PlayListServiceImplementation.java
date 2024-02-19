package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.controller.PlayListController;
import com.example.demo.entities.PlayList;
import com.example.demo.entities.Songs;
import com.example.demo.repositories.PlayListRepository;

@Service
public class PlayListServiceImplementation implements PlayListService{
	
	@Autowired
	PlayListRepository playlistRepo;

	@Override
	public String addPlayList(PlayList playlist) {
		playlistRepo.save(playlist);
		return "addplaylistsuccess";
	}

	@Override
	public List<PlayList> viewPlayList() {
		List<PlayList> playList=playlistRepo.findAll();
		return playList;
	}

	@Override
	public boolean isNameExist(PlayList playlist) {
		PlayList pylst= playlistRepo.findByName(playlist.getName());
		if(pylst!=null) {
			return true;
		}
		return false;
	}
	

	@Override
	public void deletePlaylist(int id) {
		PlayList playlist = playlistRepo.findById(id).get();
		for(Songs song:playlist.getSongs()) {
			song.getPlaylist().remove(playlist);
		}
		playlistRepo.deleteById(id);
	}

	
	
	
}
