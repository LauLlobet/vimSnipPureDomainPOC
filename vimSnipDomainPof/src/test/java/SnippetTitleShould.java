import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SnippetTitleShould {
    @Test
    public void
    be_equal_to_another_title_that_has_the_same_keywords() {
       assertEquals(new SnippetTitle("Arbol    Balance Casa"),new SnippetTitle("Casa Balance Arbol"));
    }

    @Test
    public void
    be_serializable() {
       assertEquals(new SnippetTitle("Balance Arbol    Casa").toString(), "Arbol Balance Casa");
    }

    @Test
    public void
    distinct_same_title_with_different_versions() {
       assertNotEquals(new SnippetTitle("A B C"),new SnippetTitle("A B C").updateVersion());
    }

    @Test
    public void
    obtain_version_from_creation_string_apostrophes() {
       assertThat(new SnippetTitle("A C'' B").toString(),is("'' A B C"));
    }

    @Test
    public void
    be_updatable() {
       assertThat(new SnippetTitle(" A B ' ").updateVersion().toString(),is("'' A B"));
    }
}
