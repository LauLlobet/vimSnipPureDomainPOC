import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VimSnipShould {

    private VimSnip vimsnip;

    @Mock
    private SnippetsRepository snippetRepository;

    @Before
    public void setUp() {
        vimsnip = new VimSnip(snippetRepository);
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

    @Test(expected=NotCorrectVersionOfSnippetToSave.class)
    public void
    dont_save_a_snippet_that_already_exists() {
        given(snippetRepository.has(new Snippet("keyword1 keyword2 '' "))).willReturn(true);

        vimsnip.save("keyword1 keyword2'' ","a second updated body");

        verify(snippetRepository,never()).save(argThat(snippet -> snippet.getTitleString().equals( "'' keyword3 keyword4") ) );
    }

    @Test(expected = NoSuchElementException.class)
    public void
    not_allow_getting_a_nonexistent_snippet() {
        given(snippetRepository.hasNotSnippetWith(new SnippetTitle("A'"))).willReturn(true);

        vimsnip.get("A'");
    }

    @Test
    public void
    provide_newest_snippet_version_if_it_is_not_specified() {
        given(snippetRepository.get(new SnippetTitle("A'"))).willReturn(new Snippet("A'"));
        given(snippetRepository.get(new SnippetTitle("A''"))).willReturn(new Snippet("A''"));

        Snippet retrivedSnippet = vimsnip.get("A");

        assertThat(retrivedSnippet,is(new Snippet("A ' ")));
    }
}

