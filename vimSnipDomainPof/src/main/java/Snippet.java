public class Snippet {
    private final SnippetTitle title;
    private final String body;

    public Snippet(String title, String body) {
        this.title = new SnippetTitle(title);
        this.body = body;
    }

    public Snippet(SnippetTitle snippetTitle, String body) {
        title = snippetTitle;
        this.body = body;
    }

    public Snippet(SnippetTitle snippetTitle) {
        title = snippetTitle;
        body = null;
    }

    public Snippet(String title){
        this(new SnippetTitle(title));
    }

    public String getBody()
    {
        if(body == null){
            throw new DowngradedVersionSnippetUsedAsARegularSnippet();
        }
        return body;
    }

    public SnippetTitle getTitle(){
        return title;
    }

    public String getTitleString(){
        return title.toString();
    }

    public Snippet upgradeVersion() {
        return new Snippet(title.updateVersion(),body);
    }

    public boolean isVersionZero() {
        return title.isVersionZero();
    }

    public Snippet downgradeVersion() {
        return new Snippet(title.downgradedVersionTitle());
    }
}
