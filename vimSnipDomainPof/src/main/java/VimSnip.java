public class VimSnip {
    private SnippetsRepository snippets = new SnippetsRepository();

    public VimSnip(SnippetsRepository snippetsRepository) {
        this.snippets = snippetsRepository;
    }

    public Snippet get(String title) {
        return snippets.get(new SnippetTitle(title));
    }

    public void save(String title, String body) {
        if(snippets.has(title)){
            update(title+"'",body);
            return;
        }
        snippets.save(new Snippet(title,body));
    }

    private void update(String title, String body) {
        snippets.save(new Snippet(title,body));
    }
}
