/*
 * Tomas Popela, xpopel11, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - VipsTester.java
 */

package org.fit.vips;

import java.awt.Dimension;
import java.net.URL;

import org.fit.cssbox.layout.Viewport;
import org.fit.pis.PageLoader;

/**
 * VIPS API example application.
 * @author v3s
 *
 */
public class VipsTester {

	/**
	 * Main function
	 * @param args Internet address of web page.
	 */
	public static void main(String args[]) {
	    String urlString, filenameBase, homeDir;
	    URL url;
	    boolean strict = false;
	    int pdoc = 8;
	    Viewport viewport;

		if (args.length < 1) {
			System.err.println("Usage: ./run.sh <url>[ <pdoc>][ strict]");
			System.exit(0);
		}

		urlString = args[0];

		if (args.length > 1) {
		    for (String arg: args) {
		        if (arg.compareTo("strict") == 0) {
		            strict = true;
		        } else {
		            try {
		                pdoc = Integer.parseInt(arg);
		            } catch (NumberFormatException e) {
		                continue;
		            }
		        }
		    }
		}


		try {
		    url = new URL(urlString);
		    PageLoader loader = new PageLoader(urlString);
		    viewport = loader.getViewport(new Dimension(1000, 600));

		    /* If we want to render the loaded page, uncomment the following line */

			Vips vips = new Vips(null);
			VisualStructure result = vips.extractStructure(viewport, pdoc);

			homeDir = System.getProperty("user.home")+"/";
			filenameBase = url.getHost().replaceAll("[^a-zA-Z0-9\\-]+", "-");

			loader.save(homeDir+filenameBase+".png");
			VipsOutput vipsOutput = new VipsOutput(pdoc);
	        vipsOutput.setEscapeOutput(true);
	        vipsOutput.setOutputFileName(homeDir+filenameBase+"-vips-"+pdoc);
	        vipsOutput.writeXML(result, viewport);
	        vipsOutput.drawImage(result, viewport, strict);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
