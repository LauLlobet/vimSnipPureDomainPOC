import java.util.*;

public class SnippetTitle {
    final Set<String> keywords;

    public SnippetTitle(String titleArguments) {
        String version = getVersion(titleArguments);
        String[] choppedWords = titleArguments.split("['| ]+");
        keywords = new HashSet(Arrays.asList(choppedWords)){{add(version);
        remove("");}};
    }

    private String getVersion(String titleArguments) {
        int versionNum = titleArguments.replaceAll("[^']", "").length();
        String ans = "";
        for(int i=0; i < versionNum; i++ ){
            ans += "'";
        }
        return ans;
    }


    @Override
    public String toString() {
        ArrayList<String> keywordsArray = new ArrayList<>(keywords);
        Collections.sort(keywordsArray);
        String stringifiedTitle = keywordsArray.stream().reduce("", (x, y) -> x + y + " ");
        return stringifiedTitle.substring(0, stringifiedTitle.length() - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnippetTitle that = (SnippetTitle) o;
        return Objects.equals(keywords, that.keywords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords);
    }

    public SnippetTitle updateVersion() {
        return new SnippetTitle(this.toString() + " '");
    }
}
