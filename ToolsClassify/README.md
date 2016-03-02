Content Classification Based on Visual Features using FITLayout 
===============================================================
(c) 2016 Radek Burget (burgetr@fit.vutbr.cz)

This is an experimental FITLayout build that shows the usage of FITLayout for the content classification
based on visual features. The assumed usage scenario is the following:

- The training data is prepared by manual annotation of a training set of documents (pages) where the important content
parts are marked with a set of *tags*. The training documents are stored in a persistent RDF storage. Multiple 
different training sets may be created.
- The stored annotated documents are used for training a classifier.
- The training classifier is used for automatic tagging of new, previously unseen documents. 

The FITLayout provides support for all the above mentioned tasks. This support includes:

- Page rendering and its representaion by an abstract *Area tree*.
- A graphical user interface with an Annotator plugin that allows to manually assign tags to the individual
visual areas in the page.
- A persistent RDF storage connection that is used for storing the annotated pages. The storage is organized
in *Page sets* that represent the individual training sets of pages.
- A general classification framework that includes customizable extraction of visual features from the documents
and using the extracted features as input data for a selected classifier.
- A JavaScript console that may be used for task automation.

For further information about the FITLayout framework concepts and architecture, it is also recommended to take
a look at the [FITLayout manual](http://www.fit.vutbr.cz/~burgetr/FITLayout/manual/).

Requirements and Installation
-----------------------------
FITLayout is an experimental sotware that currectly evolves fast. Therefore, the preferred way of installation
is cloning the GitHub repositories and compiling the framework maually. All the repositories include the project
metadata for [Eclipse](http://www.eclipse.org) that allows easy import of the project to the Eclipse IDE. However,
any other IDE may be used as well, all the project use Maven as the default build tool.

### Required Projects
The Classification build depends on the following projects available on GitHub:

#### Page rendering dependencies
- [CSSBox](https://github.com/radkovo/CSSBox) An experimental HTML/CSS rendering engine used for rendering the input pages.
- [jStyleParser](https://github.com/radkovo/jStyleParser) A CSS parser required by CSSBox.
- [Pdf2Dom](https://github.com/radkovo/Pdf2Dom) A PDF parser interface for processing PDF documents on input.

#### FITLayout modules
- [api](https://github.com/FitLayout/api) FITLayout core and APIs.
- [classify](https://github.com/FitLayout/classify) Classification frameworks.
- [layout-cssbox](https://github.com/FitLayout/layout-cssbox) CSSBox bindings for page rendering.
- [logical](https://github.com/FitLayout/logical) Logical tree creation (currently not relevant for the classification task).
- [segmentation](https://github.com/FitLayout/segmentation) Area tree creation and page segmentation algorithms.
- [storage-rdf](https://github.com/FitLayout/storage-rdf) RDF storage bindings.
- [tools](https://github.com/FitLayout/tools) GUI tools and the scripting console.

Note that some of the projects introduce their own dependencies that may be automatically obtained from Maven.

Running the Tools
-----------------
This build provides two executable classes: the [BlockBrowserClassify](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/java/org/fit/layout/build/classify/BlockBrowserClassify.java)
which is the GUI browser and [ConsoleClassify](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/java/org/fit/layout/build/classify/ConsoleClassify.java) 
that provides the JavaScript command line. After installing all the above dependencies, you should be able to run these
tools.

RDF Storage Setup
-----------------
Current RDF storage implementation requires a [Sesame RDF storage](http://rdf4j.org/) accessible through HTTP 
(e.g. on `http://localhost:8080/openrdf-sesame`). Current code has been tested with Sesame 2.8.x. After the Sesame
server has been installed, a new repository should be created for storing the FITLayout data. The repositories are
identified by their names (e.g. `user`). The [Sesame Workbench](http://rdf4j.org/sesame/2.8/docs/using+sesame.docbook?view#Sesame_Workbench)
may be used for creating the new repository. The complete repository URL expected by FITLayout is then
`sesame:http://localhost:8080/openrdf-sesame/repositories/user`.

In the Browser GUI, the repository may be connected using the **RDF Storage** tab. In the JavaScript console
the repository is connected using the `storage.connect(url)` command. By default, the repository is connected
in the console init script [console_init.js](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/resources/console_init.js).

The stored pages are organized in *Page sets*. For saving the page, at least one page set must be created in the storage.
Then, the currently displayed page may be stored using the `Insert new` button. When the page has been already stored,
it may be updated using the `Update` button.


Page Rendering and Annotation
-----------------------------

### Annotation using the Browser GUI

In the Browser GUI, the source page may be loaded by providing its URL in the **Sources** tab. Then, the area tree must
be created using the `Segmentator` (just press `Run` in the Segmentator tool bar). The segmentation process may include
various page preprocessing steps using customizable *operators*. See the `Operators` menu for available operators and
their configuration.

The **Annotator** tab is used for the page annotation by assigning tags to the detected visual areas. First, the area is
selected by clicking the corresponding part of the displayed page or by selecting the area in the Area tree on the left.
Then the corresponding tag is selected through the Annotator GUI and it gets assigned by pressing the `Assign` button.
Finally, the page may be saved to the RDF storage using the controls in the **RDF Storage** tab.

When some page set id selected in the **RDF Storage** tab, the contained pages may be quickly browsed and updated by
the corresponding controls in the **Annotator** tab in the bottom right corner. This allows to quickly browse and review the
annotated data set without switching between tabs.

### Page Batch Download using the Console

Using the JavaScript console, it is possible to automate the page downloading, pre-processing and storing in the RDF
storage. The example [create_training_set.js](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/resources/create_training_set.js)
script downloads a set of pages, applies some standard pre-processing steps and creates a `Training` page set in the
RDF storage that contains the downloaded pages. Then, it is possible to browse and annotate the training set using
the Browser GUI as described above.

Classification
--------------
The basic idea of the classification approach is that the important parts of the content may be recognized by their
visual (and other properties). FITLayout allows to customize the actual set of features that are used for this purpose.
The features and the way of their computation is specified by implementing a 
[FeatureExtractor](https://github.com/FitLayout/classify/blob/master/src/main/java/org/fit/layout/classify/FeatureExtractor.java).
In this build we provide an example [SimpleFeatureExtractor](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/java/org/fit/layout/build/classify/SimpleFeatureExtractor.java)
that is based on the font properties, text properties and some more simple features.

Currently the training data is represented using the [WEKA](http://www.cs.waikato.ac.nz/ml/weka/documentation.html) data
structures such as [Instance](http://weka.sourceforge.net/doc.dev/weka/core/Instance.html). The expected structure
of the data instances is specified in the [header.arff](https://github.com/FitLayout/Builds/blob/master/ToolsClassify/src/main/resources/header.arff)
file. This structure (i.e. the number of extracted values and their type) must be consistent with the used feature extractor.

### Training Dataset Creation

The [extract_training_data.js] script gives an example of creating a training data set from the annotated set of pages.
It takes the pages in the `Training` page set, extracts the features of every visual area in these pages using the
feature extractor and saves the extracted data set to a local file using in the ARFF format. This file may be later
used for training any WEKA classifier.

### Classification of New Pages

The trained classifier may be used for an automatic annotation of new, previously unseen pages. In the Browser GUI
the classification is implemented as an AreaTree operator and it may be configured in the `Operators` configuration
dialog. The corresponding operator is called `Tag visual classes (FitLayout.Tag.Visual)`. It is initialized by the
training ARFF file generated in the previous step.

When the page segmentation is executed (using the Segmentator `Run`) button in the **Sources** tab, the operator
automatically assigns the tags to the areas by applying the trained classifier on the visual features of the individual
visual areas in the new page.

The actual implementation of the classifier may be found in the [VisualClassifier](https://github.com/FitLayout/classify/blob/master/src/main/java/org/fit/layout/classify/VisualClassifier.java)
class.
