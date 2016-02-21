/**
 * MinuteTagger.java
 *
 * Created on 20. 2. 2016, 21:22:00 by burgetr
 */
package org.fit.layout.build.times.tags;

import org.fit.layout.classify.TextTag;
import org.fit.layout.model.Area;

/**
 * 
 * @author burgetr
 */
public class MinuteTagger extends NumberTagger
{
    
    public MinuteTagger()
    {
        super(0, 59);
    }

    @Override
    public TextTag getTag()
    {
        return new TextTag("minute", this);
    }

    @Override
    public String getId()
    {
        return "FITLayout.Tag.Minute";
    }

    @Override
    public String getName()
    {
        return "Minutes";
    }

    @Override
    public boolean belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText().trim();
            try {
                int val = Integer.parseInt(text);
                return val >= min && val <= max && text.length() == 2;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

}
