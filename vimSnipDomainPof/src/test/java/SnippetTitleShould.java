import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnippetTitleShould {
    @Test
    public void
    be_equal_to_another_title_that_has_the_same_keywords() {
       assertEquals(new SnippetTitle("Arbol    Balance ''Casa"),new SnippetTitle("Casa Balance Arbol"));
    }
}
