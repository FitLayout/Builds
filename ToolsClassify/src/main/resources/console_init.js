//console initialization script

println("Useful commands:");
println("proc.execInternal('create_training_set.js')  -- creates a sample training set containing a few pages");

//connect the storage immediately for testing
storage.connect("sesame:http://localhost:8080/openrdf-sesame/repositories/user");
