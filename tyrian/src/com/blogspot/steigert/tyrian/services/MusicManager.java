package com.blogspot.steigert.tyrian.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.blogspot.steigert.tyrian.Tyrian;

public class MusicManager implements Disposable 
{	
	/**
     * The available music files.
     */
	public enum TyrianMusic
	{
		MENU("music/menu.ogg"),
		LEVEL("music/level.ogg");
		
		private final String fileName;
		
		private TyrianMusic(String fileName)
		{
			this.fileName = fileName;
		}
		
		public String getFileName()
		{
			return fileName;
		}	
	}
	
	private Music musicBeingPlayed;
	
	private float volume = 1f;
	
	private boolean enabled = true;
	
	public MusicManager() { }
	
	public void play(TyrianMusic music)
	{
		if (!enabled) return;
		
		Gdx.app.log(Tyrian.LOG, "Playing music: " + music.name());
		stop();
		
		FileHandle musicFile = Gdx.files.internal(music.getFileName());
		musicBeingPlayed = Gdx.audio.newMusic(musicFile);
		musicBeingPlayed.setVolume(volume);
		musicBeingPlayed.setLooping(true);
		musicBeingPlayed.play();
	}
	
	public void stop()
	{
		if (musicBeingPlayed != null) {
			Gdx.app.log(Tyrian.LOG, "Stopping current music");
			musicBeingPlayed.stop();
			musicBeingPlayed.dispose();
		}
	}
	
	public void setVolumen(float volume)
	{
		Gdx.app.log(Tyrian.LOG, "Adjusting music volume to: " + volume);
		
		if (volume < 0 || volume > 1f){
			throw new IllegalArgumentException("The volume must be inside the range [0,1]");
		}
		this.volume = volume;
		
		// if there is music being played set the volume
		if (musicBeingPlayed != null) {
			musicBeingPlayed.setVolume(volume);
		}
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		
		if (!enabled) {
			stop();
		}
	}
	
	public void dispose()
	{
		Gdx.app.log(Tyrian.LOG,  "Disposing music manager");
		stop();
	}
}
