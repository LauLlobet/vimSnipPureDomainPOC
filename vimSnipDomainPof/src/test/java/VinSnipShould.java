import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VinSnipShould {

    private VimSnip vimsnip;

    @Mock
    private SnippetsRepository snippetRepository;

    @Before
    public void setUp() {
        vimsnip =  new VimSnip(new SnippetsRepository());
    }

    @Test
    public void
    provide_stored_snippets() {

        vimsnip.save("ONE Arbol Balance Casa", "ONE bla bla bla");
        vimsnip.save("TWO Arbol Balance Casa", "TWO bla bla bla");
        Snippet retreivedSnippet1 = vimsnip.get("ONE Arbol Balance Casa");
        Snippet retreivedSnippet2 = vimsnip.get("TWO Arbol Casa Balance");


        assertThat(retreivedSnippet1.getTitleString(),is("Arbol Balance Casa ONE"));
        assertThat(retreivedSnippet1.getBody(),is("ONE bla bla bla"));
        assertThat(retreivedSnippet2.getTitleString(),is("Arbol Balance Casa TWO"));
        assertThat(retreivedSnippet2.getBody(),is("TWO bla bla bla"));
    }

    @Test
    public void
    update_an_snippet_content_upgrading_its_version() {
        vimsnip = new VimSnip(snippetRepository);
        given(snippetRepository.has(any())).willReturn(true);

        vimsnip.save("keyword1 keyword2","an updated body");

        verify(snippetRepository).save(argThat(snippet -> snippet.getTitleString().equals( "' keyword1 keyword2") ) );
    }
}
