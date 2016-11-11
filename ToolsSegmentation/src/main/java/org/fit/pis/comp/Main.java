package org.fit.pis.comp;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.fit.cssbox.layout.Viewport;
import org.fit.pis.PageArea;
import org.fit.pis.PageLoader;
import org.fit.pis.in.FileLoader;
import org.fit.pis.out.TextOutput;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws IOException, SAXException, Exception {
        String urlString, home, nameBase;
        PageLoader pl;
        FileLoader fl;
        Viewport view;
        URL url;
        BCSProcessor bcs = null;
        VIPSProcessor vips = null;
        Rectangle r;
        ArrayList<PageArea> areas;

        if (args.length < 1) {
            System.out.println("./run.sh <address>");
            return;
        }

        urlString = args[0];
        home = System.getProperty("user.home")+"/";
        if (urlString.startsWith("http")) {
            url = new URL(urlString);

            pl = new PageLoader(url);
            view = pl.getViewport(new java.awt.Dimension(1000, 600));
            nameBase = url.getHost().replaceAll("[^a-zA-Z0-9\\-]+", "-");
            pl.save(home+nameBase+".png");

            r = new Rectangle(view.getWidth(), view.getHeight());
            bcs = new BCSProcessor(home, nameBase, r, view.getRootBox());
            vips = new VIPSProcessor(home, nameBase, view);
            (new TextOutput(bcs.getAreas())).save(home+nameBase+"-boxes.txt");
        } else {
            nameBase = urlString.substring(urlString.lastIndexOf('/'), urlString.lastIndexOf("-boxes.txt"));
            fl = new FileLoader(urlString);
            fl.save(home+nameBase+".png");

            r = fl.getPageDimensions();
            areas = fl.getAreas();
            bcs = new BCSProcessor(home, nameBase, r, areas);
        }

        for (int i = 1; i <= 10; i++) {
            bcs.process(i/10.0);
            if (vips != null) vips.process(i);
        }
    }
}
