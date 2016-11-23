/**
 * BlockBrowserTest.java
 *
 * Created on 24. 2. 2015, 9:21:16 by burgetr
 */

package org.fit.layout.build.segm;

import java.awt.EventQueue;
import java.net.URL;

import javax.script.ScriptException;
import javax.swing.JFrame;

import org.fit.layout.tools.BlockBrowser;

/**
 * 
 * @author burgetr
 */
public class BlockBrowserSegm extends BlockBrowser
{

    public BlockBrowserSegm()
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
        
        //getSegmAutorunCheckbox().setSelected(true);
        //getLogicalCombo().setSelectedIndex(1);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    browser = new BlockBrowserSegm();
                    browser.setLoadImages(false);
                    JFrame main = browser.getMainWindow();
                    //main.setSize(1000,600);
                    //main.setMinimumSize(new Dimension(1200, 600));
                    //main.setSize(1500,600);
                    main.setSize(1600,1000);
                    browser.initPlugins();
                    ((BlockBrowserSegm) browser).initScripts();
                    main.setVisible(true);
                    
                    String localpath = "file://" + System.getProperty("user.home");
                    localpath += "/git/TestingLayout";
            
                    
                    //URL url = new URL(localpath + "/test/ceur/volumes/Vol-1317.html");
                    //URL url = new URL("https://www.novinky.cz/zahranicni/amerika/419186-clintonove-hati-sanci-jeji-druha-dcera.html");
                    //URL url = new URL("http://www.businessinsider.com/mike-pompeo-cia-director-trump-2016-11");
                    URL url = new URL("http://ekonomika.idnes.cz/novela-trestniho-zakona-umozni-stihat-firmy-fn0-/ekonomika.aspx?c=A161122_133352_ekonomika_fih");
                    //URL url = new URL("http://www.reuters.com/article/us-usa-trump-idUSKBN13H1DZ");
                    //URL url = new URL("https://www.tagesschau.de/ausland/eu-parlament-tuerkei-107.html");
                    //URL url = new URL("https://ria.ru/world/20161123/1481962702.html");
                        
                    browser.setLocation(url.toString());
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
