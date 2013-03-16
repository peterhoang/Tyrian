package com.blogspot.steigert.tyrian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.blogspot.steigert.tyrian.screens.*;
import com.blogspot.steigert.tyrian.services.PreferenceManager;
import com.blogspot.steigert.tyrian.services.ProfileManager;

public class Tyrian extends Game {

	// constant useful for logging
    public static final String LOG = Tyrian.class.getSimpleName();

 // whether we are in development mode
    public static final boolean DEV_MODE = true;
    
    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
    
    // services
    private ProfileManager profileService;
    private PreferenceManager preference;
    
    public Tyrian() 
    {
    }
    
    // Service
    
    public ProfileManager getProfileService()
    {
    	return profileService;
    }
    
    public PreferenceManager getPreferences()
    {
    	return preference;
    }
    
    // Screen methods
    
    public SplashScreen getSplashScreen()
    {
    	return new SplashScreen(this);
    }
    
    public MenuScreen getMenuScreen()
    {
    	return new MenuScreen(this);
    }
    
    public HighScoresScreen getHighScoresScreen()
    {
        return new HighScoresScreen( this );
    }
    
    public LevelScreen getLevelScreen()
    {
    	return new LevelScreen(this);
    }
    
    public ProfileScreen getProfileScreen()
    {
    	return new ProfileScreen(this);
    }
    
    public StartGameScreen getStartGameScreen()
    {
    	return new StartGameScreen(this);
    }
    
    public LoadSavedGameScreen getLoadSavedGameScreen()
    {
    	return new LoadSavedGameScreen(this);
    }
    
    public OptionsScreen getOptionsScreen()
    {
    	return new OptionsScreen(this);
    }
	
    // Game methods

    @Override
    public void create()
    {
        Gdx.app.log( Tyrian.LOG, "Creating game" );
        fpsLogger = new FPSLogger();
       // profileService.retrieveProfile();

    	profileService = new ProfileManager();
    	preference = new PreferenceManager();
    }

    @Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );
        Gdx.app.log( Tyrian.LOG, "Resizing game to: " + width + " x " + height );
        if (getScreen() == null) {
            setScreen( getSplashScreen() );
        }
    }

    @Override
    public void render()
    {
        super.render();

        // output the current FPS
        if (DEV_MODE) fpsLogger.log();
    }

    @Override
    public void pause()
    {
        super.pause();
        Gdx.app.log( Tyrian.LOG, "Pausing game" );
        
        profileService.persist();
    }

    @Override
    public void resume()
    {
        super.resume();
        Gdx.app.log( Tyrian.LOG, "Resuming game" );
    }

    @Override
    public void setScreen(
        Screen screen )
    {
        super.setScreen( screen );
        Gdx.app.log( Tyrian.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

    @Override
    public void dispose()
    {
        super.dispose();
        Gdx.app.log( Tyrian.LOG, "Disposing game" );
    }
}
