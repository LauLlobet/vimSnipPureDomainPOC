import java.util.Hashtable;

public class SnippetsRepository {
    private Hashtable<String, Snippet> hashtable = new Hashtable<String, Snippet>();

    public Snippet get(SnippetTitle title) {
        return hashtable.get(title.toString());
    }

    public void save(Snippet snippet) {
        hashtable.put(snippet.getTitle().toString(),snippet);
    }

    public boolean has(SnippetTitle title) {
        return hashtable.containsKey(title);
    }
}
