package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.PlayList;
import com.example.demo.entities.Songs;
import com.example.demo.repositories.PlayListRepository;
import com.example.demo.repositories.SongsRepository;

@Service
public class SongServiceImplementaion implements SongService{

	@Autowired
	SongsRepository songRepo;
		
	
	
	@Override
	public String addSong(Songs song) {
		songRepo.save(song);
		return "addsongsuccess";
	}

	@Override
	public boolean isNameExist(String name) {
		Songs song=songRepo.findByName(name);
		if(song!=null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Songs> readAllSongs() {
		return songRepo.findAll();
	}

	@Override
	public void updatesong(Songs song) {
		songRepo.save(song);
	}

	@Override
	public void deleteSong(int id) {
		Songs song = songRepo.findById(id).get();
	    for (PlayList playlist : song.getPlaylist()) {
	        playlist.getSongs().remove(song);
	    }
	    songRepo.deleteById(id);
	}

}
