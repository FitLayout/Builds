/**
 * LogicalTreeBuilder.java
 *
 * Created on 21. 2. 2016, 11:45:24 by burgetr
 */
package org.fit.layout.build.times.logical;

import org.fit.layout.impl.BaseLogicalTreeProvider;
import org.fit.layout.impl.DefaultLogicalArea;
import org.fit.layout.impl.DefaultLogicalAreaTree;
import org.fit.layout.model.AreaTree;
import org.fit.layout.model.LogicalAreaTree;

/**
 * 
 * @author burgetr
 */
public class LogicalTreeBuilder extends BaseLogicalTreeProvider
{

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
        
        DefaultLogicalArea lroot = new DefaultLogicalArea(areaTree.getRoot());
        DefaultLogicalAreaTree ret = new DefaultLogicalAreaTree(areaTree);
        ret.setRoot(lroot);
        return ret;
    }

}
