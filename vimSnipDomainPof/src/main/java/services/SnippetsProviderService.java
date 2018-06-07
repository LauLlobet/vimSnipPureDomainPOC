package services;


import domain.Snippet;
import domain.SnippetTitle;

import java.util.Hashtable;

public class SnippetsProviderService { //TODO: Naming for this service + is it a repository?
    private Hashtable<String, Snippet> hashtable = new Hashtable<>();

    public Snippet get(SnippetTitle title) {
        return hashtable.get(title.toString());
    }

    public void save(Snippet snippet) {
        hashtable.put(snippet.getTitle().toString(),snippet);
    }

    public boolean hasSnippetWith(SnippetTitle snippetTitle) {
        return hashtable.containsKey(snippetTitle.toString());
    }
}
