//common project-specific configuration

println("WELCOME!");

entities.clearTaggers();
entities.addTagger(entities.findTagger('FITLayout.Tag.Hour', {}));
entities.addTagger(entities.findTagger('FITLayout.Tag.Minute', {}));
//entities.addTagger(entities.findTagger('FITLayout.Tag.Location', {}));
