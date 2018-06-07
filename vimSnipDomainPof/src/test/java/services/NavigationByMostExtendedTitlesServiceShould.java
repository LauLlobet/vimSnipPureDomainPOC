package services;

import domain.SnippetTitle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import services.exceptions.NotAQueryFormattedTitleBecauseItContainsVersionNumber;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class NavigationByMostExtendedTitlesServiceShould {

    private NavigationByMostExtendedTitlesService navigator;

    @Mock
    private SubSetsOfTitlesService subSetsService;

    @Before
    public void setUp() {
        navigator = new NavigationByMostExtendedTitlesService(subSetsService);
    }


    private SnippetTitle a_c        = new SnippetTitle("a c");
    private SnippetTitle a_c_b      = new SnippetTitle("a c b");
    private SnippetTitle a_c_b_1    = new SnippetTitle("a c b 1");


    @Test
    public void
    not_return_any_title_if_it_doesent_have_any_of_this_set() {
        assertThat(navigator.get(a_c), hasSize(0));
    }

    @Test(expected = NotAQueryFormattedTitleBecauseItContainsVersionNumber.class)
    public void
    not_allow_a_query_having_a_non_zero_version_title_as_query() {
        navigator.get(a_c_b_1);
    }

    @Test
    public void
    return_one_title_if_its_exteneded_from_the_query_one() {
        subSetsService(a_c_b, setOf(a_c_b));

        assertThat(navigator.get(a_c_b), hasSize(1));
    }


    private List<SnippetTitle> setOf(SnippetTitle... titles) {
        return Arrays.asList(titles);
    }

    private void subSetsService(SnippetTitle snippetTitle, List<SnippetTitle> listOfTitles) {
        given(subSetsService.subsetsOf(snippetTitle)).willReturn(new HashSet<SnippetTitle>(listOfTitles));
    }


}
