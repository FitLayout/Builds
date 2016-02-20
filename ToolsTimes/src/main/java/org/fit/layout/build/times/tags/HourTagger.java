/**
 * HourTagger.java
 *
 * Created on 20. 2. 2016, 21:18:36 by burgetr
 */
package org.fit.layout.build.times.tags;

import org.fit.layout.classify.TextTag;

/**
 * 
 * @author burgetr
 */
public class HourTagger extends NumberTagger
{
    
    public HourTagger()
    {
        super(0, 23);
    }

    @Override
    public TextTag getTag()
    {
        return new TextTag("hour", this);
    }

    @Override
    public String getId()
    {
        return "FitLayout.Tag.Hour";
    }

    @Override
    public String getName()
    {
        return "Hours";
    }

}
