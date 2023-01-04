# Custom Chat

A very neat way of communicating in game, without using Mojang's services!

## Features

- You can @mention people by typing `@<username>` (CaSe-SeNsiTivE).
- Hide/show status indicator for sneak 100.
- Enable/disable chat history for (bad) good OpSec.
- Change topic/mqtt broker in-game.
- Various configurable verbosity levels.


## Keybinds

- `M`: Open menu
- `C`: Toggle chat bypass
- `V`: Toggle connection status indicator

## Configuration

The configuration file is located at `.minecraft/config/custom_chat-client.toml`.

- general
    - `MQTT Broker = ws://broker.hivemq.com:8000`
      - the address of the MQTT broker you want to connect to.
    - `MQTT Topic = general/chat`
      - the topic you want to subscribe to. 
      - change this to something unique to your server, so you don't see messages from other servers.
      
- chat
   - `Pings = true`
      - set to true if you want to hear a sound when someone @mentions you.
   - `Add message to history = true`
      - self explanatory.
- HUD
   - `Show Connectivity Indicator = true`
      - set to true if you want to see a small indicator in the top left corner of your screen.
      - Red: Not connected, Yellow: Unsubscribed, Green: Subscribed.
- debug
   - `Notifications = false`
      - set to true if you want to see text notifications above your hotbar whenever an action occurs.

## Notes
 - Commands beginning with `/` are ignored.
 - First activation takes a bit longer as initial connection is being estabillished.

---
---
---

Source installation information for modders
-------------------------------------------
This code follows the Minecraft Forge installation methodology. It will apply
some small patches to the vanilla MCP source code, giving you and it access 
to some of the data and functions you need to build a successful mod.

Note also that the patches are built against "un-renamed" MCP source code (aka
SRG Names) - this means that you will not be able to read them directly against
normal code.

Setup Process:
==============================

Step 1: Open your command-line and browse to the folder where you extracted the zip file.

Step 2: You're left with a choice.
If you prefer to use Eclipse:
1. Run the following command: `gradlew genEclipseRuns` (`./gradlew genEclipseRuns` if you are on Mac/Linux)
2. Open Eclipse, Import > Existing Gradle Project > Select Folder 
   or run `gradlew eclipse` to generate the project.

If you prefer to use IntelliJ:
1. Open IDEA, and import project.
2. Select your build.gradle file and have it import.
3. Run the following command: `gradlew genIntellijRuns` (`./gradlew genIntellijRuns` if you are on Mac/Linux)
4. Refresh the Gradle Project in IDEA if required.

If at any point you are missing libraries in your IDE, or you've run into problems you can 
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything 
{this does not affect your code} and then start the process again.

Mapping Names:
=============================
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields 
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license, if you do not agree with it you can change your mapping names to other crowdsourced names in your 
build.gradle. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md

Additional Resources: 
=========================
Community Documentation: http://mcforge.readthedocs.io/en/latest/gettingstarted/  
LexManos' Install Video: https://www.youtube.com/watch?v=8VEdtQLuLO0  
Forge Forum: https://forums.minecraftforge.net/  
Forge Discord: https://discord.gg/UvedJ9m  
