package services;

import domain.Snippet;
import domain.SnippetTitle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.exceptions.NotCorrectVersionOfSnippetToSave;
import services.SnippetSaver;
import services.SnippetsProviderService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SnippetSaverShould { //TODO: naming, should it be called service? Will it be anemic if so? can it be a wrapper of the repo/snippetsService

    private SnippetSaver vimsnip;

    @Mock
    private SnippetsProviderService snippetService;

    private String version1Title = "title 1";
    private String version2Title = "title 2";
    private String zeroVersionTitle = "title";

    @Before
    public void setUp() {
        vimsnip = new SnippetSaver(snippetService);
    }

    @Test
    public void
    save_a_snippet_having_its_version_as_the_latest() {
        snippetServiceHasSnippetWithTitle(version1Title);
        snippetServiceHasNotSnippetWithTitle(version2Title);

        vimsnip.save(version2Title,"a body");

        verify(snippetService).save(argThat(snippet -> { assertThat(snippet.getTitleString(),is(version2Title)); return true;}) );
    }

    @Test
    public void
    save_a_first_version_of_snippet_having_no_previous_version_saved_before() {
        snippetServiceHasNotSnippetWithTitle(version1Title);

        vimsnip.save(version1Title,"a body");

        verify(snippetService).save(argThat((snippet) -> { assertThat(snippet.getTitleString(),is(version1Title)); return true; }));
    }

    @Test
    public void
    save_a_Nth_version_of_snippet_having_no_a_N_minus_1_version_saved_before() {
        snippetServiceHasNotSnippetWithTitle(version2Title);
        snippetServiceHasSnippetWithTitle(version1Title);

        vimsnip.save(version2Title,"a body");

        verify(snippetService).save(argThat((snippet) -> { assertThat(snippet.getTitleString(),is(version2Title)); return true; }));
    }

    @Test(expected= NotCorrectVersionOfSnippetToSave.class)
    public void
    dont_save_a_snippet_having_its_version_ahead_of_the_lastest() {
        snippetServiceHasNotSnippetWithTitle(version2Title);
        snippetServiceHasNotSnippetWithTitle(version1Title);

        vimsnip.save(version2Title,"a body");

        verify(snippetService,never()).save(argThat(snippet -> { assertThat(snippet.getTitleString(),is(version2Title)); return true; }) );
    }

    @Test(expected=NotCorrectVersionOfSnippetToSave.class)
    public void
    dont_save_a_snippet_that_already_exists() {
        snippetServiceHasSnippetWithTitle(version2Title);

        vimsnip.save(version2Title,"a body");

        verify(snippetService,never()).save(argThat(snippet -> snippet.getTitleString().equals(version2Title) ) );
    }

    @Test(expected=NotCorrectVersionOfSnippetToSave.class)
    public void
    dont_save_a_zero_version_snippet() {
        vimsnip.save(zeroVersionTitle,"a body");

        verify(snippetService,never()).save(argThat(snippet -> snippet.getTitleString().equals(version2Title) ) );
    }





    private void snippetServiceHasNotSnippetWithTitle(String title2) {
        given(snippetService.hasSnippetWith(new SnippetTitle(title2))).willReturn(false);
    }

    private void snippetServiceHasSnippetWithTitle(String title1) {
        given(snippetService.hasSnippetWith(new SnippetTitle(title1))).willReturn(true);
    }
}

