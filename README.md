# 🔧 Universal Tweaks 🔧

### A one-stop-shop for all bugfixing and tweaking needs

All changes are toggleable via the config file.

**Bugfixes:**

* Accurate Smooth Lighting: Improves the accuracy of smooth lighting by checking for suffocation and light opacity
* Attack Radius: Improves the attack radius of hostile mobs by checking the line of sight with raytracing
* Block Fire: Prevents fire projectiles burning entities when blocking with shields
* Block Overlay: Fixes x-ray when standing in non-suffocating blocks
* Boat Riding Offset: Fixes entities glitching through the bottom of boats
* Chunk Saving: Fixes loading of outdated chunks to prevent duplications, deletions and data corruption
* Comparator Timing: Fixes inconsistent delays of comparators to prevent redstone timing issues
* Concurrent Entity AI Tasks: Replaces linked entity AI task sets with concurrent sets to avoid mod exception concerning entity AI
* Death Time: Fixes corrupted entities exceeding the allowed death time
* Depth Mask: Fixes entity and particle rendering issues by enabling depth buffer writing
* Destroy Entity Packets: Fixes lag caused by dead entities by sending additional packets when the player is not alive
* Dimension Change Player States: Fixes missing player states when changing dimensions by sending additional packets
* Disconnect Dupe: Fixes item dupes when players are dropping items and disconnecting
* Double Consumption: Fixes consuming an item having a chance of also consuming a second item without any animation
* Elytra Deployment & Landing: Relocate elytra deployment and landing to client side to prevent issues with high latencies
* Entity Bounding Boxes: Saves entity bounding boxes to tags to prevent breakouts and suffocation
* Entity Desync: Fixes entity motion desyncs most notable with arrows and thrown items
* Entity ID: Fixes non-functional elytra firework boosting and guardian targeting if the entity ID is 0
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
* Max Health: Corrects maximum player health on joining by setting the last saved health value
* Mining Glitch: Avoids the need for multiple mining attempts by sending additional movement packets
* Model Gap: Fixes transparent gaps in all 3D models of blocks and items
* Mount Desync: Fixes mounts and boats sometimes disappearing after dismounting
* Piston Progress: Properly saves the last state of pistons to tags
* Skeleton Aim: Fixes skeletons not looking at their targets when strafing
* Tile Entity Map: Replaces the chunk position data table to prevent tile entity related issues
* Villager Mantle: Returns missing hoods to villager mantles

**Tweaks:**

* AI Improvements: Replaces/removes entity AI for improved server performance
* Attributes: Sets custom ranges for entity attributes
* Auto Jump Replacement: Replaces auto jump with an increased step height (singleplayer only)
* Auto Save Interval: Configurable interval in ticks between world auto saves
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
* Block Dispenser: Allows dispensers to place blocks
* Block Hit Delay: Sets the delay in ticks between breaking blocks
* Boat Speed: Sets the acceleration value for controlling boats
* Bow Infinity Remedy: Bows enchanted with Infinity no longer require arrows
* Breakable Bedrock: Allows customizable mining of bedrock
* Charged Creeper Spawning: Sets the chance for creepers to spawn charged
* Check Animated Models: Improves model load times by checking if an animated model exists before trying to load it
* Chunk Gen Limit: Limits maximum chunk generation per tick for improved server performance
* Copy World Seed: Enables clicking of `/seed` world seed in chat to copy to clipboard
* Crafting Cache: Adds an IRecipe cache to improve recipe performance in large modpacks
* Creeper Confetti: Replaces deadly creeper explosions with delightful confetti (with a configurable chance)
* Custom Rarity: Sets custom rarities for items, affecting tooltip colors
* Custom Use Duration: Sets custom use durations for items like shields, affecting the maximum block time
* Damage Tilt: Restores feature to tilt the camera when damaged
* Default Difficulty: Sets the default difficulty for newly generated worlds
* Dimension Unload: Unloads dimensions not in use to free up resources
* Disable Audio Debug: Improves loading times by removing debug code for missing sounds and subtitles
* Disable Creeper Music Discs: Disables creepers dropping music discs when slain by skeletons
* Disable Fancy Missing Model: Improves rendering performance by removing the resource location text on missing models
* Disable Narrator: Disables the narrator functionality entirely
* Disable Sleeping: Disables skipping night by using a bed while making it still able to set spawn
* Disable Wither Targeting AI: Disables withers targeting animals
* Easy Breeding: Enables easy breeding of animals by tossing food on the ground
* End Portal Parallax: Re-implements parallax rendering of the end portal from 1.11 and older
* Fast Dye Blending: Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry
* Fast Leaf Decay: Makes leaves decay faster when trees are chopped
* Fast Prefix Checking: Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time
* Fast World Loading: Skips initial world chunk loading & garbage collection to speed up world loading
* Fence/Wall Jump: Allows the player to jump over fences and walls
* Finite Water: Prevents creation of infinite water sources outside of ocean and river biomes
* Growth Size: Configurable growth height/length for sugar cane, cacti and vines
* Hardcore Buckets: Prevents placing of liquid source blocks in the world
* Horizontal Collision Damage: Applies horizontal collision damage to the player akin to elytra collision
* Husk & Stray Spawning: Lets husks and strays spawn underground like regular zombies and skeletons
* Incurable Potions: Excludes potion effects from being curable with curative items like buckets of milk
* Infinite Music: Lets background music play continuously without delays
* Item Entities: Enables the modification of item entity properties
* Lenient Paths: Allows the creation of grass paths everywhere (beneath fence gates, trapdoors, ...)
* Lightning
    * Lightning Damage: Sets the damage lightning bolts deal to entities
    * Lightning Fire Ticks: Sets the duration in ticks lightning bolts set entities on fire
    * No Lightning Fire: Disables the creation of fire around lightning bolt strikes
    * No Lightning Flash: Disables the flashing of skybox and ground brightness on lightning strikes
* Linear XP Amount: Sets the amount of XP needed for each level, effectively removing the increasing level scaling
* Load Sounds: Plays sounds when the game or the world are loaded
* Mending Overpowered: If mending fix is enabled, repairs entire damaged inventory with XP
* Mending: Only repairs damaged equipment with XP
* Mob Despawn Improvement: Mobs carrying picked up items will drop their equipment and despawn properly
* No Attack Cooldown: Disables the 1.9 combat update attack cooldown
* No Crafting Repair: Disables crafting recipes for repairing tools
* No Golems: Disables the manual creation of golems and withers
* No Leftover Breath Bottles: Disables dragon's breath from leaving off empty bottles when a stack is brewed with
* No Night Vision Flash: Disables the flashing effect when the night vision potion effect is about to run out
* No Potion Shift: Disables the inventory shift when potion effects are active
* No Redstone Lighting: Disables lighting of active redstone, repeaters, and comparators to improve performance
* No Saddled Wandering: Stops horses wandering around when saddled
* No Smelting XP: Disables the experience reward when smelting items in furnaces
* Offhand Improvement: Prevents placing offhand blocks when blocks or food are held in the mainhand
* Pickup Notification: Displays highly configurable notifications when the player obtains or loses items
* Player Speed: Enables the modification of base and maximum player speeds along with fixing 'Player moved too quickly' messages
* Rabbit Killer Spawning: Configurable chance for rabbits to spawn as the killer bunny variant
* Rabbit Toast Spawning: Configurable chance for rabbits to spawn as the Toast variant
* Rally Health: Adds Bloodborne's Rally system to Minecraft, regain lost health when attacking back within the risk time
* Remove Realms Button: Removes the redundant Minecraft Realms button from the main menu
* Remove Recipe Book: Removes the recipe book button from GUIs
* Remove Snooper: Forcefully turns off the snooper and hides the snooper settings button from the options menu
* Shield Parry: Allows parrying of projectiles with shields
* Skip Credits: Skips the credits screen after the player goes through the end podium portal
* Smooth Scrolling: Adds smooth scrolling to every in-game list
* Spawn Caps: Sets maximum spawning limits for different entity types
* Super Hot Torch: Enables one-time ignition of entities by hitting them with a torch
* Stronghold Replacement: Replaces stronghold generation with a safer variant
* Swing Through Grass: Allows hitting entities through grass instead of breaking it
* Tidy Chunk: Tidies newly generated chunks by removing scattered item entities
* Toast Control: Enables the control of toasts (pop-up text boxes)
* Toggle Cheats Button: Adds a button to the pause menu to toggle cheats
* Uncap FPS: Removes the hardcoded 30 FPS limit in screens like the main menu
* Undead Horses:
    * Burning: Lets untamed undead horses burn in daylight
    * Taming: Allows taming of undead horses
* Water Fall Damage: Re-implements an improved version of pre-1.4 fall damage in water
* XP Bottle Amount: Sets the amount of experience spawned by bottles o' enchanting

**Mod Tweaks:**

* AbyssalCraft
    * Optimized Item Transport: Makes an optimization to reduce tick overhead of AbyssalCraft's item transport system
* Advent of Ascension
    * Inventory-less GUI Compatibility: Fixes AoA player ticking in certain GUIs without player inventories (i.e. Flux Networks GUI)
* Binnie's Mods
    * Gather Windfall: Allows Forestry farms to pick up ExtraTrees fruit
* Biomes O' Plenty
    * Hot Spring Water: Fixes rapid inflection of regeneration effects in hot spring water
* Botania
    * Fancy Skybox: Enables the Botania Garden of Glass skybox for custom dimensions
* Chocolate Quest Repoured
    * Legacy Golden Feather: Restores the golden feather behavior from the original Better Dungeons mod
* Elementary Staffs
    * Electric Staff Port: Reintroduces the 1.5 electric staff behavior along with some subtle particles
* Elenai Dodge 2
    * Feathers Helper API Fix: Fixes server-sided crashes when the Feathers Helper API is utilized
* Epic Siege Mod
    * Disable Digger AI Debug: Disables leftover debug logging inside the digger AI of the beta builds
* Forestry
    * Arborist Villager Trades: Adds custom emerald to germling trades to the arborist villager
    * Disable Bee Damage Armor Bypass: Disables damage caused by bees bypassing player armor
    * Replanting Cocoa Beans: Allows Forestry farms to automatically replant cocoa beans
* Mo' Creatures
    * Custom Modded Biomes: Adds support for modded biome spawns via config
    * Disable Flame Wraith Burning: Disables the flickering burning effect of flame wraiths
* Roost
    * Early Register CT Chickens: Improves load time by registering CT chickens early for Roost to detect them
* Simple Difficulty
    * Iron Canteen Interaction Fix: Fixes the interaction of iron canteens with rain collectors
* Storage Drawers
    * Item Handlers:
        * Fixes voiding of items when nearing full capacity
        * Fixes slotless item handler implementation not allowing the extraction from compacting item drawers with the vending upgrade
        * Caches the drawer controller tile to avoid getting the TE from the world every time a drawer slave is interacted with
    * Render Range: Approximate range in blocks at which drawers render contained items
* Tech Reborn
    * Optimized Rolling Machine: Optimizes the Rolling Machine to reduce tick time
* Thaumcraft
    * Firebat Particles: Adds particles to firebats similar to legacy versions
    * Flower Bounding Box: Fixes the bounding box always being at the center in both cinderpearls and shimmerleafs
    * Focus Effects:
        * Revamps the cast sounds of certain focus effects to provide better variety
        * Adds impact sounds (like air or curse) to various focus effects that lack it
    * Focus Mediums: Makes several focus mediums play additional cast sounds to make them stand out more
    * Spiderlike Eldritch Crabs: Rotates dead eldritch crabs all the way like endermites, silverfish, and spiders
    * Stable Thaumometer: Stops the thaumometer from bobbing rapidly when using it to scan objects
    * Wisp Particles: Increases particle size of wisps similar to legacy versions
* Thermal Expansion
    * Insolator Custom Monoculture: Adds Monoculture Cycle integration to desired phytogenic insolator recipes added by ModTweaker
* Tinkers' Construct
    * Gaseous Fluids: Excludes gaseous fluids from being transferable via faucets
    * Offhand Shuriken: Suppresses special abilities of long swords and rapiers when shurikens are wielded in the offhand
    * Projectile Despawning: Despawns unbreakable projectiles faster to improve framerates
    * Ore Dictionary Cache: Caches all ore dictionary smelting recipes to speed up game loading