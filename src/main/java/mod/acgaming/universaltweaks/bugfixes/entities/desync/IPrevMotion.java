package mod.acgaming.universaltweaks.bugfixes.entities.desync;

// Courtesy of Meldexun
public interface IPrevMotion
{
    double getPrevMotionX();

    void setPrevMotionX(double prevMotionX);

    double getPrevMotionY();

    void setPrevMotionY(double prevMotionY);

    double getPrevMotionZ();

    void setPrevMotionZ(double prevMotionZ);

    /**
     * Checks if this entity has ever called super.onUpdate(). This should adequately determine if it should be ignored by the desync fix.
     * @return true if the implementing class calls super.onUpdate()
     */
    boolean hasSuperUpdate();
}