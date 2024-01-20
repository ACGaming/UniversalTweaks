package mod.acgaming.universaltweaks.tweaks.performance.craftingcache;

// Courtesy of EverNife
public class UTOptionalContent<T>
{
    private boolean hasContent = false;
    private T content;

    public T getContent()
    {
        return content;
    }

    public void setContent(T content)
    {
        this.content = content;
        this.hasContent = true;
    }

    public boolean hasContent()
    {
        return hasContent;
    }
}