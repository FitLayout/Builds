/**
 * ConsoleClassify.java
 *
 * Created on 8. 2. 2016, 23:28:24 by burgetr
 */
package org.fit.layout.build.classify;

import java.io.IOException;

import javax.script.ScriptException;

import org.fit.layout.tools.Console;

/**
 * Console extension specific for the project.
 * @author burgetr
 */
public class ConsoleMedical extends Console
{

    @Override
    protected void initSession() throws ScriptException
    {
        super.initSession();
        getProcessor().execInternal("config.js");
        getProcessor().execInternal("console_init.js");
    }

    public void browser()
    {
        BlockBrowserMedical.main(new String[0]);
    }
    
    public static void main(String[] args)
    {
        System.out.println("FitLayout interactive console [Medical]");
        Console con = new ConsoleMedical();
        try
        {
            con.interactiveSession(System.in, System.out, System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
