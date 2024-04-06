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
* **Entity Lists:** Fixes entity lists often not getting updated correctly
* **Entity NaN:** Prevents corruption of entities caused by invalid health or damage values
* **Entity Suffocation:** Pushes entities out of blocks when growing up to prevent suffocation
* **Entity Tracker:** Fixes entity tracker to prevent client-sided desyncs when teleporting or changing dimensions
* **Entity UUID:** Changes UUIDs of loaded entities in case their UUIDs are already assigned (and removes log spam)
* **Exhaustion:** Fixes saturation depleting in peaceful mode
* **Falling Block Entity Damage:** Only damage living entities hit by falling blocks, prevents killing items and XP
* **Faster Background Startup:** Fixes slow background startup edge case caused by checking tooltips during the loading process
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
* **Model Gap:** Fixes transparent gaps in all 3D models of blocks and items
* **Mount Desync:** Fixes mounts and boats sometimes disappearing after dismounting
* **Packet Size:** Increases the packet size limit to account for large packets in modded environments
* **Particle Spawning:** Fixes various particle types not showing up on the client
* **Piston Progress:** Properly saves the last state of pistons to tags
* **Piston Retraction:** Improves retraction behavior on double piston extenders
* **Portal Traveling Dupe:** Fixes duplication issues that can occur when entities travel through portals
* **Potion Amplifier Visibility:** Fixes potion effects not displaying their level above 'IV'
* **Shear Mooshroom Dupe:** Fixes a duplication exploit connected to shearing mooshrooms
* **Skeleton Aim:** Fixes skeletons not looking at their targets when strafing
* **Sleep Resets Weather:** Fixes sleeping always resetting rain and thunder times
* **Spectator Menu:** Fixes the spectator menu not showing player skins
* **Tile Entity Map:** Replaces the chunk position data table to prevent tile entity related issues
* **Untipped Arrow Particles:** Fixes untipped arrows emitting blue tipped arrow particles upon reloading a world
* **Villager Mantle:** Returns missing hoods to villager mantles
* **Witch Huts:** Fixes witch hut structure data not accounting for the height it is generated at

![](https://i.imgur.com/1EmHZlb.png)

### **🔧 TWEAKS**

* **Accurate Potion Duration:** Always displays the actual potion duration instead of `**:**`
* **Adaptive XP Drops:** Scales dropped experience from entities based on their health
* **AI Improvements:** Replaces/removes entity AI for improved server performance
* **Always Eat:** Allows the consumption of food at any time, regardless of the hunger bar
* **Armed Armor Stands:** Enables arms for armor stands by default
* **Armor Curve:** Adjusts the armor scaling and degradation formulae for mobs and players
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
* **Better Placement:** Removes the delay between placing blocks
* **Block Dispenser:** Allows dispensers to place blocks
* **Block Hit Delay:** Sets the delay in ticks between breaking blocks
* **Boat Speed:** Sets the acceleration value for controlling boats
* **Bow Infinity Remedy:** Bows enchanted with Infinity no longer require arrows
* **Breakable Bedrock:** Allows customizable mining of bedrock
* **Burning Baby Zombies:** Lets baby zombies burn in daylight as in Minecraft 1.13+
* **Charged Creeper Spawning:** Sets the chance for creepers to spawn charged
* **Check Animated Models:** Improves model load times by checking if an animated model exists before trying to load it
* **Chicken Shedding:** Allows chickens to have a chance to shed feathers (similarly to laying eggs)
* **Chunk Gen Limit:** Limits maximum chunk generation per tick for improved server performance
* **Cobweb Slowness:** Modifies the applied slowness factor when entities are moving in cobwebs
* **Compact Messages:** Removes duplicate messages and instead put a number behind the message how often it was repeated
* **Copy World Seed:** Enables clicking of `/seed` world seed in chat to copy to clipboard
* **Crafting Cache:** Adds an IRecipe cache to improve recipe performance in large modpacks
* **Creeper Confetti:** Replaces deadly creeper explosions with delightful confetti (with a configurable chance)
* **Critical Arrow Damage:** Sets the additional damage that critical arrows deal
* **Custom Rarity:** Sets custom rarities for items, affecting tooltip colors
* **Custom Use Duration:** Sets custom use durations for items like shields, affecting the maximum block time
* **Damage Tilt:** Restores feature to tilt the camera when damaged
* **Damage Velocity:** Enables the modification of damage sources that change the entity's velocity
* **Default Difficulty:** Sets the default difficulty for newly generated worlds
* **Dimension Unload:** Unloads dimensions not in use to free up resources
* **Disable Advancements:** Prevents the advancement system from loading entirely
* **Disable Audio Debug:** Improves loading times by removing debug code for missing sounds and subtitles
* **Disable Creeper Music Discs:** Disables creepers dropping music discs when slain by skeletons
* **Disable Fancy Missing Model:** Improves rendering performance by removing the resource location text on missing models
* **Disable Narrator:** Disables the narrator functionality entirely
* **Disable Sleeping:** Disables skipping night by using a bed while making it still able to set spawn
* **Disable Villager Trade Leveling:** Disables leveling of villager careers, only allowing base level trades
* **Disable Villager Trade Restock:** Disables restocking of villager trades, only allowing one trade per offer
* **Disable Wither Targeting AI:** Disables withers targeting animals
* **Easy Breeding:** Enables easy breeding of animals by tossing food on the ground
* **End Portal Parallax:** Re-implements parallax rendering of the end portal from 1.11 and older
* **Explosion Block Drop Chance:** Determines the numerator of the block drop formula on explosions
* **Falling Block Lifespan:** Determines how long falling blocks remain in ticks until they are dropped under normal circumstances
* **Fast Dye Blending:** Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry
* **Fast Leaf Decay:** Makes leaves decay faster when trees are chopped
* **Fast Prefix Checking:** Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time
* **Fast World Loading:** Skips initial world chunk loading & garbage collection to speed up world loading
* **Fence/Wall Jump:** Allows the player to jump over fences and walls
* **Finite Water:** Prevents creation of infinite water sources outside of ocean and river biomes
* **First Person Burning Overlay:** Sets the offset for the fire overlay in first person when the player is burning
* **Growth Size:** Configurable growth height/length for sugar cane, cacti and vines
* **Hardcore Buckets:** Prevents placing of liquid source blocks in the world
* **Horizontal Collision Damage:** Applies horizontal collision damage to the player akin to elytra collision
* **Husk & Stray Spawning:** Lets husks and strays spawn underground like regular zombies and skeletons
* **Improved Entity Tracker Warning:** Provides more information to addPacket removed entity warnings
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
* **Mob Despawn Improvement:** Mobs carrying picked up items will drop their equipment and despawn properly
* **Modern Knockback:** Backports 1.16+ knockback to 1.12: Knockback resistance is now a scale instead of a probability
* **More Banner Layers:** Sets the amount of applicable pattern layers for banners
* **Mute Advancement Errors:** Silences advancement errors
* **Mute Ore Dictionary Errors:** Silences ore dictionary errors
* **Mute Texture Map Errors:** Silences texture map errors
* **No Attack Cooldown:** Disables the 1.9 combat update attack cooldown
* **No Crafting Repair:** Disables crafting recipes for repairing tools
* **No Golems:** Disables the manual creation of golems and withers
* **No Leftover Breath Bottles:** Disables dragon's breath from leaving off empty bottles when a stack is brewed with
* **No Night Vision Flash:** Disables the flashing effect when the night vision potion effect is about to run out
* **No Potion Shift:** Disables the inventory shift when potion effects are active
* **No Portal Spawning:** Prevents zombie pigmen spawning from nether portals
* **No Redstone Lighting:** Disables lighting of active redstone, repeaters, and comparators to improve performance
* **No Saddled Wandering:** Stops horses wandering around when saddled
* **No Smelting XP:** Disables the experience reward when smelting items in furnaces
* **Offhand Improvement:** Prevents placing offhand blocks when blocks or food are held in the mainhand
* **Overhaul Beacon:** Change how beacon construct and range apply per level
* **Overlay Message Height:** Sets the Y value of the overlay message (action bar), displayed for playing records etc.
* **Pickup Notification:** Displays highly configurable notifications when the player obtains or loses items
* **Player Speed:** Enables the modification of base and maximum player speeds along with fixing 'Player moved too quickly' messages
* **Pumpkin Placing:** Allows placing Pumpkins without a supporting block
* **Rabbit Killer Spawning:** Configurable chance for rabbits to spawn as the killer bunny variant
* **Rabbit Toast Spawning:** Configurable chance for rabbits to spawn as the Toast variant
* **Rally Health:** Adds Bloodborne's Rally system to Minecraft, regain lost health when attacking back within the risk time
* **Remove 3D Anaglyph Button:** Removes the 3D Anaglyph button from the video settings menu
* **Remove Realms Button:** Removes the redundant Minecraft Realms button from the main menu
* **Remove Recipe Book:** Removes the recipe book button from GUIs
* **Remove Snooper:** Forcefully turns off the snooper and hides the snooper settings button from the options menu
* **Riding Exhaustion:** Enables depleting saturation when riding mounts
* **Sapling Behavior:** Allows customization of sapling behavior while utilizing an optimized method
* **Sea Level:** Sets the default height of the overworld's sea level
* **Selected Item Tooltip Height:** Sets the Y value of the selected item tooltip, displayed when held items are changed
* **Shield Parry:** Allows parrying of projectiles with shields
* **Skip Credits:** Skips the credits screen after the player goes through the end podium portal
* **Skip Missing Registry Entries Screen:** Automatically confirms the 'Missing Registry Entries' screen on world load
* **Sleeping Time:** Determines at which time of day sleeping is allowed in ticks (0 - 24000)
* **Smooth Scrolling:** Adds smooth scrolling to every in-game list
* **Soulbound Vexes:** Summoned vexes will also die when their summoner is killed
* **Spawn Caps:** Sets maximum spawning limits for different entity types
* **Super Hot Torch:** Enables one-time ignition of entities by hitting them with a torch
* **Stronghold Replacement:** Replaces stronghold generation with a safer variant
* **Swing Through Grass:** Allows hitting entities through grass instead of breaking it
* **Tidy Chunk:** Tidies newly generated chunks by removing scattered item entities
* **Toast Control:** Enables the control of toasts (pop-up text boxes)
* **Toggle Cheats Button:** Adds a button to the pause menu to toggle cheats
* **Trample Control:** Controls how and when farmland is trampled
* **Uncap FPS:** Removes the hardcoded 30 FPS limit in screens like the main menu
* **Undead Horses**
    * **Burning:** Lets untamed undead horses burn in daylight
    * **Taming:** Allows taming of undead horses
* **Village Distance:** Sets the village generation distance in chunks
* **Water Fall Damage:** Re-implements an improved version of pre-1.4 fall damage in water
* **XP Bottle Amount:** Sets the amount of experience spawned by bottles o' enchanting
* **XP Level Cap:** Sets the maximum experience level players can reach

![](https://i.imgur.com/1EmHZlb.png)

### **⚙️ MOD INTEGRATION**

* **AbyssalCraft**
    * **Optimized Item Transport:** Optimizes AbyssalCraft's item transport system to reduce tick overhead
    * **Disable Plague Potion Clouds:** Disables AbyssalCraft's Plague-type mobs spawning lingering potion effects on death
* **Actually Additions**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Laser Upgrade Voiding:** Fixes Laser Upgrades voiding instead of applying if there is only one item in the stack
* **Advent of Ascension**
    * **Improved Player Tick:** Improves AoA player ticking by only sending inventory changes when necessary
* **Arcane Archives**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Binnie's Mods**
    * **Gather Windfall:** Allows Forestry farms to pick up ExtraTrees fruit
* **Biomes O' Plenty**
    * **Hot Spring Water:** Fixes rapid inflection of regeneration effects in hot spring water
* **Blood Magic**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Optimized Hellfire Forge:** Optimizes the Hellfire/Soul Forge to reduce tick time
    * **World Unload Memory Leak Fix:** Fixes memory leak when unloading worlds/switching dimensions
* **Botania**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Fancy Skybox:** Enables the Botania Garden of Glass skybox for custom dimensions
* **CB Multipart**
    * **Memory Leak Fix:** Fixes a memory leak associated with EntityPlayer
* **Chisel**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Chocolate Quest Repoured**
    * **Legacy Golden Feather:** Restores the golden feather behavior from the original Better Dungeons mod
* **CoFH Core**
    * **Vorpal Enchantment Damage:** Sets the damage multiplier of the Vorpal enchantment
* **Compact Machines**
    * **Invisible Wall Render Fix:** Fixes some compact machine walls being invisible if [Nothirium](https://www.curseforge.com/minecraft/mc-mods/nothirium) 0.2.x (and up) or [Vintagium](https://github.com/Asek3/sodium-1.12) is installed
* **Effortless Building**
    * **Block Transmutation Fix:** Fixes Effortless Building ignoring Metadata when checking for items in inventory
* **Elementary Staffs**
    * **Electric Staff Port:** Reintroduces the 1.5 electric staff behavior along with some subtle particles
* **Elenai Dodge 2**
    * **Extinguishing Dodges:** Chance per dodge to extinguish the player when burning
    * **Feathers Helper API Fix:** Fixes server-sided crashes when the Feathers Helper API is utilized
    * **Sprinting Integration:** Configurable consumption of feathers when the player is sprinting
* **Epic Siege Mod**
    * **Disable Digger AI Debug:** Disables leftover debug logging inside the digger AI of the beta builds
* **Extra Utilities 2**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Forestry**
    * **Arborist Villager Trades:** Adds custom emerald to germling trades to the arborist villager
    * **Disable Bee Damage Armor Bypass:** Disables damage caused by bees bypassing player armor
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Replanting Cocoa Beans:** Allows Forestry farms to automatically replant cocoa beans
* **HWYLA**
  * **Keybindings Fix:** Fixes crashes in all menus when changing HWYLA keybindings to unsupported values
* **IndustrialCraft 2**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Industrial Foregoing**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Infernal Mobs**
    * **Sticky Recall Compatibility:** Enables compatibility between Infernal Mobs' Sticky effect and Capsule's Recall enchantment
    * **Sticky Pedestal Compatibility:** Enables compatibility between Infernal Mobs' Sticky effect and Reliquary's Pedestal
* **Iron Backpacks**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Item Stages**
    * **Ingredient Matching:** Changes item matching code to CraftTweaker's ingredient matching system, fixes item NBT issues
* **Mekanism**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Mob Stages**
    * **Spawning Rules Fixes:** Fixes mob replacement ignoring entity spawning rules
* **MrTJPCore**
    * **Memory Leak Fix:** Fixes a memory leak associated with EntityPlayer
* **Nether Chest**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Netherrocks**
    * **Right Click Harvesting Fix:** Prevents crashing with mods implementing right click crop harvesting
* **NuclearCraft**
    * **Radiation Environment Map:** Changes the data table of the radiation environment handler to improve tick time
* **ProjectRed**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Quark**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Roost**
    * **Early Register CT Chickens:** Improves load time by registering ContentTweaker chickens early for Roost to detect them
* **Simple Difficulty**
    * **Iron Canteen Interaction Fix:** Fixes the interaction of iron canteens with rain collectors
* **Simply Jetpacks**
    * **Memory Leak Fix:** Fixes a client-side memory leak associated with EntityPlayer
* **Spice Of Life**
    * **Duplication Fixes:** Fixes various duplication exploits
* **Storage Drawers**
    * **Render Range:** Approximate range in blocks at which drawers render contained items
* **Tardis**
    * **Memory Leak Fix:** Fixes a client-side memory leak associated with EntityPlayer
* **Tech Reborn**
    * **Optimized Rolling Machine:** Optimizes the Rolling Machine to reduce tick time
* **Thaumcraft**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Firebat Particles:** Adds particles to firebats similar to legacy versions
    * **Flower Bounding Box:** Fixes the bounding box always being at the center in both cinderpearls and shimmerleafs
    * **Focus Effects**
        * Revamps the cast sounds of certain focus effects to provide better variety
        * Adds impact sounds (like air or curse) to various focus effects that lack it
    * **Focus Mediums:** Makes several focus mediums play additional cast sounds to make them stand out more
    * **Spiderlike Eldritch Crabs:** Rotates dead eldritch crabs all the way like endermites, silverfish, and spiders
    * **Stable Thaumometer:** Stops the thaumometer from bobbing rapidly when using it to scan objects
    * **Wisp Particles:** Increases particle size of wisps similar to legacy versions
* **Thaumic Wonders**
    * **Duplication Fixes:** Fixes various duplication exploits
    * **Memory Leak Fix:** Fixes a client-side memory leak when wearing Void Fortress armor
* **The Erebus**
    * **Preserved Blocks Fix:** Prevents HWYLA/TOP crashes with preserved blocks
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
    * **Projectile Despawning:** Despawns unbreakable projectiles faster to improve framerates
    * **Tool Customization:** Sets the attack damage cutoff at which diminishing returns start for any Tinkers' tool and sets the rate at which a tool's attack damage incrementally decays depending on its damage cutoff
* **Tiny Progressions**
    * **Duplication Fixes:** Fixes various duplication exploits