# 🔧 Universal Tweaks 🔧
### A one-stop-shop for all bugfixing and tweaking needs

Already implemented features:

**Bugfixes:**
* Block Overlay: Fixes x-ray when standing in non-suffocating blocks
* Dimension Change Player States: Fixes missing player states when changing dimensions by sending additional packets
* Entity Bounding Boxes: Saves entity bounding boxes to tags to prevent breakouts and suffocation
* Ladder Flying Slowdown: Disables climbing movement when flying
* Max Health: Saves increased player health to tags
* Mending: Only repairs damaged equipment with XP
* Piston Progress: Properly saves the last state of pistons to tags
* Skeleton Aim: Fixes skeletons not looking at their targets when strafing
* Tile Entity Update Order: Keeps the order of tile entities on chunk load

**Tweaks:**
* Attributes Uncap: Virtually uncaps entity attributes
* Auto Jump Replacement: Replaces auto jump with an increased step height
* Bed Obstruction Replacement: Replaces bed obstruction checks with an improved version
* Bow Infinity Remedy: Bows enchanted with Infinity no longer require arrows
* Mob Despawn Improvement: Mobs carrying picked up items will drop their equipment and despawn properly
* Offhand Improvement: Prevents placing offhand blocks when blocks or food are held in the mainhand
* Water Fall Damage: Re-implements an improved version of pre-1.4 fall damage in water