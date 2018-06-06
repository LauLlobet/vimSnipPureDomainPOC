import java.util.Objects;

public class Snippet {
    private final SnippetTitle title;
    private final String body;

    public Snippet(String title, String body) {
        this.title = new SnippetTitle(title);
        this.body = body;
    }

    public Snippet(SnippetTitle snippetTitle, String body) {
        title = snippetTitle;
        this.body = body;
    }

    public Snippet(SnippetTitle snippetTitle) {
        title = snippetTitle;
        body = null;
    }

    public Snippet(String title){
        this(new SnippetTitle(title));
    }

    public String getBody()
    {
        if(body == null){
            throw new DowngradedVersionSnippetUsedAsARegularSnippet();
        }
        return body;
    }

    public SnippetTitle getTitle(){
        return title;
    }

    public String getTitleString(){
        return title.toString();
    }

    public Snippet upgradeVersion() {
        return new Snippet(title.upgradeVersion(),body);
    }

    public boolean isVersionZero() {
        return title.isVersionZero();
    }

    public Snippet downgradeVersion() {
        return new Snippet(title.downgradedVersion());
    }

    public boolean isNotAZeroVersion() {
        return !isVersionZero();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snippet snippet = (Snippet) o;
        return Objects.equals(title, snippet.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }
}
