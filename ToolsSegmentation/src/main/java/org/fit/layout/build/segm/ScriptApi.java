/**
 * ScriptApi.java
 *
 * Created on 28. 4. 2015, 16:40:17 by burgetr
 */
package org.fit.layout.build.segm;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.fit.cssbox.layout.Viewport;
import org.fit.layout.api.ScriptObject;
import org.fit.pis.PageLoader;
import org.fit.pis.comp.BCSProcessor;
import org.fit.pis.comp.VIPSProcessor;
import org.fit.pis.out.TextOutput;
import org.xml.sax.SAXException;

/**
 * JavaScript application interface for the storage.
 * @author burgetr
 */
public class ScriptApi implements ScriptObject
{
    @SuppressWarnings("unused")
    private BufferedReader rin;
    @SuppressWarnings("unused")
    private PrintWriter wout;
    private PrintWriter werr;

    
    public ScriptApi()
    {
        
    }
    
    @Override
    public String getVarName()
    {
        return "eval";
    }
    
    @Override
    public void setIO(Reader in, Writer out, Writer err)
    {
        rin = new BufferedReader(in);
        wout = new PrintWriter(out);
        werr = new PrintWriter(err);
    }
    
    public void saveReference(String uri, Viewport vp)
    {
        String home = "/tmp/";
        URL url = null;
        try
        {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            werr.println(e.getMessage());
            return;
        }

        /* PageLoader is being used as it supports the abstraction
         * of boxes and the web page is therefore rendered as a set
         * of rectangles rather than real web page.
         */
        PageLoader pl = new PageLoader(url);
        pl.setViewport(vp);

        /* Just an example, any other prefix can be used */
        String nameBase = url.getHost().replaceAll("[^a-zA-Z0-9\\-]+", "-");

        /* Store the abstracted image of the processed web page and all the
         * other files*/
        pl.save(home + nameBase + ".png");
        try
        {
            produceData(home, nameBase, vp);
        } catch (Exception e) {
            werr.println(e.getMessage());
        }
    }

    private void produceData(String basePath, String nameBase, Viewport view) throws IOException, SAXException, Exception 
    {
        BCSProcessor bcs = null;
        VIPSProcessor vips = null;
        Rectangle r;

        if (basePath == null) {
            basePath = System.getProperty("user.home")+"/";
        }

        r = new Rectangle(view.getWidth(), view.getHeight());
        bcs = new BCSProcessor(basePath, nameBase, r, view.getRootBox());
        vips = new VIPSProcessor(basePath, nameBase, view);
        (new TextOutput(bcs.getAreas())).save(basePath+nameBase+"-boxes.txt");

        for (int i = 1; i <= 10; i++) {
            bcs.process(i/10.0);
            if (vips != null) vips.process(i);
        }
    }

}
