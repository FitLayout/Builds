//default operators and their parametres
//used for the initial configuration of the GUIProcessor
//reference from the default_segm.js script as the default operators on the created tree

proc.apply('FitLayout.Segm.FlattenTree', {});
//proc.apply('FitLayout.Segm.CollapseAreas', {});
//proc.apply('FitLayout.Segm.SortByLines', {});
//proc.apply('FitLayout.Segm.FindLines', {useConsistentStyle: true, maxLineEmSpace: 1.5});
//proc.apply('FitLayout.Segm.MultiLine', {useConsistentStyle: true, maxLineEmSpace: 0.5});
//proc.apply('Ceur.Tag.Class', {});
//proc.apply('FitLayout.Segm.HomogeneousLeaves', {});
//proc.apply('FitLayout.Segm.GroupByDOM', {});
//proc.apply('FitLayout.Segm.SuperAreas', {depthLimit: 1});
proc.apply('FitLayout.Segm.BCS', {CT: 0.3, calcImgColor: true});

//proc.apply('FitLayout.Tools.XMLOutput', {filename: "/tmp/out.xml"});
//proc.apply('FitLayout.Tools.HTMLOutput', {filename: "/tmp/out.html"});
