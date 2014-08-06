![](https://raw.githubusercontent.com/SanAndreasP/ClaySoldiersMod/c68469697af8eb163348182866e35ebd62c6fc13/resources/assets/claysoldiers/logo.png)
===============
A Minecraft Mod which adds tiny clay soldiers and additional content for those.

###For Players:
If you experience any bug, issue, crash whilst using this mod or you have any feature request, feel free to open a new issue over here &gt; [SanAndreasP/ClaySoldiersMod/issues](https://github.com/SanAndreasP/ClaySoldiersMod/issues)!<br>
Please label the issue <b style="background-color:#FF0000;color:#FFF;padding: 3px 4px;border-radius:3px;box-shadow:0 -1px 0 rgba(0, 0, 0, 0.12) inset;font-size:11px">bug</b> if you have a bug/crash/any other issue.<br>
Use the label <b style="background-color:#84b6eb;color:#000;padding: 3px 4px;border-radius:3px;box-shadow:0 -1px 0 rgba(0, 0, 0, 0.12) inset;font-size:11px">enchantment</b> if you have a feature request.<br>
The label <b style="background-color:#A000A0;color:#FFF;padding: 3px 4px;border-radius:3px;box-shadow:0 -1px 0 rgba(0, 0, 0, 0.12) inset;font-size:11px">question</b> should be used for questions.

###For Modders:
If you want to add a new feature / possible bugfix to the mod, feel free to fork this repo, make your changes and make a Pull Request.<br>
If you want to develop a new addon for this mod, download the compiled jar file and add it as a library in your workspace (IDE required).<br>
For both, you'll also need to get the Manager Pack Coremod from here &gt; [SanAndreasP/SAPManagerPack](https://github.com/SanAndreasP/SAPManagerPack). Instructions on how to use that are in the README there.

###For Developers
I recommend you use the [ForgeGradleWrapper](https://github.com/SanAndreasP/ForgeGradleWrapper) to setup/build the mod. Use following steps:

1. Run the fGradleW.py file (if you have python 2.7)
2. Choose "[1] setup Forge", use the build which is listed inside the build.gradle under "minecraft->version". If you've already built with the wrapper, but have a different build number, update the workspace
3. Clone the SAPManagerPack repo and put the repo folder (either directly or per symbolic link) inside the src folder of your workspace (like forge/src/SAPManagerPack/)
4. After that, clone this repo and put the repo folder (either directly or per symbolic link) inside the src folder of your workspace (like forge/src/ClaySoldiersMod/)
5. In your IDE, mark the java and resources folder within both repos as Source Folders
  * For Eclipse: Right-click the folder, choose "Build Path", then "Use as Source Folder"
  * For IntelliJ IDEA:  Right-click the folder, choose "Mark Directory As", then "sources Root"
6. To compile the mod:
  1. Clean the build folder (if you have one) in both repos
  2. Run the fGradleH.py file and choose "[3] build mod"
  3. Choose to build the Manager Pack first
  4. After it's done building, build the Clay Soldiers Mod
  5. Copy the SAPManPack-[version].jar and the ClaySoldiersMod-[version].jar, not those suffixed with either -deobf or -src
  6. ???
  7. Zoidberg

Have fun playing :squirrel:
