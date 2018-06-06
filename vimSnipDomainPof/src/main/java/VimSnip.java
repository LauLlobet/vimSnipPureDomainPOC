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
        if(isAFirstVersionAndNeverSavedBefore(snippet) || snippets.hasNot(snippet) && snippets.has(snippet.downgradeVersion())){
            snippets.save(snippet);
        }
    }

    private boolean isAFirstVersionAndNeverSavedBefore(Snippet snippet) {
        return snippet.isVersionZero() && snippets.hasNot(snippet);
    }
}
