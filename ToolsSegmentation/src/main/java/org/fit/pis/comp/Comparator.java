package org.fit.pis.comp;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import org.fit.cssbox.layout.Viewport;
import org.fit.pis.PageLoader;
import org.fit.pis.out.TextOutput;
import org.xml.sax.SAXException;

public class Comparator {
    /**
     * Produce data that can be used to compare VIPS and BCS
     *
     * This function will perform both VIPS and BCS segmentations multiple
     * times and output all the results required to compare the data.
     *
     * @param basePath Path to a directory where all the output files should be put
     * @param url URL of the web page that is to be parsed. This is only used to derive file names
     * @param view ViewPort representing a loaded page that is to be parsed
     * @throws IOException
     * @throws SAXException
     * @throws Exception
     */
    public static void produceData(String basePath, String nameBase, Viewport view) throws IOException, SAXException, Exception {
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

    /**
     * This is only to test the entry point: produceData()
     *
     * There is another execution point in class Main. That one
     * can be used to fine tune BCS and its parameters, as it can
     * also load previously produced data files and perform the
     * segmentation on the content they represent.
     *
     * @param args
     * @throws Exception
     * @throws SAXException
     * @throws IOException
     */
    public static void main(String [] args) throws IOException, SAXException, Exception {
        String urlString, home, nameBase;
        PageLoader pl;
        Viewport view;
        URL url;

        if (args.length < 1) {
            System.out.println("./comparator-test.sh <address>");
            return;
        }

        urlString = args[0];
        home = System.getProperty("user.home")+"/";
        if (urlString.startsWith("http")) {
            url = new URL(urlString);

            /* PageLoader is being used as it supports the abstraction
             * of boxes and the web page is therefore rendered as a set
             * of rectangles rather than real web page.
             */
            pl = new PageLoader(url);
            view = pl.getViewport(new java.awt.Dimension(1000, 600));

            /* Just an example, any other prefix can be used */
            nameBase = url.getHost().replaceAll("[^a-zA-Z0-9\\-]+", "-");

            /* Store the abstracted image of the processed web page and all the
             * other files*/
            pl.save(home+nameBase+".png");
            Comparator.produceData(home, nameBase, view);
        }
    }
}
