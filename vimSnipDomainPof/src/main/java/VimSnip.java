import java.util.NoSuchElementException;

public class VimSnip {
    private SnippetsProviderService repository;

    VimSnip(SnippetsProviderService snippetsProviderService) {
        this.repository = snippetsProviderService;
    }

    public Snippet get(String titleString) { //TODO: Erik, is domain core comunicated with outer layers via primitives? or can it use types
        SnippetTitle title = new SnippetTitle(titleString);
        if(title.isVersionZero()){
            return latestVersion(title);
        }
        return getOrElseThrowSnippetWithTitle(title);
    }

    private Snippet getOrElseThrowSnippetWithTitle(SnippetTitle title) { //TODO: Marc, ask if the 3 responsabilities, (newes't version + get + save ) should be in three separated classes
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
        checkIsCorrectVersion(snippet.getTitle());
        repository.save(snippet);
    }

    private void checkIsCorrectVersion(SnippetTitle snippetTitle) {
        if( repository.hasSnippetWith(snippetTitle) ||
                snippetTitle.isVersionZero() ||
                ( !repository.hasSnippetWith(snippetTitle.downgradedVersion()) && snippetTitle.isNotVersion1())
                ){

            throw new NotCorrectVersionOfSnippetToSave();
        }
    }
}
