/**
 * NumberTagger.java
 *
 * Created on 9. 2. 2016, 9:38:26 by burgetr
 */
package org.fit.layout.build.times.tags;

import java.util.ArrayList;
import java.util.List;

import org.fit.layout.classify.taggers.BaseTagger;
import org.fit.layout.model.Area;
import org.fit.layout.model.Tag;

/**
 * 
 * @author burgetr
 */
public abstract class NumberTagger extends BaseTagger
{
    protected int min;
    protected int max;
    
    public NumberTagger(int min, int max)
    {
        super();
        this.min = min;
        this.max = max;
    }

    @Override
    public String getDescription()
    {
        return "Numbers from " + min + " to " + max;
    }
    
    @Override
    public double getRelevance()
    {
        return 0.9;
    }
    
    @Override
    public boolean belongsTo(Area node)
    {
        if (node.isLeaf())
        {
            String text = node.getText().trim();
            try {
                int val = Integer.parseInt(text);
                return val >= min && val <= max;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean allowsContinuation(Area node)
    {
        return false;
    }

    @Override
    public boolean allowsJoining()
    {
        return false;
    }
    
    public boolean mayCoexistWith(Tag other)
    {
        return true;
    }
    
    @Override
    public List<String> extract(String src)
    {
        List<String> ret = new ArrayList<String>(1);
        ret.add(src.trim());
        return ret;
    }

}
