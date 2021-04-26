# Phoneline-Cincinnati-3D
In order to run the game first you must clone this git reposisotory.  
Then you have two options:
1. Use the pre built version:
The prebuilt version contains everything you need to run the game. Simply extract the folder named PhonelineCincinnati_windows.zip and double click the .jar inside.
2. Build the game yourself:
In order to build the game yourself, first open a console inside the reposisotory folder. Type `cd Game`, followed by `./gradlew desktop:dist`. Let the game build. Then in order to run the game successfully, copy all the folders inside `(path)\ce301_carpenter_george_f\Game\core\assets` to a seperate folder, and then copy `(path)\ce301_carpenter_george_f\Game\desktop\build\libs\desktop-1.0.jar` into the same folder you copied the other files into. Rename the .jar to whatever you want. The jar MUST be ran inside this folder to ensure it has access to the assets it needs.
