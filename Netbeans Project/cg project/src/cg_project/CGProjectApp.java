/*
 * CGProjectApp.java
 */

package cg_project;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class CGProjectApp extends SingleFrameApplication {
    public static String arg[];
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        try {
            show(new CGProjectView(this));
        } catch (Exception ex) {
            Logger.getLogger(CGProjectApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static CGProjectApp getApplication() {
        return Application.getInstance(CGProjectApp.class);
    }
    
    public static void main(String[] args) {
           arg = args;
           launch(CGProjectApp.class, args);
       
        
    }
}
