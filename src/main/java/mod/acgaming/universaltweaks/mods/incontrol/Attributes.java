package mod.acgaming.universaltweaks.mods.incontrol;

public enum Attributes
{
    HEALTH("ctrlHealth"),
    SPEED("ctrlSpeed"),
    DAMAGE("ctrlDamage");

    final String tag;

    Attributes(String tag)
    {
        this.tag = tag;
    }

    public String getTag()
    {
        return tag;
    }
}
