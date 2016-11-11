package org.fit.pis.comp;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.fit.cssbox.layout.ElementBox;
import org.fit.pis.AreaCreator;
import org.fit.pis.AreaProcessor2;
import org.fit.pis.PageArea;
import org.fit.pis.out.ImageOutput;
import org.fit.pis.out.TextOutput;

public class BCSProcessor {
    private AreaCreator creator;
    private Rectangle view;
    private String nameBase;
    private String basePath;
    private ArrayList<PageArea> areas;

    public BCSProcessor(String basePath, String nameBase, Rectangle view, ElementBox rootBox) {
        this.basePath = basePath;
        this.nameBase = nameBase;
        this.view = view;
        this.creator = new AreaCreator((int)view.getWidth(), (int)view.getHeight());
        this.areas = this.creator.getAreas(rootBox);
    }

    public BCSProcessor(String basePath, String nameBase, Rectangle view, ArrayList<PageArea> areas) {
        this.basePath = basePath;
        this.nameBase = nameBase;
        this.view = view;
        this.areas = areas;
    }

    public ArrayList<PageArea> getAreas() {
        return this.areas;
    }

    public void process(double threshold) throws Exception {
        ArrayList<PageArea> groups;
        ArrayList<PageArea> ungrouped;
        AreaProcessor2 h;
        ImageOutput out;
        TextOutput textOut;
        String imageString;

        h = new AreaProcessor2(this.areas, (int)this.view.getWidth(), (int)this.view.getHeight());
        if (threshold > 0) h.setThreshold(threshold);

        groups = h.extractGroups(h.getAreas());
        ungrouped = h.getUngrouped();

        /* For the sake of the right name */
        threshold = h.getThreshold();

        out = new ImageOutput(new Rectangle((int)this.view.getWidth(), (int)this.view.getHeight()), groups, ungrouped);
        out.save(this.basePath+this.nameBase+"-boxes-"+threshold+".png");

        textOut = new TextOutput(groups);
        textOut.save(this.basePath+this.nameBase+"-boxes-"+threshold+".txt");
    }
}
