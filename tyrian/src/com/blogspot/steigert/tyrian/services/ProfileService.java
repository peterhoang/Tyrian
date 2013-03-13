package com.blogspot.steigert.tyrian.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.blogspot.steigert.tyrian.Tyrian;
import com.blogspot.steigert.tyrian.domain.Profile;

/**
 * Profile operations.
 */
public class ProfileService
{
    // the location of the profile data file
    private static final String PROFILE_DATA_FILE = ".tyrian/profile-v1.json";

    // the loaded profile (may be null)
    private Profile profile;

    /**
     * Creates the profile service.
     */
    public ProfileService()
    {
    }

    /**
     * Retrieves the player's profile, creating one if needed.
     */
    public Profile retrieveProfile()
    {
        Gdx.app.log( Tyrian.LOG, "Retrieving profile" );

        // if the profile is already loaded, just return it
        if( profile != null ) return profile;

        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.external( PROFILE_DATA_FILE );

        // create the JSON utility object
        Json json = new Json();

        // check if the profile data file exists
        if( profileDataFile.exists() ) {

            // load the profile from the data file
            try {

                // read the file as text
                String profileAsText = profileDataFile.readString().trim();

                // decode the contents (if it's base64 encoded)
                if( profileAsText.matches( "^[A-Za-z0-9/+=]+$" ) ) {
                    Gdx.app.log( Tyrian.LOG, "Persisted profile is base64 encoded" );
                    profileAsText = Base64Coder.decodeString( profileAsText );
                }

                // restore the state
                profile = json.fromJson( Profile.class, profileAsText );

            } catch( Exception e ) {

                // log the exception
                Gdx.app.error( Tyrian.LOG, "Unable to parse existing profile data file", e );

                // recover by creating a fresh new profile data file;
                // note that the player will lose all game progress
                profile = new Profile();
                persist( profile );

            }

        } else {
            // create a new profile data file
            profile = new Profile();
            persist( profile );
        }

        // return the result
        return profile;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(
        Profile profile )
    {
        Gdx.app.log( Tyrian.LOG, "Persisting profile" );

        // create the JSON utility object
        Json json = new Json();

        // create the handle for the profile data file
        FileHandle profileDataFile = Gdx.files.external( PROFILE_DATA_FILE );

        // convert the given profile to text
        String profileAsText = json.toJson( profile );

        // encode the text
        if( ! Tyrian.DEV_MODE ) {
            profileAsText = Base64Coder.encodeString( profileAsText );
        }

        // write the profile data file
        profileDataFile.writeString( profileAsText, false );
    }

    /**
     * Persists the player's profile.
     * <p>
     * If no profile is available, this method does nothing.
     */
    public void persist()
    {
        if( profile != null ) {
            persist( profile );
        }
    }
}