public class VimSnip {
    private Snippet snippet;
    private SnippetsRepository snippets = new SnippetsRepository();

    public Snippet get(String title) {
        return snippets.get(title);
    }

    public void save(String title, String body) {
        snippets.save(new Snippet(title,body));
    }
}
