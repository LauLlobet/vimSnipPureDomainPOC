public class VimSnip {
    private SnippetsRepository snippets;

    public VimSnip(SnippetsRepository snippetsRepository) {
        this.snippets = snippetsRepository;
    }

    public Snippet get(String title) {
        return snippets.get(new SnippetTitle(title));
    }

    public void save(String title, String body) {
        Snippet snippet = new Snippet(title,body);
        if(snippets.has(snippet.getTitle())){
            snippets.save(snippet.upgradeVersion());
            return;
        }
        snippets.save(snippet);
    }
}
