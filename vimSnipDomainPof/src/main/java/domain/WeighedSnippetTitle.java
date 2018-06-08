package domain;

public class WeighedSnippetTitle implements Comparable{
    private final SnippetTitle title;
    private final int sons;

    public WeighedSnippetTitle(SnippetTitle title, int sons) {

        this.title = title;
        this.sons = sons;
    }


    @Override
    public int compareTo(Object o) {
        WeighedSnippetTitle weightedTitle = (WeighedSnippetTitle)o;
        if( - sons + weightedTitle.sons == 0){
            return this.title.toString().compareTo( weightedTitle.title.toString());
        }
        return - sons + weightedTitle.sons;
    }

    public SnippetTitle getTitle() {
        return title;
    }
}
