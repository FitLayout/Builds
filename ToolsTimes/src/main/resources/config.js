//common project-specific configuration

println("WELCOME!");

entities.clearTaggers();
entities.addTagger(entities.findTagger('FitLayout.Tag.Hour', {}));
entities.addTagger(entities.findTagger('FitLayout.Tag.Minute', {}));
