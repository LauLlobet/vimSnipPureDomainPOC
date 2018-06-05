import java.util.Hashtable;

public class SnippetsRepository {
    private Hashtable<String, Snippet> hashtable = new Hashtable<String, Snippet>();

    public Snippet get(String title) {
        return hashtable.get(title);
    }

    public void save(Snippet snippet) {
        hashtable.put(snippet.getTitle(),snippet);
    }
}
