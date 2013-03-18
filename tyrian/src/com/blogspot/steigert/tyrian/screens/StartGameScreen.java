package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.domain.FrontGun;
import com.blogspot.steigert.tyrian.domain.Item;
import com.blogspot.steigert.tyrian.domain.Level;
import com.blogspot.steigert.tyrian.domain.Profile;
import com.blogspot.steigert.tyrian.domain.Shield;
import com.blogspot.steigert.tyrian.domain.Ship;
import com.blogspot.steigert.tyrian.domain.ShipModel;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;

public class StartGameScreen
    extends
        AbstractScreen
{
    private Profile profile;
    private Ship ship;

    private Table table;
    private TextButton episode1Button;
    private TextButton episode2Button;
    private TextButton episode3Button;
    private SelectBox shipModelSelectBox;
    private SelectBox frontGunSelectBox;
    private SelectBox shieldSelectBox;
    private Label creditsLabel;

    private Image shipModelImage;
    private Image frontGunImage;
    private Image shieldImage;

    private LevelClickListener levelClickListener;
    private ItemSelectionListener itemSelectionListener;

    public StartGameScreen(
        Tyrian game )
    {
        super( game );

        // create the listeners
        levelClickListener = new LevelClickListener();
        itemSelectionListener = new ItemSelectionListener();
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

        // retrieve the table's layout
        profile = game.getProfileService().retrieveProfile();
        ship = profile.getShip();

        // create the level buttons
        episode1Button = new TextButton( "Episode 1", skin );
        episode1Button.setClickListener( levelClickListener );
       
        episode2Button = new TextButton( "Episode 2", skin );
        episode2Button.setClickListener( levelClickListener );
       
        episode3Button = new TextButton( "Episode 3", skin );
        episode3Button.setClickListener( levelClickListener );
       
        // create the item select boxes
        shipModelSelectBox = new SelectBox( skin );
        shipModelSelectBox.setItems( ShipModel.values() );
        shipModelSelectBox.setSelectionListener( itemSelectionListener );
       
        frontGunSelectBox = new SelectBox( skin );
        frontGunSelectBox.setItems( FrontGun.values() );
        frontGunSelectBox.setSelectionListener( itemSelectionListener );
       
        shieldSelectBox = new SelectBox( skin );
        shieldSelectBox.setItems( Shield.values() );
        shieldSelectBox.setSelectionListener( itemSelectionListener );
       
        // create the image placeholders
        shipModelImage = new Image();
        layout.register( "shipModelImage", shipModelImage );
        frontGunImage = new Image();
        layout.register( "frontGunImage", frontGunImage );
        shieldImage = new Image();
        layout.register( "shieldImage", shieldImage );

        // create the credits label
        creditsLabel = new Label( profile.getCreditsAsText(), skin );
        layout.register( "creditsLabel", creditsLabel );

        // register the back button
        TextButton backButton = new TextButton( "Back to main menu", skin );
        backButton.setClickListener( new ClickListener() {
            @Override
            public void click(
                Actor actor,
                float x,
                float y )
            {
                game.getSoundManager().play( TyrianSound.CLICK );
                game.setScreen( game.getMenuScreen() );
            }
        } );
        layout.register( "backButton", backButton );

        // finally, parse the layout descriptor
        layout.parse( Gdx.files.internal( "layout-descriptors/start-game-screen.txt" ).readString() );

        // set the select boxes' initial values
        updateValues();
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

    private void updateValues()
    {
        // select boxes
        shipModelSelectBox.setSelection( ship.getShipModel().ordinal() );
        frontGunSelectBox.setSelection( ship.getFrontGun().ordinal() );
        shieldSelectBox.setSelection( ship.getShield().ordinal() );

        // images
        shipModelImage.setRegion( getAtlas().findRegion( ship.getShipModel().getPreviewImage() ) );
        frontGunImage.setRegion( getAtlas().findRegion( ship.getFrontGun().getPreviewImage() ) );
        shieldImage.setRegion( getAtlas().findRegion( ship.getShield().getPreviewImage() ) );
    }

    /**
     * Listener for all the level buttons.
     */
    private class LevelClickListener
        implements
            ClickListener
    {
        @Override
        public void click(
            Actor actor,
            float x,
            float y )
        {
            game.getSoundManager().play( TyrianSound.CLICK );
            
            // find the target level ID
            Level[] levels = Level.retrieve();
            int targetLevelId = 0;
            if( actor == episode1Button ) {
                targetLevelId = levels[ 0 ].getId();
            } else if( actor == episode2Button ) {
                targetLevelId = levels[ 1 ].getId();
            } else if( actor == episode3Button ) {
                targetLevelId = levels[ 2 ].getId();
            }

            // check the current level ID
            if( profile.getCurrentLevelId() >= targetLevelId ) {
                Gdx.app.log( Tyrian.LOG, "Starting level: " + targetLevelId );
            } else {
                Gdx.app.log( Tyrian.LOG, "Unable to start level: " + targetLevelId );
            }
        }
    }

    /**
     * Listener for all the item select boxes.
     */
    private class ItemSelectionListener
        implements
            SelectionListener
    {
        @Override
        public void selected(
            Actor actor,
            int index,
            String value )
        {
            // find the selected item
            Item selectedItem = null;
            if( actor == shipModelSelectBox ) {
                selectedItem = ShipModel.values()[ index ];
            } else if( actor == frontGunSelectBox ) {
                selectedItem = FrontGun.values()[ index ];
            } else if( actor == shieldSelectBox ) {
                selectedItem = Shield.values()[ index ];
            }

            // if the ship already contains the item, there is no need to buy it
            if( ship.contains( selectedItem ) ) return;

            // try and buy the item
            if( profile.buy( selectedItem ) ) {
                creditsLabel.setText( profile.getCreditsAsText() );
            }

            // update the widgets
            updateValues();
        }
    }
}