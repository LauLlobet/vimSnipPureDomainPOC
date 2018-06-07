import java.util.*;

public class SnippetTitle {
    private final String title;
    private String BY_SPACE_OR_APOSTROPHE = "['| ]+";

    SnippetTitle(String titleString) {
        SortedSet<String> keywordsSet = createKeywordsSetWithVersionFrom(titleString, getVersionNum(titleString));
        title = keywordSetToString(keywordsSet);
    }

    private SortedSet<String> createKeywordsSetWithVersionFrom(String titleArguments, int versionNum) {
        SortedSet<String> set = new TreeSet<>(Arrays.asList(titleArguments.split(BY_SPACE_OR_APOSTROPHE)));
        set.add(createVersionKeyword(versionNum));
        set.remove("");
        return set;
    }

    private String keywordSetToString(Set<String> keywords) {
        String stringifiedTitle = new ArrayList<>(keywords).stream().reduce("", (x, y) -> x + y + " ");
        return stringifiedTitle.substring(0, stringifiedTitle.length() - 1);
    }

    public boolean isVersionZero() {
        return ! title.contains("'");
    }

    public SnippetTitle downgradedVersion() {
        int lowerVersion = getVersionNum(title) - 1;
        Set<String> keywordsSet = createKeywordsSetWithVersionFrom(title, lowerVersion);
        return new SnippetTitle(keywordSetToString(keywordsSet));
    }

    private String createVersionKeyword(int versionNum) {
        String ans = "";
        for(int i=0; i < versionNum; i++ ){
            ans += "'";
        }
        return ans;
    }

    private int getVersionNum(String titleArguments) {
        return titleArguments.replaceAll("[^']", "").length();
    }

    public SnippetTitle upgradeVersion() {
        return new SnippetTitle(this.toString() + " '");
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


    public boolean isNotVersion1() {
        return getVersionNum(title) != 1;
    }
}
