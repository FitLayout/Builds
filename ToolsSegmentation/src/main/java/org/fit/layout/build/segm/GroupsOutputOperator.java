/**
 * TagEntitiesOperator.java
 *
 * Created on 22. 1. 2015, 16:02:09 by burgetr
 */
package org.fit.layout.build.segm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.fit.layout.api.Parameter;
import org.fit.layout.impl.BaseOperator;
import org.fit.layout.impl.DefaultArea;
import org.fit.layout.impl.ParameterString;
import org.fit.layout.model.Area;
import org.fit.layout.model.AreaTree;
import org.fit.layout.model.Rectangular;

/**
 * 
 * @author burgetr
 */
public class GroupsOutputOperator extends BaseOperator
{
    private String filename;
    
    public GroupsOutputOperator()
    {
        filename = "/tmp/out-groups.txt";
    }
    
    public GroupsOutputOperator(String filename)
    {
        this.filename = filename;
    }
    
    @Override
    public String getId()
    {
        return "FitLayout.Out.Groups";
    }
    
    @Override
    public String getName()
    {
        return "Groups output";
    }

    @Override
    public String getDescription()
    {
        return "...";
    }

    @Override
    public String getCategory()
    {
        return "output";
    }

    @Override
    public List<Parameter> defineParams()
    {
        List<Parameter> ret = new ArrayList<>(1);
        ret.add(new ParameterString("filename"));
        return ret;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    //==============================================================================

    @Override
    public void apply(AreaTree atree)
    {
        apply(atree, atree.getRoot());
    }

    @Override
    public void apply(AreaTree atree, Area root)
    {
        try
        {
            PrintWriter out = new PrintWriter(filename);
            recursiveOutputGroups(root, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //==============================================================================
    
    private void recursiveOutputGroups(Area root, PrintWriter out)
    {
        if (root instanceof DefaultArea && "<area>".equals(((DefaultArea) root).getName()))
        {
            Rectangular b = root.getBounds();
            out.println(b.getX1() + "," + b.getY1() + "," + b.getWidth() + "," + b.getHeight());
        }
        for (Area child : root.getChildAreas())
            recursiveOutputGroups(child, out);
    }
    
}
