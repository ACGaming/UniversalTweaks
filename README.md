# 🔧 Universal Tweaks 🔧

### A one-stop-shop for all bugfixing and tweaking needs

All changes are toggleable via the config file.

**Bugfixes:**

* Block Fire: Prevents fire projectiles burning entities when blocking with shields
* Block Overlay: Fixes x-ray when standing in non-suffocating blocks
* Boat Riding Offset: Fixes entities glitching through the bottom of boats
* Chunk Saving: Fixes loading of outdated chunks to prevent duplications, deletions and data corruption
* Comparator Timing: Fixes inconsistent delays of comparators to prevent redstone timing issues
* Death Time: Fixes corrupted entities exceeding the allowed death time
* Depth Mask: Fixes entity and particle rendering issues by enabling depth buffer writing
* Destroy Entity Packets: Fixes lag caused by dead entities by sending additional packets when the player is not alive
* Dimension Change Player States: Fixes missing player states when changing dimensions by sending additional packets
* Disconnect Dupe: Fixes item dupes when players are dropping items and disconnecting
* Entity Bounding Boxes: Saves entity bounding boxes to tags to prevent breakouts and suffocation
* Entity Desync: Fixes entity motion desyncs most notable with arrows and thrown items
* Entity NaN: Prevents corruption of entities caused by invalid health or damage values
* Entity Suffocation: Pushes entities out of blocks when growing up to prevent suffocation
* Entity Tracker: Fixes entity tracker to prevent client-sided desyncs when teleporting or changing dimensions
* Entity UUID: Changes UUIDs of loaded entities in case their UUIDs are already assigned (and removes log spam)
* Exhaustion: Fixes saturation depleting in peaceful mode
* Faster Background Startup: Fixes slow background startup edge case caused by checking tooltips during the loading process
* Frustum Culling: Fixes invisible chunks in edge cases (small enclosed rooms at chunk borders)
* Help: Replaces the help command, sorts and reports broken commands
* Hopper Bounding Box: Slims down the hopper bounding box for easier access of nearby blocks
* Hopper Insert Safety Check: Prevents crashes when the destination tile entity becomes unavailable during the item insert process
* Item Frame Void: Prevents voiding held items when right + left-clicking on an item frame simultaneously
* Ladder Flying Slowdown: Disables climbing movement when flying
* Locale: Prevents various crashes with Turkish locale
* Max Health: Saves increased player health to tags
* Mining Glitch: Avoids the need for multiple mining attempts by sending additional movement packets
* Model Gap: Fixes transparent gaps in all 3D models of blocks and items
* Piston Progress: Properly saves the last state of pistons to tags
* Skeleton Aim: Fixes skeletons not looking at their targets when strafing
* Tile Entity Update Order: Keeps the order of tile entities on chunk load
* Villager Mantle: Returns missing hoods to villager mantles

**Tweaks:**

* AI Improvements: Replaces/removes entity AI for improved server performance
* Attributes: Sets custom ranges for entity attributes
* Auto Jump Replacement: Replaces auto jump with an increased step height
* Auto Switch Tools: Switches the selected hotbar slot to a proper tool if required
* Bed Obstruction Replacement: Replaces bed obstruction checks with an improved version
* Better Burning
    * Fixes some edge cases where fire damage sources won't cause mobs to drop their cooked items
    * Allows skeletons to shoot flaming arrows when on fire (30% chance * regional difficulty)
    * If entities have fire resistance, they get extinguished right away when on fire
    * Allows fire to spread from entity to entity (30% chance * regional difficulty)
    * Prevents the fire animation overlay from being displayed when the player is immune to fire
* Better Harvest: Prevents breaking lower parts of sugar cane and cacti as well as unripe crops, unless sneaking
* Better Ignition: Enables ignition of entities by right-clicking instead of awkwardly lighting the block under them
* Better Placement: Removes the delay between placing blocks
* Boat Speed: Sets the acceleration value for controlling boats
* Bow Infinity Remedy: Bows enchanted with Infinity no longer require arrows
* Breakable Bedrock: Allows customizable mining of bedrock
* Charged Creeper Spawning Chance: Sets the chance for creepers to spawn charged
* Check Animated Models: Improves model load times by checking if an animated model exists before trying to load it
* Chunk Gen Limit: Limits maximum chunk generation per tick for improved server performance
* Crafting Cache: Adds an IRecipe cache to improve recipe performance in large modpacks
* Creeper Confetti: Replaces deadly creeper explosions with delightful confetti
* Damage Tilt: Restores feature to tilt the camera when damaged
* Dimension Unload: Unloads dimensions not in use to free up resources
* Disable Audio Debug: Improves loading times by removing debug code for missing sounds and subtitles
* Disable Narrator: Disables the narrator functionality entirely
* Disable Sleeping: Disables skipping night by using a bed while making it still able to set spawn
* Easy Breeding: Enables easy breeding of animals by tossing food on the ground
* End Portal Parallax: Re-implements parallax rendering of the end portal from 1.11 and older
* Fast Dye Blending: Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry
* Fast Leaf Decay: Makes leaves decay faster when trees are chopped
* Fast Prefix Checking: Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time
* Fast World Loading: Skips initial world chunk loading & garbage collection to speed up world loading
* Fence/Wall Jump: Allows the player to jump over fences and walls
* Finite Water: Prevents creation of infinite water sources outside of ocean and river biomes
* Hardcore Buckets: Prevents placing of liquid source blocks in the world
* Horizontal Collision Damage: Applies horizontal collision damage to the player akin to elytra collision
* Husk & Stray Spawning: Lets husks and strays spawn underground like regular zombies and skeletons
* Incurable Potions: Excludes potion effects from being curable with curative items like buckets of milk
* Infinite Music: Lets background music play continuously without delays
* Item Entities: Enables the modification of item entity properties
* Linear XP Amount: Sets the amount of XP needed for each level, effectively removing the increasing level scaling
* Load Sounds: Plays sounds when the game or the world are loaded
* Mending Overpowered: If mending fix is enabled, repairs entire damaged inventory with XP
* Mending: Only repairs damaged equipment with XP
* Mob Despawn Improvement: Mobs carrying picked up items will drop their equipment and despawn properly
* No Attack Cooldown: Disables the 1.9 combat update attack cooldown
* No Golems: Disables the manual creation of golems and withers
* No Leftover Breath Bottles: Disables Dragon's Breath from leaving off empty bottles when a stack is brewed with
* No Lightning Flash: Disables the flashing of skybox and ground brightness on lightning strikes
* No Night Vision Flash: Disables the flashing effect when the night vision potion effect is about to run out
* No Potion Shift: Disables the inventory shift when potion effects are active
* No Redstone Lighting: Disables lighting of active redstone, repeaters, and comparators to improve performance
* No Saddled Wandering: Stops horses wandering around when saddled
* Offhand Improvement: Prevents placing offhand blocks when blocks or food are held in the mainhand
* Plantables: Configurable growth height for sugar cane and cacti
* Player Speed: Enables the modification of base and maximum player speeds along with fixing 'Player moved too quickly' messages
* Rally Health: Adds Bloodborne's Rally system to Minecraft, regain lost health when attacking back within the risk time
* Remove Realms Button: Removes the redundant Minecraft Realms button from the main menu
* Remove Recipe Book: Removes the recipe book button from GUIs
* Remove Snooper: Forcefully turns off the snooper and hides the snooper settings button from the options menu
* Shield Parry: Allows parrying of projectiles with shields
* Skip Credits: Skips the credits screen after the player goes through the end podium portal
* Smooth Scrolling: Adds smooth scrolling to every in-game list
* Super Hot Torch: Enables one-time ignition of entities by hitting them with a torch
* Stronghold Replacement: Replaces stronghold generation with a safer variant
* Suppress Tutorial Hints: Suppresses in-game tutorial hint tabs in the right-hand corner
* Swing Through Grass: Allows hitting entities through grass instead of breaking it
* Tidy Chunk: Tidies newly generated chunks by removing scattered item entities
* Toggle Cheats Button: Adds a button to the pause menu to toggle cheats
* Uncap FPS: Removes the hardcoded 30 FPS limit in screens like the main menu
* Water Fall Damage: Re-implements an improved version of pre-1.4 fall damage in water
* XP Bottle Amount: Sets the amount of experience spawned by bottles o' enchanting

**Mod Tweaks:**

* Biomes O' Plenty
    * Hot Spring Water: Fixes rapid inflection of regeneration effects in hot spring water
* Botania
    * Fancy Skybox: Enables the Botania Garden of Glass skybox for custom dimensions
* Epic Siege Mod
    * Disable Digger AI Debug: Disables leftover debug logging inside the digger AI of the beta builds
* Forestry
    * Arborist Villager Trades: Adds custom emerald to germling trades to the arborist villager
    * Disable Bee Damage Armor Bypass: Disables damage caused by bees bypassing player armor
* Mo' Creatures
    * Custom Modded Biomes: Adds support for modded biome spawns via config
* Roost
    * Early Register CT Chickens: Improves load time by registering CT chickens early for Roost to detect them
* Storage Drawers
    * Item Handlers:
        * Fixes voiding of items when nearing full capacity
        * Fixes slotless item handler implementation not allowing the extraction from compacting item drawers with the vending upgrade
        * Caches the drawer controller tile to avoid getting the TE from the world every time a drawer slave is interacted with
    * Render Range: Approximate range in blocks at which drawers render contained items
* Thaumcraft
    * Firebat Particles: Adds particles to firebats similar to legacy versions
    * Flower Bounding Box: Fixes the bounding box always being at the center in both cinderpearls and shimmerleafs
    * Stable Thaumometer: Stops the thaumometer from bobbing rapidly when using it to scan objects
    * Wisp Particles: Increases particle size of wisps similar to legacy versions
* Tinkers' Construct
    * Gaseous Fluids: Excludes gaseous fluids from being transferable via faucets
    * Offhand Shuriken: Suppresses special abilities of long swords and rapiers when shurikens are wielded in the offhand
    * Projectile Despawning: Despawns unbreakable projectiles faster to improve framerates
    * Ore Dictionary Cache: Caches all ore dictionary smelting recipes to speed up game loading