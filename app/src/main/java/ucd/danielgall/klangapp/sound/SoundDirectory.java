package ucd.danielgall.klangapp.sound;

import android.content.Context;
import ucd.danielgall.klangapp.sound.elements.SoundElement;

public class SoundDirectory {

    /*
    This class retrieves the ID of a sound file from
    the raw folder.

    We need this as a prerequisite to loading in the
    sound clip.
     */

    private final String RES_FOLDER = "raw";

    private Context appContext;

    public SoundDirectory(Context context) {
        this.appContext = context;
    }

    /*
    Get resource ID of a particular sound element.
     */
    public int getId(SoundElement element) {
        String fileName = element.getFileName();
        return getId(fileName);
    }

    private int getId(String file) {
        if (exists(file, RES_FOLDER)) {
            return getResource(file, RES_FOLDER);
        } else {
            throw new IllegalArgumentException("Invalid Resource");
        }
    }

    private int getResource(String file, String folder) {
        String resPackage = appContext.getPackageName();

        return appContext.getResources().getIdentifier(
                file, folder, resPackage);
    }

    /*
    Checks if this file exists within a particular
    folder.
    */
    private boolean exists(String fileName, String folder) {
        return getResource(fileName, folder) != 0;
    }
}
