package mod.acgaming.universaltweaks.tweaks.craftingcache;

// Courtesy of EverNife
public class UTOptionalContent<T>
{
    public boolean hasContent = false;
    public T content;

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