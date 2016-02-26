/**
 * BlockBrowserTest.java
 *
 * Created on 8. 2. 2016, 9:21:16 by burgetr
 */

package org.fit.layout.build.times;

import java.awt.EventQueue;
import java.net.URL;

import javax.script.ScriptException;
import javax.swing.JFrame;

import org.fit.layout.tools.BlockBrowser;

/**
 * Block browser extension specific for the project.
 * @author burgetr
 */
public class BlockBrowserTimes extends BlockBrowser
{

    public BlockBrowserTimes()
    {
        super();
    }

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
                    browser = new BlockBrowserTimes();
                    browser.setLoadImages(false);
                    JFrame main = browser.getMainWindow();
                    //main.setSize(1000,600);
                    //main.setMinimumSize(new Dimension(1200, 600));
                    //main.setSize(1500,600);
                    main.setSize(1600,1000);
                    browser.initPlugins();
                    ((BlockBrowserTimes) browser).initScripts();
                    main.setVisible(true);
                    
                    String localpath = "file://" + System.getProperty("user.home");
                    localpath += "/git/TestingLayout";
            
                    /* SCHEDULES */
                    
                    //URL url = new URL(localpath + "/test/schedule/brno30.pdf");
                    //URL url = new URL(localpath + "/test/schedule/dpmb_z_30.pdf");
                    URL url = new URL(localpath + "/test/schedule/dpmb_30_kamenolom.pdf");
                    
                    
                    browser.setLocation(url.toString());
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
