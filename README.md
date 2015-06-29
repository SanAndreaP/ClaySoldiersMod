![](https://raw.githubusercontent.com/SanAndreasP/ClaySoldiersMod/c68469697af8eb163348182866e35ebd62c6fc13/resources/assets/claysoldiers/logo.png)
===============
A Minecraft Mod which adds tiny clay soldiers and additional content for those.

###For Players:
####Please read [this](https://github.com/SanAndreasP/ClaySoldiersMod/issues/67) first if you're running a Mac! (or have Java 6)

If you experience any bug, issue, crash whilst using this mod or you have any feature request, feel free to open a new issue over here &gt; [SanAndreasP/ClaySoldiersMod/issues](https://github.com/SanAndreasP/ClaySoldiersMod/issues)!<br>

###For Modders:
If you want to add a new feature / possible bugfix to the mod, feel free to fork this repo, make your changes and make a Pull Request. Read the ***For Developers*** section below on how to get a workspace built.<br>
If you want to develop a new addon for this mod, download the compiled jar file and add it as a library in your workspace (IDE required).<br>
For the latter, you'll also need to get the Manager Pack Coremod from here &gt; [SanAndreasP/SAPManagerPack](https://github.com/SanAndreasP/SAPManagerPack). Instructions on how to use that are in the README there.

###For Developers
Here are the Instructions on how to build a new workspace (for PullRequests / Maintainers) of this repo:
* Fork this repo and download it via a git client (I recommend SourceTree)
* run <tt>gradlew setupDecompWorkspace</tt>
* setup a new workspace in your preferred IDE, here an example for IntelliJ:
  * open IntelliJ and open a new project
  * point to the build.gradle
  * leave the default settings and click "OK"
  * After the project is loaded, open the "Gradle" tab (**may be invisible for you, in which case click on the icon in the bottom left corner and select "Gradle" from there**)
  * Execute the task "genIntelliJRuns" via double-click on it
  * Reload the project
* Done!
* *Please note that we code in Java 7, so you'll need at least the Java Development Kit (JDK) 7 (ver. 8 works as well)! Also make sure your code added to this repo compiles against Java 7 or earlier!*


Have fun playing :squirrel:
