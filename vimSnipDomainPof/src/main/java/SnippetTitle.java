import java.util.*;

public class SnippetTitle {
    final Set<String> keywords;

    public SnippetTitle(String titleArguments) {
        String[] choppedWords = titleArguments.split("['| ]+");
        keywords = new HashSet(Arrays.asList(choppedWords));

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
}
