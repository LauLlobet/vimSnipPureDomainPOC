package domain;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnippetTitle {
    private final SortedSet<String> keywords;
    private final int version;
    private String BY_SPACE = "['| ]+";

    public SnippetTitle(String titleString) {
        version = getVersionNum(titleString);
        keywords = createKeywordsSetWithVersionFrom(titleString);
    }

    public SnippetTitle(SortedSet<String> keywords, int version) {
        this.keywords = keywords;
        this.version = version;
    }

    private SortedSet<String> createKeywordsSetWithVersionFrom(String titleArguments) {
        SortedSet<String> set = new TreeSet<>(Arrays.asList(titleArguments.split(BY_SPACE)));
        set.remove("");
        set.remove(""+version);
        return set;
    }

    public boolean isVersionZero() {
        return version == 0;
    }

    @Override
    public String toString(){
        String stringifiedTitle = new ArrayList<>(keywords).stream().reduce("", (x, y) -> x + y + " ");
        return stringifiedTitle + version;
    }

    public SnippetTitle downgradedVersion() {
        return new SnippetTitle(keywords, version - 1);
    }

    private int getVersionNum(String titleArguments) {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(titleArguments);
        ArrayList<Integer> list = new ArrayList<>();
        while (m.find()) {
            list.add(Integer.parseInt(m.group()));
        }
        if(list.size() > 1){
            throw new TwoOrMoreVersionsProvidedForSnippetCreation();
        }
        if(list.size() == 0){
            return 0;
        }
        return list.get(0);
    }

    public SnippetTitle upgradeVersion() {
        return new SnippetTitle(keywords, version + 1);
    }


    public boolean isNotVersion1() {
        return version != 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SnippetTitle that = (SnippetTitle) o;
        return version == that.version &&
                Objects.equals(keywords, that.keywords) &&
                Objects.equals(BY_SPACE, that.BY_SPACE);
    }

    @Override
    public int hashCode() {

        return Objects.hash(keywords, version, BY_SPACE);
    }

    public int getVersion() {
        return version;
    }
}