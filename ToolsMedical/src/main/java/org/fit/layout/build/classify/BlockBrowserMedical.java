/**
 * BlockBrowserTest.java
 *
 * Created on 8. 2. 2016, 9:21:16 by burgetr
 */

package org.fit.layout.build.classify;

import java.awt.EventQueue;
import java.net.URL;

import javax.script.ScriptException;
import javax.swing.JFrame;

import org.fit.layout.tools.BlockBrowser;

/**
 * Block browser extension specific for the project.
 * @author burgetr
 */
public class BlockBrowserMedical extends BlockBrowser
{

    public void initScripts()
    {
        try
        {
            getProcessor().execInternal("config.js");
            getProcessor().execInternal("browser_init.js");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    browser = new BlockBrowserMedical();
                    browser.setLoadImages(false);
                    JFrame main = browser.getMainWindow();
                    //main.setSize(1000,600);
                    //main.setMinimumSize(new Dimension(1200, 600));
                    //main.setSize(1500,600);
                    main.setSize(1600,1000);
                    browser.initPlugins();
                    ((BlockBrowserMedical) browser).initScripts();
                    main.setVisible(true);
                    
                    //String localpath = "file://" + System.getProperty("user.home");
                    //localpath += "/git/TestingLayout";
            
                    URL url = new URL("http://cssbox.sf.net/");
                    //URL url = new URL("http://www.idnes.cz/");
                    //URL url = new URL("http://olomouc.idnes.cz/rad-nemeckych-rytiru-pozadal-v-restitucich-i-o-hrady-bouzov-a-sovinec-12b-/olomouc-zpravy.aspx?c=A131113_115042_olomouc-zpravy_mip");
                    //URL url = new URL("http://www.aktualne.cz/");
                    
                    
                    browser.setLocation(url.toString());
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
