package org.fit.pis.comp;

import org.fit.cssbox.layout.Viewport;
import org.fit.vips.Vips;
import org.fit.vips.VipsOutput;
import org.fit.vips.VisualStructure;

public class VIPSProcessor {
    private Vips vips;
    Viewport view;
    private String nameBase;
    private String basePath;

    public VIPSProcessor(String basePath, String nameBase, Viewport view) {
        this.basePath = basePath;
        this.nameBase = nameBase;
        this.view = view;
        this.vips = new Vips(null);
    }

    public void process(int pdoc) {
        VisualStructure result;

        result = vips.extractStructure(this.view, pdoc);

        VipsOutput vipsOutput = new VipsOutput(pdoc);
        vipsOutput.setEscapeOutput(true);
        vipsOutput.setOutputFileName(this.basePath+this.nameBase+"-vips-"+pdoc);
        vipsOutput.writeXML(result, this.view);
        vipsOutput.drawImage(result, this.view, false);
    }
}
