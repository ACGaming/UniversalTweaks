package mod.acgaming.universaltweaks.bugfixes.blocks.miningglitch;

// MC-118710
// https://bugs.mojang.com/browse/MC-118710
// Courtesy of mrgrim
public interface IEntityPlayerSP
{
    void updateWalkingPlayer();
}