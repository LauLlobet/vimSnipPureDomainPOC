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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VinSnipShould {

    private VimSnip vimsnip;

    @Mock
    private SnippetsRepository snippetRepository;

    @Before
    public void setUp() {
        vimsnip = new VimSnip(snippetRepository);
    }

    @Test
    public void
    provide_stored_snippets() {
        vimsnip =  new VimSnip(new SnippetsRepository());

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
    save_a_snippet_having_its_version_as_the_latest() {
        given(snippetRepository.has(new Snippet("' keyword3 keyword4"))).willReturn(true);
        given(snippetRepository.has(new Snippet("keyword3 keyword4 '' "))).willReturn(false);

        vimsnip.save("keyword3 keyword4''","a second updated body");
        verify(snippetRepository).save(argThat(snippet -> snippet.getTitleString().equals( "'' keyword3 keyword4") ) );
    }

    @Test
    public void
    save_a_first_version_snippet_which_has_not_been_stored_before() {
        given(snippetRepository.has(new Snippet("A"))).willReturn(true);
        given(snippetRepository.has(new Snippet("' A"))).willReturn(false);

        vimsnip.save("A '","body");

        verify(snippetRepository).save(argThat((snippet) -> { assertThat(snippet.getTitleString(),is("' A")); return true; }));
    }

    @Test(expected=NotCorrectVersionOfSnippetToSave.class)
    public void
    dont_save_a_snippet_having_its_version_ahead_of_the_lastest() {
        given(snippetRepository.has(new Snippet("keyword1 keyword2 '' "))).willReturn(false);
        given(snippetRepository.has(new Snippet("keyword1 keyword2 ' "))).willReturn(false);

        vimsnip.save("keyword1 keyword2'' ","a second updated body");

        verify(snippetRepository,never()).save(argThat(snippet -> snippet.getTitleString().equals( "'' keyword3 keyword4") ) );
    }
}
