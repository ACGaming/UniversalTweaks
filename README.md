# 🔧 Universal Tweaks 🔧
### A one-stop-shop for all bugfixing and tweaking needs

All changes are toggleable via the config file.

**Bugfixes:**
* Block Overlay: Fixes x-ray when standing in non-suffocating blocks
* Destroy Entity Packets: Fixes lag caused by dead entities by sending additional packets when the player is not alive
* Dimension Change Player States: Fixes missing player states when changing dimensions by sending additional packets
* Entity Bounding Boxes: Saves entity bounding boxes to tags to prevent breakouts and suffocation
* Entity Suffocation: Pushes entities out of blocks when growing up to prevent suffocation
* Entity Tracker: Fixes entity tracker to prevent client-sided desyncs when teleporting or changing dimensions
* Frustum Culling: Fixes invisible chunks in edge cases (small enclosed rooms at chunk borders)
* Hash Code: Reduces CPU overhead caused by incorrect hash code
* Help: Replaces the help command, sorts and reports broken commands
* Ladder Flying Slowdown: Disables climbing movement when flying
* Locale: Prevents various crashes with Turkish locale
* Max Health: Saves increased player health to tags
* Mining Glitch: Avoids the need for multiple mining attempts by sending additional movement packets
* Piston Progress: Properly saves the last state of pistons to tags
* Skeleton Aim: Fixes skeletons not looking at their targets when strafing
* Tile Entity Update Order: Keeps the order of tile entities on chunk load

**Tweaks:**
* Attributes Uncap: Virtually uncaps entity attributes
* Auto Jump Replacement: Replaces auto jump with an increased step height
* Auto Switch Tools: Switches the selected hotbar slot to a proper tool if required
* Bed Obstruction Replacement: Replaces bed obstruction checks with an improved version
* Bow Infinity Remedy: Bows enchanted with Infinity no longer require arrows
* Disable Animated Models: Improves model load times by removing Forge's animated models
* Disable Audio Debug: Improves loading times by removing debug code for missing sounds and subtitles
* Fast Dye Blending: Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry
* Fast Leaf Decay: Makes leaves decay faster when trees are chopped
* Fast Prefix Checking: Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time
* Mending: Only repairs damaged equipment with XP
* Mending Overpowered: If mending fix is enabled, repairs entire damaged inventory with XP
* Item Entity Combination: Stops combination of item entities if their maximum stack size is reached
* Mob Despawn Improvement: Mobs carrying picked up items will drop their equipment and despawn properly
* Offhand Improvement: Prevents placing offhand blocks when blocks or food are held in the mainhand
* Remove Recipe Book: Removes the recipe book button from GUIs
* Stronghold Replacement: Replaces stronghold generation with a safer variant
* Water Fall Damage: Re-implements an improved version of pre-1.4 fall damage in water