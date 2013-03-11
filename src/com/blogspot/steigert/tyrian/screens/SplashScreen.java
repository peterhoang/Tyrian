package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.blogspot.steigert.tyrian.Tyrian;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen
    extends
        AbstractScreen
{
    private Texture splashTexture;

    public SplashScreen(
        Tyrian game )
    {
        super( game );
    }

    @Override
    public void show()
    {
        super.show();

        // load the splash image and create the texture region
        splashTexture = new Texture( "splash.png" );

        // we set the linear texture filter to improve the stretching
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
    }
    
    @Override
    public void resize(int width, int height) 
    {
    	super.resize(width, height);
    	
    	stage.clear();
    	
    	// in the image atlas, our splash image begins at (0,0) of the
        // upper-left corner and has a dimension of 512x301
        TextureRegion splashRegion = new TextureRegion( splashTexture, 0, 0, 512, 301 );
        TextureRegionDrawable drawableRegion = new TextureRegionDrawable(splashRegion);
        
        // here we create the splash image actor and set its size
        Image splashImage = new Image( drawableRegion, Scaling.stretch, Align.bottom | Align.left ); 
        
        splashImage.setWidth(width);
        splashImage.setHeight(height);
        
        // Set the image to 0 alpha for fade-in effect
        splashImage.getColor().a = 0f;
        
        // configure the fade-in/out effect on the splash image
        splashImage.addAction(sequence(fadeIn(0.75f), delay(1.75f, fadeOut(0.75f)), run(new Runnable() {
        	public void run () {
        		game.setScreen( game.getMenuScreen() );
        	}
        })));
        
        stage.addActor(splashImage);
    }


    @Override
    public void dispose()
    {
        super.dispose();
        splashTexture.dispose();
    }
}