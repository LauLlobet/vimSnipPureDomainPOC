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

    public String getBody() {
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
}
