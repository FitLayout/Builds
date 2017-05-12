/**
 * GraphicalOutput.java
 *
 * Created on 12. 5. 2017, 10:04:35 by burgetr
 */
package org.fit.layout.build.segm;

import javax.script.ScriptException;

import org.fit.layout.process.ScriptableProcessor;

/**
 * Executes the predefined analysis process.
 * 
 * @author burgetr
 */
public class GraphicalOutput
{
    private ScriptableProcessor proc;
    private String inputUrl;

    public GraphicalOutput(String inputUrl)
    {
        this.inputUrl = inputUrl;
        proc = new ScriptableProcessor();
    }

    public String getInputUrl()
    {
        return inputUrl;
    }

    private void runAnalysis()
    {
        try
        {
            proc.put("app", this);
            proc.execInternal("run_output.js");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
    
    //=============================================================================================
    
    public static void main(String[] args)
    {
        if (args.length == 1)
        {
            GraphicalOutput pa = new GraphicalOutput(args[0]);
            pa.runAnalysis();
            System.exit(0);
        }
        else
            System.err.println("Usage: BCS <input_url>");
    }

}
