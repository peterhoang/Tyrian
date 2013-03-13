package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.domain.Profile;

public class HighScoresScreen
    extends
        AbstractScreen
{
    private Table table;
    private Profile profile;

    public HighScoresScreen(
        Tyrian game )
    {
        super( game );
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();

        // create the table actor and add it to the stage
        table = new Table( skin );
        stage.addActor( table );

        // create the labels widgets
        String level1Highscore = String.valueOf( profile.getHighScore( 0 ) );
        Label episode1HighScore = new Label( level1Highscore, skin );
        table.row();table.add(episode1HighScore);table.add(level1Highscore);
       
        String level2Highscore = String.valueOf( profile.getHighScore( 1 ) );
        Label episode2HighScore = new Label( level2Highscore, skin );
        table.row();table.add(episode2HighScore);table.add(level2Highscore);
       
        String level3Highscore = String.valueOf( profile.getHighScore( 2 ) );
        Label episode3HighScore = new Label( level3Highscore, skin );
        table.row();table.add(episode3HighScore);table.add(level3Highscore);
       
        // register the button "start game"
        TextButton backButton = new TextButton( "Back to main menu", skin );
//        backButton.setClickListener( new ClickListener() {
//            @Override
//            public void click(
//                Actor actor,
//                float x,
//                float y )
//            {
//                game.setScreen( game.getMenuScreen() );
//            }
//        } );
        table.row();table.add( backButton );       
    }

    @Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );

        // resize the table
        table.setWidth(width);
        table.setHeight(height);

        // we need a complete redraw
        table.invalidateHierarchy();
    }
}

