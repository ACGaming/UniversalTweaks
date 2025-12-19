![Available for MC 1.12.2](https://img.shields.io/badge/Available%20for-MC%201.12.2-3498db.svg?labelColor=34495e&style=for-the-badge)
![License LGPL-3.0](https://img.shields.io/github/license/ACGaming/UniversalTweaks?labelColor=34495e&color=3498db&style=for-the-badge)
[![Stars](https://img.shields.io/github/stars/ACGaming/UniversalTweaks?labelColor=34495e&color=3498db&style=for-the-badge)](https://github.com/ACGaming/UniversalTweaks/stargazers)
[![Superseded Mods](https://img.shields.io/badge/Superseded%20Mods-100+-3498db.svg?labelColor=34495e&style=for-the-badge)](https://www.curseforge.com/minecraft/mc-mods/universal-tweaks/relations/dependencies?page=1&type=Incompatible)

# UNIVERSAL TWEAKS

### A one-stop-shop for all bugfixing and tweaking needs

Universal Tweaks consolidates various bugfixes and tweaks into a single solution for Minecraft 1.12.2.

All changes are toggleable via config files.

[![Requires MixinBooter](https://img.shields.io/badge/Requires-MixinBooter-3498db.svg?labelColor=34495e&style=for-the-badge)](https://www.curseforge.com/minecraft/mc-mods/mixin-booter)
[![Requires ConfigAnytime](https://img.shields.io/badge/Requires-ConfigAnytime-3498db.svg?labelColor=34495e&style=for-the-badge)](https://www.curseforge.com/minecraft/mc-mods/configanytime)

![](https://i.imgur.com/1EmHZlb.png)

### **🐞 BUGFIXES**

* **Accurate Smooth Lighting:** Improves the accuracy of smooth lighting by checking for suffocation and light opacity
* **Attack Radius:** Improves the attack radius of hostile mobs by checking the line of sight with raytracing
* **Banner Bounding Box:** Fixes rendering issues with banners by correctly sizing their render bounding boxes
* **Block Fire:** Prevents fire projectiles burning entities when blocking with shields
* **Block Overlay:** Fixes x-ray when standing in non-suffocating blocks
* **Boat Breaking Fall Height:** Sets the required fall height in blocks for boats to break
* **Boat Riding Offset:** Fixes entities glitching through the bottom of boats
* **Chunk Saving:** Fixes loading of outdated chunks to prevent duplications, deletions and data corruption
* **Comparator Timing:** Fixes inconsistent delays of comparators to prevent redstone timing issues
* **Concurrent Entity AI Tasks:** Replaces linked entity AI task sets with concurrent sets to avoid mod exception concerning entity AI
* **Crafted Item Statistics:** Fixes crafted item statistics not increasing correctly when items are crafted with shift-click or drop methods
* **Death Time:** Fixes corrupted entities exceeding the allowed death time
* **Depth Mask:** Fixes entity and particle rendering issues by enabling depth buffer writing
* **Destroy Entity Packets:** Fixes lag caused by dead entities by sending additional packets when the player is not alive
* **Dimension Change Player States:** Fixes missing player states when changing dimensions by sending additional packets
* **Disconnect Dupe:** Fixes item dupes when players are dropping items and disconnecting
* **Double Consumption:** Fixes consuming an item having a chance of also consuming a second item without any animation
* **Donkey/Mule Dupe:** Fixes a duplication exploit connected to the inventories of donkeys and mules
* **Elytra Deployment & Landing:** Relocate elytra deployment and landing to client side to prevent issues with high latencies
* **Entity Bounding Boxes:** Saves entity bounding boxes to tags to prevent breakouts and suffocation
* **Entity Desync:** Fixes entity motion desyncs most notable with arrows and thrown items
* **Entity ID:** Fixes non-functional elytra firework boosting and guardian targeting if the entity ID is 0
* **Entity Lists**
    * **Chunk Updates:** Fixes chunk entity lists often not getting updated correctly
    * **World Additions:** Fixes client-side memory leak where some entity ids are not set before being added to the world's entity list
* **Entity NaN:** Prevents corruption of entities caused by invalid health or damage values
* **Entity Suffocation:** Pushes entities out of blocks when growing up to prevent suffocation
* **Entity Tracker:** Fixes entity tracker to prevent client-sided desyncs when teleporting or changing dimensions
* **Entity UUID:** Changes UUIDs of loaded entities in case their UUIDs are already assigned (and removes log spam)
* **Exhaustion:** Fixes saturation depleting in peaceful mode
* **Extend Item Durability Range:** Changes the durability storing to use ints instead of shorts, eliminating many issues with high-durability items (I.E. TiC tools)
* **Falling Block Entity Damage:** Only damage living entities hit by falling blocks, prevents killing items and XP
* **Faster Background Startup:** Fixes slow background startup edge case caused by checking tooltips during the loading process
* **Fixes Invisible Player when Flying with Elytra:** Fixes the player model occasionally disappearing when flying with elytra in a straight line in third-person mode
* **Frustum Culling:** Fixes invisible chunks in edge cases (small enclosed rooms at chunk borders)
* **Help:** Replaces the help command, sorts and reports broken commands
* **Hopper Bounding Box:** Slims down the hopper bounding box for easier access of nearby blocks
* **Hopper Insert Safety Check:** Prevents crashes when the destination tile entity becomes unavailable during the item insert process
* **Horse Falling:** Modifies falling logic of horses, listening to LivingFallEvent and taking jump boost into account
* **Item Frame Void:** Prevents voiding held items when right + left-clicking on an item frame simultaneously
* **Ladder Flying Slowdown:** Disables climbing movement when flying
* **Locale:** Prevents various crashes with Turkish locale
* **Max Health:** Corrects maximum player health on joining by setting the last saved health value
* **Minecart AI:** Fixes non-player entities being able to control minecarts
* **Mining Glitch:** Prevents ghost blocks by sending additional movement packets
* **Mount Desync:** Fixes mounts and boats sometimes disappearing after dismounting
* **Overlay Message Fade Out:** Fixes Forge's overlay message (action bar) fade out regression
* **Packet Size:** Increases the packet size limit to account for large packets in modded environments
* **Particle Spawning:** Fixes various particle types not showing up on the client
* **Piston Progress:** Properly saves the last state of pistons to tags
* **Piston Retraction:** Improves retraction behavior on double piston extenders
* **Portal Location Link:** Ensures portals link to their original overworld portal if within close distance
* **Portal Traveling Dupe:** Fixes duplication issues that can occur when entities travel through portals
* **Potion Amplifier Visibility:** Fixes potion effects not displaying their level above 'IV'
* **Shear Mooshroom Dupe:** Fixes a duplication exploit connected to shearing mooshrooms
* **Skeleton Aim:** Fixes skeletons not looking at their targets when strafing
* **Sleep Resets Weather:** Fixes sleeping always resetting rain and thunder times
* **Spectator Menu:** Fixes the spectator menu not showing player skins
* **Tile Entity Map:** Replaces the chunk position data table to prevent tile entity related issues
* **Untipped Arrow Particles:** Fixes untipped arrows emitting blue tipped arrow particles upon reloading a world
* **Village Component Parts:** Always builds village structures with biome-specific blocks, improves compatibility with mods such as BiomeTweaker
* **Villager Mantle:** Returns missing hoods to villager mantles
* **Witch Huts:** Fixes witch hut structure data not accounting for the height it is generated at

![](https://i.imgur.com/1EmHZlb.png)

### **🔧 TWEAKS**

* **Accurate Potion Duration:** Always displays the actual potion duration instead of `**:**`
* **Advancement Screenshot:** Silently takes a screenshot every time an advancement is achieved
* **Advancement GUI**
    * Allows increasing the Advancement GUI to scale with the screen size
    * Moves the page buttons to in-line with the rest of the GUI instead of hovering significantly
    * Hides page switching buttons when at the maximum/minimum page count
    * Disables the background fading when hovering over an advancement
    * Adds Advancement Page Title text to the Advancement GUI header
* **Adaptive XP Drops:** Scales dropped experience from entities based on their health
* **AI Improvements:** Replaces/removes entity AI for improved server performance
* **Always Eat:** Allows the consumption of food at any time, regardless of the hunger bar
* **Always Return to Main Menu:** Always returns the player to the main menu when quitting the game
* **Anvil XP Level Cap:** Sets the experience level cap for anvil recipes
* **Armed Armor Stands:** Enables arms for armor stands by default
* **Armor Curve:** Adjusts the armor scaling and degradation formulae for mobs and players
* **Armor Swap:** Enable the ability to swap equipped armor when right-clicking armor, instead of only being able to equip armor when the slot is empty. Also applies to Elytra
* **Attributes:** Sets custom ranges for entity attributes
* **Auto Jump Replacement:** Replaces auto jump with an increased step height (singleplayer only)
* **Auto Save Interval:** Configurable interval in ticks between world auto saves
* **Auto Switch Tools:** Switches the selected hotbar slot to a proper tool if required
* **Bed Obstruction Replacement:** Replaces bed obstruction checks with an improved version
* **Better Burning**
    * Fixes some edge cases where fire damage sources won't cause mobs to drop their cooked items
    * Allows skeletons to shoot flaming arrows when on fire (30% chance * regional difficulty)
    * If entities have fire resistance, they get extinguished right away when on fire
    * Allows fire to spread from entity to entity (30% chance * regional difficulty)
    * Prevents the fire animation overlay from being displayed when the player is immune to fire
* **Better Harvest:** Prevents breaking lower parts of sugar cane and cacti as well as unripe crops, unless sneaking
* **Better Ignition:** Enables ignition of entities by right-clicking instead of awkwardly lighting the block under them
* **Better Ping Display:** Displays the ping in milliseconds of players when viewing the server list
* **Better Placement:** Removes the delay between placing blocks
* **Better Rail Placement:** Implements an improved system that makes rails face the player when placed
* **Block Dispenser:** Allows dispensers to place blocks
* **Block Hit Delay:** Sets the delay in ticks between breaking blocks
* **Boat Speed:** Sets the acceleration value for controlling boats
* **Infinity:**
    * **Bow Infinity Remedy:** Bows enchanted with Infinity no longer require arrows
    * **Mending and Infinity:** Allows the Infinity Enchantment to be combined with Mending
    * **Infinity Affects All Arrows:** Allows the Infinity Enchantment to apply to all arrows (e.g. Tipped Arrows)
* **Breakable Bedrock:** Allows customizable mining of bedrock
* **Broadcast Sounds:** Controls broadcasting of Ender Dragon, End portal creation and Wither sounds
* **Burning Baby Zombies:** Lets baby zombies burn in daylight as in Minecraft 1.13+
* **Burning Skeletons:** Prevents skeletons burning in daylight
* **Burning Zombies:** Prevents zombies burning in daylight
* **Cave Generation:** Sets custom values for the vanilla cave generation
* **Charged Creeper Spawning:** Sets the chance for creepers to spawn charged
* **Chat:**
    * **Chat Lines:** Sets the maximum number of chat lines to display
    * **Compact Messages:** Removes duplicate messages and instead put a number behind the message how often it was repeated
    * **Keep Sent Messages:** Don't clear sent message history on leaving the world
    * **Keep Chat Active on waking:** When waking up from bed, if the chat had text keep the chat window open
* **Check Animated Models:** Improves model load times by checking if an animated model exists before trying to load it
* **Chicken Shedding:** Allows chickens to have a chance to shed feathers (similarly to laying eggs)
* **Chunk Gen Limit:** Limits maximum chunk generation per tick for improved server performance
* **Cobweb Slowness:** Modifies the applied slowness factor when entities are moving in cobwebs
* **Connection Timeouts:** Allows configuring read/login timeouts
    * Helps slow clients log into a server of a large modpack
* **Copy World Seed:** Enables clicking of `/seed` world seed in chat to copy to clipboard
* **Coyote Time Jumping:** Lets the player jump a couple frames after stepping off a ledge, similar to jumping in many platformers
* **Crafting Cache:** Adds an IRecipe cache to improve recipe performance in large modpacks
* **Creeper Confetti:** Replaces deadly creeper explosions with delightful confetti (with a configurable chance)
* **Critical Arrow Damage:** Sets the additional damage that critical arrows deal
* **Custom Rarity:** Sets custom rarities for items, affecting tooltip colors
* **Custom Use Duration:** Sets custom use durations for items like shields, affecting the maximum block time
* **Damage Tilt:** Restores feature to tilt the camera when damaged
* **Damage Velocity:** Enables the modification of damage sources that change the entity's velocity
* **Default Difficulty:** Sets the default difficulty for newly generated worlds
* **Default GUI Text Color:** Sets the default GUI text color (HEX RGB code) which can improve readability in dark mode resource packs
* **Dimension Unload:** Unloads dimensions not in use to free up resources
* **Disable Advancements:** Prevents the advancement system from loading entirely
* **Disable Audio Debug:** Improves loading times by removing debug code for missing sounds and subtitles
* **Disable Creeper Music Discs:** Disables creepers dropping music discs when slain by skeletons
* **Disable Fancy Missing Model:** Improves rendering performance by removing the resource location text on missing models
* **Disable Hotbar Scroll Wrapping:** Disables using the scroll wheel to change hotbar slots wrapping
* **Disable Mob Spawner Entity Render:** Disables rendering an entity inside of Mob Spawners
* **Disable Narrator:** Disables the narrator functionality entirely
* **Disable Rain Particles:** Prevents Rain and Snow Particles from rendering when Raining or Thundering
* **Disable Glint Overlay on Enchantment Books:** Disables the glint overlay on enchantment books
* **Disable Glint Overlay on Potions:** Disables the glint overlay on potions
* **Disable Sleeping:** Disables skipping night by using a bed while making it still able to set spawn
* **Disable Sleeping Setting Spawn:** Disables setting the spawn point by using a bed while making it still able to sleep
* **Disable Text Shadowing:** Disables all text shadowing, where text has a darker version of itself rendered behind the normal text, changing the appearance and can improve fps on some screens
* **Disable Villager Trade Leveling:** Disables leveling of villager careers, only allowing base level trades
* **Disable Villager Trade Restock:** Disables restocking of villager trades, only allowing one trade per offer
* **Disable Wither Targeting AI:** Disables withers targeting animals
* **Display Title:** Determines the title of the game window
* **Easy Breeding:** Enables easy breeding of animals by tossing food on the ground
* **End Crystal Placement:** Allows placing end crystals on more blocks than obsidian or bedrock below
* **End Portal Parallax:** Re-implements parallax rendering of the end portal from 1.11 and older
* **Entity Radius Check**
    * These tweaks are only effective if you have mod(s) that increase `World.MAX_ENTITY_RADIUS`! (Lycanites Mobs, Advanced Rocketry, Immersive Railroading, etc.)
    * Reduces the search size of various AABB functions for specified entity types to improve performance
    * Reduces size of collision checks for most vanilla and specified entity types to improve performance
* **Explosion Block Drop Chance:** Determines the numerator of the block drop formula on explosions
* **Falling Block Lifespan:** Determines how long falling blocks remain in ticks until they are dropped under normal circumstances
* **Fast Dye Blending:** Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry
* **Fast Leaf Decay:** Makes leaves decay faster when trees are chopped
* **Fast Prefix Checking:** Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time
* **Fast World Loading:** Skips garbage collection to speed up world loading
* **Faster Advancement Trigger Checking:** Optimizes triggering advancements when obtaining items
* **Fence/Wall Jump:** Allows the player to jump over fences and walls
* **Finite Water:** Prevents creation of infinite water sources outside of ocean and river biomes
* **First Person Burning Overlay:** Sets the offset for the fire overlay in first person when the player is burning
* **Forge Mod List Improvements:** Improves the Forge mod list GUI by remembering last searches and supporting pipes `|` to look up multiple mods
* **Glass Bottle Consumes Water Source:** Causes Glass Bottles to consume the source block of water
* **Growth Size:** Configurable growth height/length for sugar cane, cacti and vines
* **Hardcore Buckets:** Prevents placing of liquid source blocks in the world
* **Hide Personal Effect Particles:** Disables potion effect particles emitting from yourself
* **Hunger Threshold to Sprint:** Sets the limit of required hunger to be able to sprint
* **Husk & Stray Spawning:** Lets husks and strays spawn underground like regular zombies and skeletons
* **Improve Barrier Particle Display:** Causes Barrier Particles to always be displayed to players in Creative mode
* **Improve Language Switching Speed:** Improves the speed of switching languages in the Language GUI
* **Improve Server Connection Speed:** Improves the speed of connecting to servers by setting the InetAddress host name to the IP in situations where it can be represented as the IP address, preventing getHostFromNameService from being to be run
* **Incurable Potions:** Excludes potion effects from being curable with curative items like buckets of milk
* **Infinite Music:** Lets background music play continuously without delays
* **Item Entities:** Enables the modification of item entity properties
* **LAN Server Properties:** Enhance the vanilla 'Open to LAN' GUI for listening port customization, removal of enforced authentication and more
* **Lenient Paths:** Allows the creation of grass paths everywhere (beneath fence gates, trapdoors, ...)
* **Lightning**
    * **Lightning Damage:** Sets the damage lightning bolts deal to entities
    * **Lightning Fire Ticks:** Sets the duration in ticks lightning bolts set entities on fire
    * **No Lightning Fire:** Disables the creation of fire around lightning strikes
    * **No Lightning Flash:** Disables the flashing of skybox and ground brightness on lightning strikes
    * **No Lightning Item Destruction:** Prevents lightning bolts from destroying items
* **Linear XP Amount:** Sets the amount of XP needed for each level, effectively removing the increasing level scaling
* **Load Sounds:** Plays sounds when the game or the world are loaded
* **Mending Overpowered:** If mending fix is enabled, repairs entire damaged inventory with XP
* **Mending:** Only repairs damaged equipment with XP
* **Minecart Drops Itself:** Replaces vanilla Minecarts dropping a Minecart and the contained item, and instead drop the combined item
* **Mob Despawning Improvement:** Mobs carrying picked up items will despawn properly (and optionally drop their equipment)
* **Modern Knockback:** Backports 1.16+ knockback to 1.12: Knockback resistance is now a scale instead of a probability
* **More Banner Layers:** Sets the amount of applicable pattern layers for banners
* **Music Control:** Enables various music playback control tweaks
* **Mute Advancement Errors:** Silences advancement errors
* **Mute Texture Map Errors:** Silences texture map errors
* **No Attack Cooldown:** Disables the 1.9 combat update attack cooldown
* **No Crafting Repair:** Disables crafting recipes for repairing tools
* **No Enchantment Table Obstruction:** Allows blocks between enchantment tables and bookshelves
* **No Golems:** Disables the manual creation of golems and withers
* **No Leftover Breath Bottles:** Disables dragon's breath from leaving off empty bottles when a stack is brewed with
* **No Night Vision Flash:** Disables the flashing effect when the night vision potion effect is about to run out
* **No Pathfinding Chunk Loading:** Disables mob pathfinding from loading new/unloaded chunks when building chunk caches to improve performance
* **No Potion Shift:** Disables the inventory shift when potion effects are active
* **No Portal Spawning:** Prevents zombie pigmen spawning from nether portals
* **No Redstone Lighting:** Disables lighting of active redstone, repeaters, and comparators to improve performance
* **No Saddled Wandering:** Stops horses wandering around when saddled
* **No Skeleton Trap Spawning:** Prevents skeleton traps spawning during thunderstorms
* **No Smelting XP:** Disables the experience reward when smelting items in furnaces
* **Offhand Improvement:** Prevents placing offhand blocks when blocks or food are held in the mainhand
* **Overhaul Beacon:** Change how beacon construct and range apply per level
* **Overlay Message Height:** Sets the Y value of the overlay message (action bar), displayed for playing records etc.
* **Particle Limit:** Limits particles to a set amount. Should not be set too low, as it will cause particles to appear for a single tick before vanishing
* **Pickup Notification:** Displays highly configurable notifications when the player obtains or loses items
* **Piston Block Blacklist:** Integrates a blacklist of blocks which are not allowed to be pushed by pistons
* **Player Speed:** Enables the modification of base and maximum player speeds along with fixing 'Player moved too quickly' messages
* **Player XP Drop Percentage:** Sets the percentage of experience players can drop upon death instead of the capped ~7 levels
* **Prevent Mob Eggs from Changing Spawners:** Prevents using Mob Spawner Eggs to change what a Spawner is spawning
* **Prevent Observer Activating on Placement:** Controls if the observer activates itself on the first tick when it is placed
* **Prevent Placing Buckets in Portals:** Prevents placing of liquid source blocks overriding portal blocks
* **Projectiles Bounce Off Slime Blocks:** Lets projectiles like arrows bounce off slime blocks
* **Pumpkin Placing:** Allows placing Pumpkins and Jack'O'Lanterns without a supporting block
* **Rabbit Killer Spawning:** Configurable chance for rabbits to spawn as the killer bunny variant
* **Rabbit Toast Spawning:** Configurable chance for rabbits to spawn as the Toast variant
* **Rally Health:** Adds Bloodborne's Rally system to Minecraft, regain lost health when attacking back within the risk time
* **Regen Exhaustion:** Sets the maximum exhaustion value when healing
* **Remove 3D Anaglyph Button:** Removes the 3D Anaglyph button from the video settings menu
* **Remove Realms Button:** Removes the redundant Minecraft Realms button from the main menu and silences notifications
* **Remove Recipe Book:** Removes the recipe book button from GUIs
* **Remove Snooper:** Forcefully turns off the snooper and hides the snooper settings button from the options menu
* **Render End Portal Bottom:** Controls if the End Portal renders its texture on the bottom face
* **Repairable Anvils:** Allows repairing damaged anvils by right-clicking with items like iron ingots
* **Riding Exhaustion:** Enables depleting saturation when riding mounts
* **Sapling Behavior:** Allows customization of sapling behavior while utilizing an optimized method
* **Sea Level:** Sets the default height of the overworld's sea level
* **Selected Item Tooltip Height:** Sets the Y value of the selected item tooltip, displayed when held items are changed
* **Shield Parry:** Allows parrying of projectiles with shields
* **Skip Credits:** Skips the credits screen after the player goes through the end podium portal
* **Skip Missing Registry Entries Screen:** Automatically confirms the 'Missing Registry Entries' screen on world load
* **Sleeping Time:** Determines at which time of day sleeping is allowed in ticks (0 - 24000)
* **Smart Eat:** Requires the hunger bar to be missing food points equal to or more than the amount restored by the food
* **Smooth Scrolling:** Adds smooth scrolling to every in-game list
* **Soulbound Vexes:** Summoned vexes will also die when their summoner is killed
* **Spawn Caps:** Sets maximum spawning limits for different entity types
* **Spawn Chunks Generation:** Determines if overworld spawn chunks are generated during world creation
* **Spawn Chunks Loading:** Determines if overworld spawn chunks are always kept loaded
* **Super Hot Torch:** Enables one-time ignition of entities by hitting them with a torch
* **Stronghold Enforcement:** Enforces stronghold generation to generate all blocks, regardless of air
* **Swing Through Grass:** Allows hitting entities through grass instead of breaking it
* **Texture Atlas Size:** Increases the size of the texture atlas as large as the GPU actually supports, has no effect when [Valkyrie](https://www.curseforge.com/minecraft/mc-mods/valkyrie) is installed
* **Third Person Ignores Non-solid Blocks:** When viewing in third person, don't stop the camera on non-solid blocks
* **Tidy Chunk:** Tidies newly generated chunks by removing scattered item entities
* **Toast Control:** Enables the control of toasts (pop-up text boxes)
* **Toggle Cheats Button:** Adds a button to the pause menu to toggle cheats
* **Trample Control:** Controls how and when farmland is trampled
* **Uncap FPS:** Removes the hardcoded 30 FPS limit in screens like the main menu
* **Undead Horses**
    * **Burning:** Lets untamed undead horses burn in daylight
    * **Taming:** Allows taming of undead horses
* **Unsafe Sleeping**
    * **Highlight Unsafe Mobs:** Apply the Glowing potion effect for a configurable length of time to nearby mobs preventing sleeping
    * **Ignore Unsafe Sleeping:** Allow sleeping even if mobs are nearby
    * **Ignore Named Mobs:** When checking nearby hostile mobs, skip named mobs1
* **Unlimited Sound Pitch Range:** Removes the hardcoded range for sound pitches (0.5-2.0)
* **Use Separate Dismount Key:** Makes the dismount keybind separate from LSHIFT, allowing it to be rebound independently
* **Use Separate Narrator Key:** Allows using a custom Narrator key, instead of being stuck with CTRL+B
* **Village Distance:** Sets the village generation distance in chunks
* **Villager Profession Biome Restriction:** Controls villager professions in specified biomes
* **Void Fog:** Re-implements pre-1.8 void fog and void particles
* **Water Fall Damage:** Re-implements an improved version of pre-1.4 fall damage in water
* **Weaken Golem Structure Requirements:** Allows creating Golem with non-air blocks in the bottom corners of the structure
* **Weaken Wither Structure Requirements:** Allows creating Withers with non-air blocks in the bottom corners of the structure
* **XP Bottle Amount:** Sets the amount of experience spawned by bottles o' enchanting
* **XP Level Cap:** Sets the maximum experience level players can reach
* **View Bobbing Mode:** Sets the view bobbing mode (Default: Bobs both hand and camera (vanilla default); Hand only: Bobs only hand; Camera only: Bobs only camera)
* **Void Teleport:**
    * Options allow toggling the setting globally, controlling to what Y-level the entity is teleported, if blindness is applied, maximum number of consecutive times, and how much and in what way fall damage is taken
    * **Configure Entities:** Configures what entities can be teleported, and if the player is teleported
    * **Configure Dimensions:** Configures what dimensions the effect can take place in
* **Window Icon:** Sets the relative path to the 16x16, 32x32 and 256x256 Minecraft window icons

![](https://i.imgur.com/1EmHZlb.png)

### **⚙️ MOD INTEGRATION**

* **AbyssalCraft**
    * **Disable Plague Potion Clouds:** Disables AbyssalCraft's Plague-type mobs spawning lingering potion effects on death
* **Actually Additions**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Laser Upgrade Voiding:** Fixes Laser Upgrades voiding instead of applying if there is only one item in the stack
    * **Item Laser Particles Graphics:** Sets what level of Graphic Setting is required to disable the Item Particles generated by Item Lasers transferring items
* **Agricraft**
    * **Ender IO Integration Fix:** Fixes crash when Ender IO's Farming Station attempts to harvest Agricraft crops
* **Astral Sorcery**
    * **Missing Player Log Level:** Downgrades the message when completing a recipe without an initializing player from a warning to a debug
    * **Sooty Marble Rendering:** Fixes Sooty Marble Pillar blocking the proper rendering of adjacent fluids due to inverted logic
    * **Clear Particle Effects:** Fixes a bug where particle effects would continue to render after changing dimensions
    * **Fix Division By Zero Crystal Tool:** Fixes a bug where merging Crystal Tool Properties could result in a division by zero
    * **Fix Fluid Vein Overflow:** Fixes a bug where the fluid veins could overflow, resulting in empty veins (just water)
    * **Allow Unlimited Fluid Veins:** Make it so max size fluid veins never run out
    * **Allow to change the Neromantic Prime Extraction Quantity:** Allows configuring the amount extracted from the fluid vein per Neromantic Prime operation
    * **Allow to specify dimensions for fluid veins:** Extends the fluid vein configuration to allow specifying in which dimensions a fluid can generate
* **Advent of Ascension**
    * **Improved Player Tick:** Improves AoA player ticking by only sending inventory changes when necessary
* **Arcane Archives**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Backpacks**
    * **No Offhand Interaction:** Prevents unintended offhand right-click behavior when opening backpacks
* **Bewitchment**
    * **Witches' Oven Fix:** Fixes Witches' Oven consuming container fuel items
* **Bibliocraft**
    * **Disable Version Check:** Fixes client-side memory leak by disabling version check
    * **Fix ItemStack Copying:** Fixes removing an ItemStack not copying all data correctly, particularly for backpacks
* **Binnie's Mods**
    * **Gather Windfall:** Allows Forestry farms to pick up ExtraTrees fruit
* **Biomes O' Plenty**
    * **Hot Spring Water:** Fixes rapid inflection of regeneration effects in hot spring water
* **Blood Magic**
    * **Bound Tool Harvest Tweak:** Improves performance when harvesting blocks with Bound Tool's right-click and exposes block drops to HarvestDropsEvent
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fluid Routing Fix:** Fixes Routing Node fluid routing unable to support multiple liquids and soft-locking when encountering a full fluid tank
    * **Memory Leak Fix:** Fixes memory leak when unloading worlds/switching dimensions
    * **Optimized Hellfire Forge:** Optimizes the Hellfire/Soul Forge to reduce tick time
    * **Ritual Fix:** Fixes ritual resetting on chunk/world unload
* **Botania**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fancy Skybox:** Enables the Botania Garden of Glass skybox for custom dimensions
    * **Alfheim Portal NBT Fix:** Optimize the Alfheim Portal for large quantities of items, preventing chunkbans and chunk corruption. NOTE: Toggling this off after having it on will wipe the contents of existing Alfheim Portals
* **Better with Mods**
    * **Beacon NBT Loading Fix:** Fixes BWM beacons reading null tags from vanilla beacons
* **CB Multipart**
    * **Memory Leak Fix:** Fixes a memory leak associated with EntityPlayer
* **Chisel**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Chocolate Quest Repoured**
    * **Legacy Golden Feather:** Restores the golden feather behavior from the original Better Dungeons mod
* **CodeChicken Lib**
    * **Packet Leak Fix:** Fixes network ByteBuf leaks from PacketCustom
* **CoFH Core**
    * **Multishot Enchantment For Any Bow:** Applies the Multishot enchantment to most bows instead of vanilla and CoFH only
    * **Vorpal Enchantment Damage:** Sets the damage multiplier of the Vorpal enchantment
* **CoFH World**
    * **Prevent Superflat Generation:** Prevents CoFH World features from generating in superflat world types
* **Collective**
    * **Memory Leak Fix:** Fixes memory leak when unloading worlds/switching dimensions
* **Compact Machines**
    * **Allowed Spawns Improvement:** Improves server performance by properly controlling spawn checks (effectiveness depends on CM's config)
    * **Invisible Wall Render Fix:** Fixes some compact machine walls being invisible if [Nothirium](https://www.curseforge.com/minecraft/mc-mods/nothirium) 0.2.x (and up) or [Vintagium](https://github.com/Asek3/sodium-1.12) is installed
    * **Memory Leak Fix:** Fixes client-side memory leak associated with miniaturization recipes
* **Cooking For Blockheads**
    * **Oven Fix:** Fixes CFB Oven consuming container fuel items
* **Corpse**
    * **Opening GUIs Off-thread Fix:** Fixes opening up GUIs on a non-client thread
* **Cyclic**
    * **Memory Leak Fix:** Fixes memory leak associated with advancements when creating FakePlayers
* **Divine RPG**
    * **Change Water Mob Creature Type:** Changes the creature type for DivineRPG Water Mobs to be WATER_CREATURE, fixing issues with hostile mob spawn caps and infinite water mob spawning
    * **Fix Aquamarine Stack Size:** Aquamarine has durability, yet doesn't have a max stack size of 1
    * **Fix Armor Set Effect Cleanup:** Fixes issues with armor set effects being cleaned up under the wrong conditions that caused crashes
    * **Fix Consuming Incorrect Hand:** Fix various DivineRPG items consuming the item in the main hand regardless of the hand actually used
* **Dank Storage**
    * **Max Int stack voiding:** Fixes Max Int (2.1B) stacks being voided when right clicking on them in a Dank
* **Effortless Building**
    * **Block Transmutation Fix:** Fixes Effortless Building ignoring Metadata when checking for items in inventory
* **Electroblob's Wizardry**
    * **Construct's Armory Armor Fix:** Fixes crash when wearing armors from Construct's Armory
* **Elementary Staffs**
    * **Electric Staff Port:** Reintroduces the 1.5 electric staff behavior along with some subtle particles
    * **Health Staff Player Healing:** Lets the health staff also heal other players (and potentially more living entities)
* **Elenai Dodge 2**
    * **Extinguishing Dodges:** Chance per dodge to extinguish the player when burning
    * **Feathers Helper API Fix:** Fixes server-sided crashes when the Feathers Helper API is utilized
    * **Sprinting Integration:** Configurable consumption of feathers when the player is sprinting
* **Ender IO**
    * **Fix Chorus Farming StackOverflow:** Fixes the Farming Station Chorus Walker being able to loop though and check the same positions endlessly, causing a StackOverflow
    * **Fix Soul Binder JEI Appearance:** Fixes the Soul Binder having empty ingredients or displaying filled soul vials in the output slot incorrectly
    * **Replace Obelisk Renderer:** Fixes client-side memory leak by replacing obelisk renderer with a simpler one
    * **Save Filter Cycle Buttons Properly:** Fixes an issue where Cycle Buttons for Damage do not report being clicked when in the Picker Overlay, preventing changing Damage values until clicked again
* **Ender Storage**
    * **Fix Frequency Tracking:** Fixes storage frequencies being tracked multiple times
* **Epic Siege Mod**
    * **Disable Digger AI Debug:** Disables leftover debug logging inside the digger AI of the beta builds
* **Extra Utilities 2**
    * **Catch Radar Exception:** Fixes the Radar feature (find in nearby inventories) entirely breaking when near some inventories
    * **Make Radar skip ungenerated chests:** Makes the Radar skip inventories when the loottable for it has not yet been generated
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fix Deep Dark Stats:** Fixes Mob Attack and Health Statistics being repeatedly doubled
    * **Mutable Machine Block Drops:** Fixes Machine Block drops being immutable, causing a crash on attempting to remove entries from the list.
    * **Creative Mill Harvestability:** Fixes the Creative Mill Generator not respecting the Creative Block Breaking config
    * **Downgrade Potion Recipes Log Level:** Downgrades the message when creating a potion recipe from info to a debug
* **Forestry**
    * **Arborist Villager Trades:** Adds custom emerald to germling trades to the arborist villager
* **FPS Reducer**
    * **Correct FPS Display:** Fixes incorrect FPS number being displayed on the HUD
* **Gaia Dimension**
    * **Fix Restructurer Crash:** Safely access a nullable value when checking recipes in the Restructurer
* **HWYLA**
    * **Keybindings Fix:** Fixes crashes in all menus when changing HWYLA keybindings to unsupported values
* **Immersive Engineering**
    * **Tool Break Fire Event:** Fires the PlayerDestroyItemEvent when an Immersive Engineering tool breaks, fixing a number of cross-compatibility issues
    * **Tool Break Hand Replacement:** Fixes the tool breaking setting the main hand to empty regardless of what hand the tool is in
* **In Control!**
    * **Spawn Rule Stats Fix:** Fixes onJoin spawn rules repeatedly modifying mob attack/health/speed
* **IndustrialCraft 2**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Industrial Foregoing**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Machines Max Range Off-By-One Fix:** Fixes an off-by-one error where IF Machines would display the max tier of range addon as one less than the actual maximum
* **Infernal Mobs**
    * **Better Entity Names:** Gets the actual display names of entities for improved naming
    * **Sticky Recall Compatibility:** Enables compatibility between Infernal Mobs' Sticky effect and Capsule's Recall enchantment
    * **Sticky Pedestal Compatibility:** Enables compatibility between Infernal Mobs' Sticky effect and Reliquary's Pedestal
* **Iron Backpacks**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Iron Chests**
    * **Replace Crystal Chest/Shulker Renderer:** Fixes client-side memory leak by replacing the crystal chest/shulker box renderer with a simpler one (Note: Disables stack size rendering)
* **Item Favorites**
    * **Linux Saving/Loading**: Fixes saving and loading of favorite items on Unix systems
* **Item Stages**
    * **Ingredient Matching:** Changes item matching code to CraftTweaker's ingredient matching system, fixes item NBT issues
* **Jurassic Reborn**
    * **Geneticist Villager House Generation:** Toggles the generation of geneticist houses in villages
* **Mekanism**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fluid Tank Extraction:** Fixes a logic error allowing extracting fluids from fluid tanks regardless of requested fluid
* **MmmMmmMmmMmm**
    * **Copy Armor Stacks to Dummy:** Instead of deleting the original itemstack being equipped, use a copy of it and do not drop armor
* **Moar Tinkers**
    * **Baubles Compatibility:** Enables Energy Eater/Repair to pull from baubles
* **Mob Stages**
    * **Spawning Rules Fixes:** Fixes mob replacement ignoring entity spawning rules
* **Modular Magic**
    * **Fix Null Ingredient:** Fix a Null Pointer Exception in a few places caused by not checking if the ingredient is null before attempting to rendering it
* **Modular Routers**
    * **Particle Thread Fix:** Fixes particles being added from the wrong thread which corrupted the particle manager
* **MrTJPCore**
    * **Memory Leak Fix:** Fixes a memory leak associated with EntityPlayer
* **Nether Chest**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Netherrocks**
    * **Right Click Harvesting Fix:** Prevents crashing with mods implementing right click crop harvesting
* **NuclearCraft**
    * **Radiation Environment Map:** Changes the data table of the radiation environment handler to improve tick time
* **OpenBlocks**
    * **Last Stand Trigger Fix:** Fixes the Last Stand enchantment triggering too early on pre-mitigation damage (before enchants, potions, etc), instead of on post-mitigation damage.
* **ProjectRed**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Proper Pumpkins**
    * **Facing Crash Fix:** Fixes a bug where converting a pumpkin from a non-horizontal face would crash
* **Quark**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fix Untranslated Link Items:** When using the Link Items feature, if playing on a server, items that are not localized serverside will display the lang code in chat. This only impacts servers.
* **Random Things**
    * **Anvil Crafting Fix:** Fixes a bug where crafting the output of an Anvil recipe would modify the recipe, preventing crafts until restart
    * **Fix Spectre Dimension Teleport Stall:** Fix a bug where teleporting to the Spectre dimension on servers can leave the player stalled out in the void
    * **Item Collector Dupe:** Fixes a duplication exploit connected to the Advanced Item Collector
* **Railcraft**
    * **No Beta Warning:** Disables the beta message warning on world join
* **Requious Frakto**
    * **Particle Fixes:** Fixes server world being leaked to various particles
* **RFTools Dimension**
    * **Properly Unregister Dimensions:** Fixes a bug where joining a world or server with any RFTools Dimension registered would disallow entering another world without that dimension until restarting.
* **Roost**
    * **Early Register CT Chickens:** Improves load time by registering ContentTweaker chickens early for Roost to detect them
* **Roots**
    * **Creative Pouch GUI Crash:** Disable opening the Creative Pouch GUI as it immediately crashes
    * **Modifier GUI Voiding:** Fix an issue where disabled modifiers would still consume their material, voiding the 
    * **Icicle Type Saving:** Save the Icicle entity type as NBT, fixing a crash where the type was null
    * **Mortar Spell Dust Fix:** Make the Mortar Spell Dust crafting output a copy of the ItemStack, avoiding issues where the recipe is inadvertently modified
    * **Check Spirit Drop Oredict:** Check if the oredict is registered before adding an entry to the list, preventing a bug when copper or silver ingots or nuggets are disabled
    * **Summon Creatures Ritual Infinite loop:** Make the Summon Creatures Ritual stop when checking if blocks out of world are air, preventing an infinite loop when running the ritual above the void
    * **Prevent Shatter Spell Breaking Unbreakable:** Check if the target block is unbreakable for the Shatter Spell
    * **Disable Element Soil Growth Acceleration:** Disable Elemental Soils triggering updates on redstone updates
* **Simple Difficulty**
    * **Iron Canteen Interaction Fix:** Fixes the interaction of iron canteens with rain collectors
    * **Altitude Modifier:** Sets additional variables for altitude modifier calculations
* **Simply Jetpacks**
    * **Memory Leak Fix:** Fixes a client-side memory leak associated with EntityPlayer
* **Spice Of Life**
    * **Duplication Fixes:** Fixes various duplication exploits
* **SteamWorld**
    * **Sky of Old Dimension Fix:** Fixes a Stack Overflow crash when entering the Sky of Old Dimension
* **Storage Drawers**
    * **Item Voiding Fix:** Prevents voiding of items when near capacity limits
    * **Render Range:** Approximate range in blocks at which drawers render contained items
* **Tardis**
    * **Memory Leak Fix:** Fixes a client-side memory leak associated with EntityPlayer
* **Tech Reborn**
    * **Optimized Rolling Machine:** Optimizes the Rolling Machine to reduce tick time
* **The Erebus**
    * **Disable Death Compass:** Disables granting a death compass upon demise without disabling Block o' Bones
    * **Fix Cabbage Drop:** Fixes Cabbage not dropping the correct items in some situations
    * **Fix Quake Hammer Texture:** Fixes the Quake Hammer using the incorrect config option to control its size
    * **Preserved Blocks Fix:** Prevents HWYLA/TOP crashes with preserved blocks
* **EvilCraft**
    * **Vengeance Spirit Regex Cache:** Cache the result of Vengeance Spirit checks against the config, which may attempt to build and check against hundreds of Regex Patterns every tick
    * **Vengeance Spirit Random Performance:** Avoid repeatedly running intensive calculations involving spawning a random Vengeance Spirit
* **The Farlanders**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Thermal Expansion**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Insolator Custom Monoculture:** Adds Monoculture Cycle integration to desired phytogenic insolator recipes added by ModTweaker
* **Tinkers' Construct**
    * **Disable Rendering Items in Smeltery:** Disables rendering items in the world when they are inside the Smeltery to prevent lag while rendering
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Gaseous Fluids:** Excludes gaseous fluids from being transferable via faucets
    * **Material Blacklist:** Hides tool/bow materials in the 'Materials and You' book
    * **Offhand Shuriken:** Suppresses special abilities of long swords and rapiers when shurikens are wielded in the offhand
    * **Ore Dictionary Cache:** Caches all ore dictionary smelting recipes to speed up game loading
    * **Particle Fixes:** Fixes server world being leaked to various particles
    * **Projectile Despawning:** Despawns unbreakable projectiles faster to improve framerates
    * **Tool Customization:** Sets the attack damage cutoff at which diminishing returns start for any Tinkers' tool and sets the rate at which a tool's attack damage incrementally decays depending on its damage cutoff
* **Tiny Progressions**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Woot**
    * **Cleanup Simulated Kills:** Remove any leftover entities spawned on simulated mob's death
