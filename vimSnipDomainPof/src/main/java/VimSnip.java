public class VimSnip {
    private SnippetsRepository snippets = new SnippetsRepository();

    public VimSnip(SnippetsRepository snippetsRepository) {
        this.snippets = snippetsRepository;
    }

    public Snippet get(String title) {
        return snippets.get(new SnippetTitle(title));
    }

    public void save(String title, String body) {
        if(snippets.has(new SnippetTitle(title))){
            Snippet temp = (new Snippet(title,body)).upgradeVersion();
            snippets.save(temp);
            return;
        }
        snippets.save(new Snippet(title,body));
    }

}
