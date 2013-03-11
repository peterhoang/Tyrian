package com.blogspot.steigert.tyrian.screens;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.blogspot.steigert.tyrian.Tyrian;
 
public class MenuScreen
    extends
        AbstractScreen
{
	public MenuScreen(
	        Tyrian game )
	    {
	        super( game );
	    }

	    @Override
	    public void resize(
	        int width,
	        int height )
	    {
	        super.resize( width, height );

	        // retrieve the skin (created on the AbstractScreen class)
	        Skin skin = super.getSkin();

	        // create the table actor
	        Table table = new Table( getSkin() );
	        table.setWidth(width);
	        table.setHeight(height);
	        table.layout();
        

	        // register the label "welcome"
	        Label welcomeLabel = new Label( "Welcome to Tyrian for Android!", skin );
	        table.add(welcomeLabel);

	        // register the button "start game"
	        TextButton startGameButton = new TextButton( "Start game", skin );
	        table.row();
	        table.add( startGameButton );

	        // register the button "options"
	        TextButton optionsButton = new TextButton( "Options", skin );
	        table.row();
	        table.add(  optionsButton );

	        // register the button "hall of fame"
	        TextButton hallOfFameButton = new TextButton( "Hall of Fame", skin );
	        table.row();
	        table.add(hallOfFameButton );


	        
	        // add the table to the stage and retrieve its layout
	        stage.addActor( table );
	    }
}