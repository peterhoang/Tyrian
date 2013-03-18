package com.blogspot.steigert.tyrian.screens;

import java.util.Locale;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.services.MusicManager.TyrianMusic;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;

/**
 * A simple options screen.
 */
public class OptionsScreen
    extends
        AbstractScreen
{
    private Table table;
    private Label volumeValue;

    public OptionsScreen(
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
        final CheckBox soundEffectsCheckbox = new CheckBox( "soundEffectsCheckbox", skin );
        soundEffectsCheckbox.setChecked( game.getPreferences().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferences().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
                game.getSoundManager().play( TyrianSound.CLICK );
            }
        } );
        table.row(); table.add( soundEffectsCheckbox );

        final CheckBox musicCheckbox = new CheckBox( "musicCheckbox", skin );
        musicCheckbox.setChecked( game.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(
                ChangeEvent event,
                Actor actor )
            {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferences().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );
                game.getSoundManager().play( TyrianSound.CLICK );

                // if the music is now enabled, start playing the menu music
                if( enabled ) game.getMusicManager().play( TyrianMusic.MENU );
            }
        } );
        table.row();table.add( musicCheckbox );

        // range is [0.0,1.0]; step is 0.1f
        Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, skin );
        volumeSlider.setValue( game.getPreferences().getVolume() );
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor)
            {
            	float value = ((Slider)actor).getValue();
            	game.getPreferences().setVolume(value);
            	game.getMusicManager().setVolume(value);
            	game.getSoundManager().setVolume(value);
            	updateVolumeLabel();
            }
        } );
        table.row();table.add( volumeSlider );

        volumeValue = new Label( "volumeValue", skin );
        updateVolumeLabel();
        table.row(); table.add( volumeValue );

        // register the back button
        TextButton backButton = new TextButton( "Back to main menu", skin );
        backButton.addListener( new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                game.setScreen( game.getMenuScreen() );
                return true;
            }
        } );
        table.row(); table.add( backButton );

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

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel()
    {
        float volume = ( game.getPreferences().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
    }
}