import java.util.NoSuchElementException;

public class VimSnip {
    private SnippetsRepository repository;

    VimSnip(SnippetsRepository snippetsRepository) {
        this.repository = snippetsRepository;
    }

    public Snippet get(String titleString) {
        SnippetTitle title = new SnippetTitle(titleString);
        if(title.isVersionZero()){
            return latestVersion(title);
        }
        if(! repository.hasSnippetWith(title)){
            throw new NoSuchElementException();
        }
        return repository.get(new SnippetTitle(titleString));
    }

    private Snippet latestVersion(SnippetTitle zeroVersionTitle) {
        SnippetTitle title = zeroVersionTitle;
        do {
            title = title.upgradeVersion();
        } while (repository.hasSnippetWith(title));
        title = title.downgradedVersion();
        if(title.isVersionZero()){
            throw new NoSuchElementException();
        }
        return repository.get(title);
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
