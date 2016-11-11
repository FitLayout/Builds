/*
 * Tomas Popela, xpopel11, 2012
 * VIPS - Visual Internet Page Segmentation
 * Module - Vips.java
 */

package org.fit.vips;

import org.fit.cssbox.layout.Viewport;

/**
 * Vision-based Page Segmentation algorithm
 *
 * @author Tomas Popela
 *
 */
public class Vips {
    private String outputFolder;

    public Vips(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getDetailRendering() {
        return this.outputFolder;
    }

    public VisualStructure extractStructure(Viewport viewport, int pDoC) {
        long startTime, endTime;
        int numberOfIterations = 10;
        int pageWidth = viewport.getWidth();
        int pageHeight = viewport.getHeight();
        int sizeTresholdWidth = 350;
        int sizeTresholdHeight = 400;
        VisualStructure result;

        if (pDoC <= 0 || pDoC > 11) {
            System.err.println("pDoC value must be between 1 and 11! Not " + pDoC + "!");
            return null;
        }

        startTime = System.nanoTime();


        VipsSeparatorGraphicsDetector detector;
        VipsParser vipsParser = new VipsParser(viewport);
        VisualStructureConstructor constructor = new VisualStructureConstructor(pDoC);
        constructor.setGraphicsOutput(this.outputFolder != null);

        for (int iterationNumber = 1; iterationNumber < numberOfIterations
                + 1; iterationNumber++) {
            detector = new VipsSeparatorGraphicsDetector(pageWidth, pageHeight);

            // visual blocks detection
            vipsParser.setSizeTresholdHeight(sizeTresholdHeight);
            vipsParser.setSizeTresholdWidth(sizeTresholdWidth);

            vipsParser.parse();

            VipsBlock vipsBlocks = vipsParser.getVipsBlocks();

            if (iterationNumber == 1) {
                if (outputFolder != null) {
                    // in first round we'll export global separators
                    detector.setVipsBlock(vipsBlocks);
                    detector.fillPool();
                    detector.saveToImage("blocks" + iterationNumber);
                    detector.setCleanUpSeparators(0);
                    detector.detectHorizontalSeparators();
                    detector.detectVerticalSeparators();
                    detector.exportHorizontalSeparatorsToImage();
                    detector.exportVerticalSeparatorsToImage();
                    detector.exportAllToImage();
                }

                // visual structure construction
                constructor.setVipsBlocks(vipsBlocks);
                constructor.setPageSize(pageWidth, pageHeight);
            } else {
                vipsBlocks = vipsParser.getVipsBlocks();
                constructor.updateVipsBlocks(vipsBlocks);

                if (outputFolder != null) {
                    detector.setVisualBlocks(constructor.getVisualBlocks());
                    detector.fillPool();
                    detector.saveToImage("blocks" + iterationNumber);
                }
            }

            // visual structure construction
            constructor.constructVisualStructure();

            // prepare tresholds for next iteration
            if (iterationNumber <= 5) {
                sizeTresholdHeight -= 50;
                sizeTresholdWidth -= 50;

            }
            if (iterationNumber == 6) {
                sizeTresholdHeight = 100;
                sizeTresholdWidth = 100;
            }
            if (iterationNumber == 7) {
                sizeTresholdHeight = 80;
                sizeTresholdWidth = 80;
            }
            if (iterationNumber == 8) {
                sizeTresholdHeight = 40;
                sizeTresholdWidth = 10;
            }
            if (iterationNumber == 9) {
                sizeTresholdHeight = 1;
                sizeTresholdWidth = 1;
            }

        }

        // constructor.normalizeSeparatorsSoftMax();
        constructor.normalizeSeparatorsMinMax();

        result = constructor.getVisualStructure();

        endTime = System.nanoTime();

        long diff = endTime - startTime;

        System.out.println((diff / 1000000.0) + " ms");

        return result;
    }
}
