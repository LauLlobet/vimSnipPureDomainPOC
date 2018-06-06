import java.util.Hashtable;

public class SnippetsRepository {
    private Hashtable<String, Snippet> hashtable = new Hashtable<>();

    public Snippet get(SnippetTitle title) {
        return hashtable.get(title.toString());
    }

    public void save(Snippet snippet) {
        hashtable.put(snippet.getTitle().toString(),snippet);
    }

    public boolean has(Snippet snippet) {
        return hasSnippetWith(snippet.getTitle());
    }

    public boolean hasNot(Snippet snippet) {
        return !has(snippet);
    }

    public boolean hasSnippetWith(SnippetTitle snippetTitle) {
        return hashtable.containsKey(snippetTitle.toString());
    }

    public boolean hasNotSnippetWith(SnippetTitle snippetTitle) {
        return !hasSnippetWith(snippetTitle);
    }
}
