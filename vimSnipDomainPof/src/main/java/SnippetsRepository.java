import java.util.Hashtable;

public class SnippetsRepository {
    private Hashtable<SnippetTitle, Snippet> hashtable = new Hashtable<SnippetTitle, Snippet>();

    public Snippet get(SnippetTitle title) {
        return hashtable.get(title);
    }

    public void save(Snippet snippet) {
        hashtable.put(snippet.getTitle(),snippet);
    }

    public boolean has(String title) {
        return hashtable.containsKey(title);
    }
}
