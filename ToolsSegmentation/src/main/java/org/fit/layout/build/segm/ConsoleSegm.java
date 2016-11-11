/**
 * ConsoleTest.java
 *
 * Created on 13. 1. 2016, 23:28:24 by burgetr
 */
package org.fit.layout.build.segm;

import java.io.IOException;

import javax.script.ScriptException;

import org.fit.layout.tools.Console;

/**
 * 
 * @author burgetr
 */
public class ConsoleSegm extends Console
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
        BlockBrowserSegm.main(new String[0]);
    }
    
    public static void main(String[] args)
    {
        System.out.println("FitLayout interactive console [Segmentation]");
        Console con = new ConsoleSegm();
        try
        {
            con.interactiveSession(System.in, System.out, System.err);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
