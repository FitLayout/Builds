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

import org.fit.layout.api.AreaTreeOperator;
import org.fit.layout.classify.op.VisualClassificationOperator;
import org.fit.layout.tools.BlockBrowser;

/**
 * Block browser extension specific for the Classify project.
 * @author burgetr
 */
public class BlockBrowserClassify extends BlockBrowser
{

    public BlockBrowserClassify()
    {
        super();
        
        //register the custom feature extractor to work with the visual classifier
        AreaTreeOperator vcls = getProcessor().getOperators().get("FitLayout.Tag.Visual");
        if (vcls != null && vcls instanceof VisualClassificationOperator)
        {
            ((VisualClassificationOperator) vcls).setFeatures(new SimpleFeatureExtractor());
        }
        else
            System.err.println("Couldn't configure FitLayout.Tag.Visual!");
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
                    browser = new BlockBrowserClassify();
                    browser.setLoadImages(false);
                    JFrame main = browser.getMainWindow();
                    //main.setSize(1000,600);
                    //main.setMinimumSize(new Dimension(1200, 600));
                    //main.setSize(1500,600);
                    main.setSize(1600,1000);
                    browser.initPlugins();
                    ((BlockBrowserClassify) browser).initScripts();
                    main.setVisible(true);
                    
                    //String localpath = "file://" + System.getProperty("user.home");
                    //localpath += "/git/TestingLayout";
            
                    //URL url = new URL("http://cssbox.sf.net/");
                    //URL url = new URL("http://www.idnes.cz/");
                    //URL url = new URL("http://olomouc.idnes.cz/rad-nemeckych-rytiru-pozadal-v-restitucich-i-o-hrady-bouzov-a-sovinec-12b-/olomouc-zpravy.aspx?c=A131113_115042_olomouc-zpravy_mip");
                    //URL url = new URL("http://www.aktualne.cz/");
                    URL url = new URL("http://www.reuters.com/article/us-usa-election-idUSKCN0XN12P");
                    //URL url = new URL("http://www.reuters.com/article/us-tanzania-poaching-insight-idUSKCN0VJ0FZ"); //TODO segmentace?
                    
                    browser.setLocation(url.toString());
                        
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
