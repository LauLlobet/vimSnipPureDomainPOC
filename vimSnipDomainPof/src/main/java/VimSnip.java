public class VimSnip {
    private SnippetsRepository repository;

    VimSnip(SnippetsRepository snippetsRepository) {
        this.repository = snippetsRepository;
    }

    public Snippet get(String title) {
        return repository.get(new SnippetTitle(title));
    }

    public void save(String title, String body) {
        Snippet snippet = new Snippet(title,body);
        checkVersion(snippet);
        repository.save(snippet);
    }

    private void checkVersion(Snippet snippet) {
        if( repository.has(snippet) || snippet.isNotAZeroVersion() && ! repository.has(snippet.downgradeVersion())){
            throw new NotCorrectVersionOfSnippetToSave();
        }
    }
}
