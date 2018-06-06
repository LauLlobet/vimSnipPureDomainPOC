import java.util.*;

public class SnippetTitle {
    private static final String ZERO_VERSION = "";
    private final String title;
    private String BY_SPACE_OR_APOSTROPHE = "['| ]+";

    SnippetTitle(String titleArgument) {
        Set<String> keywords = createKeywordsSetWithVersionFrom(titleArgument);
        title = keywordSetToString(keywords);
    }

    private Set<String> createKeywordsSetWithVersionFrom(String titleArguments) {
        HashSet<String> set = new HashSet<>(Arrays.asList(titleArguments.split(BY_SPACE_OR_APOSTROPHE)));
        set.add(getVersion(titleArguments));
        set.remove("");
        return set;
    }

    private boolean isNotVersionZero(String titleArguments) {
        return titleArguments.contains("'");
    }

    private String getVersion(String titleArguments) {
        int versionNum = titleArguments.replaceAll("[^']", "").length();
        String ans = "";
        for(int i=0; i < versionNum; i++ ){
            ans += "'";
        }
        return ans;
    }

    public SnippetTitle updateVersion() {
        return new SnippetTitle(this.toString() + " '");
    }

    private String keywordSetToString(Set<String> keywords) {
        ArrayList<String> keywordsArray = new ArrayList<>(keywords);
        Collections.sort(keywordsArray);
        String stringifiedTitle = keywordsArray.stream().reduce("", (x, y) -> x + y + " ");
        return stringifiedTitle.substring(0, stringifiedTitle.length() - 1);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnippetTitle that = (SnippetTitle) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }
}
