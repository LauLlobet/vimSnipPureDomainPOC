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
        return getOrElseThrowSnippetWithTitle(title);
    }

    private Snippet getOrElseThrowSnippetWithTitle(SnippetTitle title) {
        if(!repository.hasSnippetWith(title)){
            throw new NoSuchElementException();
        }
        return repository.get(title);
    }

    private Snippet latestVersion(SnippetTitle zeroVersionTitle) {
        SnippetTitle title = getMostRecentTitleStartingFrom(zeroVersionTitle);
        throwIfIsZeroVersion(title);
        return repository.get(title);
    }

    private void throwIfIsZeroVersion(SnippetTitle title) {
        if(title.isVersionZero()){
            throw new NoSuchElementException();
        }
    }

    private SnippetTitle getMostRecentTitleStartingFrom(SnippetTitle title) {
        do {
            title = title.upgradeVersion();
        } while (repository.hasSnippetWith(title));
        title = title.downgradedVersion();
        return title;
    }

    public void save(String title, String body) {
        Snippet snippet = new Snippet(title,body);
        checkIsCorrectVersion(snippet);
        repository.save(snippet);
    }

    private void checkIsCorrectVersion(Snippet snippet) {
        if( repository.has(snippet) || snippet.isNotAZeroVersion() && ! repository.has(snippet.downgradeVersion())){
            throw new NotCorrectVersionOfSnippetToSave();
        }
    }
}
