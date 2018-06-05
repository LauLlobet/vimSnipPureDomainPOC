import org.junit.Test;

import java.util.BitSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class VinSnipShould {

    @Test
    public void
    provide_stored_snippets() {
        VimSnip vimsnip = new VimSnip();
        String snippetBody = "bla bla bla";
        String snippetTitle = "Arbol Balance Casa ''";

        vimsnip.save(snippetTitle, snippetBody);
        Snippet retreivedSnippet = vimsnip.get(snippetTitle);

        assertThat(retreivedSnippet.getBody(),is(snippetBody));
    }
}
