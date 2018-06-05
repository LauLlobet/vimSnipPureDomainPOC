import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class VinSnipShould {

    @Test
    public void
    provide_stored_snippets() {
        VimSnip vimsnip = new VimSnip();

        vimsnip.save("ONE Arbol Balance Casa ''", "ONE bla bla bla");
        vimsnip.save("TWO Arbol Balance Casa ''", "TWO bla bla bla");
        Snippet retreivedSnippet1 = vimsnip.get("ONE Arbol Balance Casa ''");
        Snippet retreivedSnippet2 = vimsnip.get("TWO Arbol Casa Balance ''");

        assertThat(retreivedSnippet1.getBody(),is("ONE bla bla bla"));
        assertThat(retreivedSnippet1.getTitle(),is("ONE Arbol Balance Casa ''"));
        assertThat(retreivedSnippet2.getBody(),is("bla bla bla TWO"));
        assertThat(retreivedSnippet2.getTitle(),is("TWO Arbol Balance Casa ''"));
    }
}
