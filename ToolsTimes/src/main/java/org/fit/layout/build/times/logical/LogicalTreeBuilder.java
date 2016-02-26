/**
 * LogicalTreeBuilder.java
 *
 * Created on 21. 2. 2016, 11:45:24 by burgetr
 */
package org.fit.layout.build.times.logical;

import java.util.ArrayList;
import java.util.List;

import org.fit.layout.classify.NodeStyle;
import org.fit.layout.classify.StyleCounter;
import org.fit.layout.impl.BaseLogicalTreeProvider;
import org.fit.layout.impl.DefaultLogicalArea;
import org.fit.layout.impl.DefaultLogicalAreaTree;
import org.fit.layout.impl.DefaultTag;
import org.fit.layout.model.Area;
import org.fit.layout.model.AreaTree;
import org.fit.layout.model.LogicalAreaTree;
import org.fit.layout.model.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author burgetr
 */
public class LogicalTreeBuilder extends BaseLogicalTreeProvider
{
    private static Logger log = LoggerFactory.getLogger(LogicalTreeBuilder.class);
    private static final String TT = "FitLayout.TextTag";
    
	private List<Area> leaves;

	//tags to compare to
	private Tag tminute = new DefaultTag(TT, "minute");
	private Tag thour = new DefaultTag(TT, "hour");
	
	//statistics
	private StyleCounter<NodeStyle> sminute;
	private StyleCounter<NodeStyle> shour;
	
    @Override
    public String getId()
    {
        return "Times.Logical";
    }

    @Override
    public String getName()
    {
        return "Experimental time extraction";
    }

    @Override
    public String getDescription()
    {
        return "";
    }

    @Override
    public String[] getParamNames()
    {
        return new String[0];
    }

    @Override
    public ValueType[] getParamTypes()
    {
        return new ValueType[0];
    }

    //========================================================================================

    @Override
    public LogicalAreaTree createLogicalTree(AreaTree areaTree)
    {
    	leaves = new ArrayList<Area>();
    	sminute = new StyleCounter<NodeStyle>();
    	shour = new StyleCounter<NodeStyle>();
    	findLeaves(areaTree.getRoot(), leaves);

    	System.out.println("hour: " + shour);
    	System.out.println("min: " + sminute);
    	
        DefaultLogicalArea lroot = new DefaultLogicalArea(areaTree.getRoot());
        DefaultLogicalAreaTree ret = new DefaultLogicalAreaTree(areaTree);
        ret.setRoot(lroot);
        return ret;
    }

    //========================================================================================
    
    private void findLeaves(Area root, List<Area> dest)
    {
        if (root.isLeaf())
        {
            dest.add(root);
            
            if (root.hasTag(thour))
            	shour.add(new NodeStyle(root));
            if (root.hasTag(tminute))
            	sminute.add(new NodeStyle(root));
        }
        else
        {
            for (int i = 0; i < root.getChildCount(); i++)
                findLeaves(root.getChildArea(i), dest);
        }
    }

}
