package mod.acgaming.universaltweaks.mods.tconstruct.oredictcache;

// Courtesy of youyihj
public enum UTOreDictRecipesState
{
    SCAN, READ;

    private static UTOreDictRecipesState currentState = null;

    public static UTOreDictRecipesState getCurrentState()
    {
        return currentState;
    }

    public static void setCurrentState(UTOreDictRecipesState currentState)
    {
        UTOreDictRecipesState.currentState = currentState;
    }

    public boolean isRead()
    {
        return this == READ;
    }

    public boolean isScan()
    {
        return this == SCAN;
    }
}