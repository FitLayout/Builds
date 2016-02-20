//render the page
var srcConfig = {
		width: 1200,
		height: 800
};
srcConfig.url = "http://cssbox.sf.net/";
proc.renderPage('FitLayout.CSSBox', srcConfig);

//segmentation
proc.initAreaTree('FitLayout.Grouping', {});

//apply operators
proc.apply('FitLayout.Segm.CollapseAreas', {});

storage.connect('http://localhost:8080/openrdf-sesame/repositories/user');
