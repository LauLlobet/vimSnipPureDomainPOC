package services;

import domain.Snippet;
import domain.SnippetTitle;

import java.util.NoSuchElementException;

public class SnippetRetriever {
    private SnippetsProviderService repository;

    SnippetRetriever(SnippetsProviderService snippetsProviderService) {
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
        SnippetTitle title = getLastestTitleStartingFrom(zeroVersionTitle);
        throwIfIsZeroVersion(title);
        return repository.get(title);
    }

    private void throwIfIsZeroVersion(SnippetTitle title) {
        if(title.isVersionZero()){
            throw new NoSuchElementException();
        }
    }

    private SnippetTitle getLastestTitleStartingFrom(SnippetTitle title) {
        do {
            title = title.upgradeVersion();
        } while (repository.hasSnippetWith(title));
        title = title.downgradedVersion();
        return title;
    }
}
