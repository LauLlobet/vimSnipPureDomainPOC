import java.util.NoSuchElementException;

public class SnippetSaver {
    private SnippetsProviderService repository;

    SnippetSaver(SnippetsProviderService snippetsProviderService) {
        this.repository = snippetsProviderService;
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
