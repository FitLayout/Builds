/**
 * ConsoleClassify.java
 *
 * Created on 8. 2. 2016, 23:28:24 by burgetr
 */
package org.fit.layout.build.classify;

import java.io.IOException;

import javax.script.ScriptException;

import org.fit.layout.classify.InstanceExtractor;
import org.fit.layout.tools.Console;

/**
 * Console extension specific for the Classify project.
 * @author burgetr
 */
public class ConsoleClassify extends Console
{
    private InstanceExtractor extractor;

    @Override
    protected void init()
    {
        super.init();
        //custom instance extractor for training data extraction from the annotated pages
        extractor = new InstanceExtractor(new SimpleFeatureExtractor(), "FitLayout.Annotate");
        getProcessor().put("extr", extractor);
    }

    @Override
    protected void initSession() throws ScriptException
    {
        super.initSession();
        getProcessor().execInternal("config.js");
        getProcessor().execInternal("console_init.js");
    }

    public void browser()
    {
        BlockBrowserClassify.main(new String[0]);
    }
    
    public static void main(String[] args)
    {
        System.out.println("FitLayout interactive console [Classify]");
        Console con = new ConsoleClassify();
        try
        {
            con.interactiveSession(System.in, System.out, System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
