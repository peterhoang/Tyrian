package com.blogspot.steigert.tyrian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.blogspot.steigert.tyrian.screens.MenuScreen;
import com.blogspot.steigert.tyrian.screens.SplashScreen;

public class Tyrian extends Game {

	// constant useful for logging
    public static final String LOG = Tyrian.class.getSimpleName();

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
    
    // Screen methods
    
    public SplashScreen getSplashScreen()
    {
    	return new SplashScreen(this);
    }
    
    public MenuScreen getMenuScreen()
    {
    	return new MenuScreen(this);
    }
	
    // Game methods

    @Override
    public void create()
    {
        Gdx.app.log( Tyrian.LOG, "Creating game" );
        fpsLogger = new FPSLogger();
        setScreen( getSplashScreen() );
    }

    @Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );
        Gdx.app.log( Tyrian.LOG, "Resizing game to: " + width + " x " + height );
    }

    @Override
    public void render()
    {
        super.render();

        // output the current FPS
        fpsLogger.log();
    }

    @Override
    public void pause()
    {
        super.pause();
        Gdx.app.log( Tyrian.LOG, "Pausing game" );
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
