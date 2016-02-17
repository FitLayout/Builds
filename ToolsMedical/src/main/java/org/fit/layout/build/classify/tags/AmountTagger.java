/**
 * AmountTagger.java
 *
 * Created on 9. 2. 2016, 9:38:26 by burgetr
 */
package org.fit.layout.build.classify.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fit.layout.classify.TextTag;
import org.fit.layout.classify.taggers.BaseTagger;
import org.fit.layout.model.Area;
import org.fit.layout.model.Tag;

/**
 * A tagger that tags discovers the amount information (e.g. 200 mg).
 * @author burgetr
 */
public class AmountTagger extends BaseTagger
{
    protected Pattern[] aexpr = {
            Pattern.compile("[0-9]+\\,[0-9]+[\\s\\xA0]+mg"), //number
            Pattern.compile("[1-9][0-9]*[\\s\\xA0]+mg") //integer
    };
    
    @Override
    public String getId()
    {
        return "Medical.Tag.Amount";
    }

    @Override
    public String getName()
    {
        return "Amounts";
    }

    @Override
    public String getDescription()
    {
        return "A tagger that tags discovers the amount information (e.g. 200 mg).";
    }
    
    @Override
    public TextTag getTag()
    {
        return new TextTag("amount", this);
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
            String text = node.getText();
            for (Pattern p : aexpr)
            {
                if (p.matcher(text).find()) 
                    return true;
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
        List<String> ret = new ArrayList<String>();
        for (Pattern p : aexpr)
        {
            Matcher match = p.matcher(src);
            while (match.find())
            {
                ret.add(match.group());
            }
        }
        return ret;
    }

}
