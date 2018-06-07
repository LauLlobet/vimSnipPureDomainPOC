import java.util.Objects;

public class Snippet {
    private final SnippetTitle title;
    private final String body;

    public Snippet(String title, String body) {
        this.title = new SnippetTitle(title);
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
        return body;
    }

    public SnippetTitle getTitle(){
        return title;
    }

    public String getTitleString(){
        return title.toString();
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
