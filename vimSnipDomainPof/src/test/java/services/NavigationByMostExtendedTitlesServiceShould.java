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
import static org.hamcrest.Matchers.contains;
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

    private SnippetTitle a_b_1    = new SnippetTitle("a b 1");

       private SnippetTitle a_b_h_1 = new SnippetTitle("a b h 1");

    private SnippetTitle a_d = new SnippetTitle("a d");

      private SnippetTitle a_d_1    = new SnippetTitle("a d 1");
      private SnippetTitle a_d_2    = new SnippetTitle("a d 2");
      private SnippetTitle a_d_3    = new SnippetTitle("a d 3");

        private SnippetTitle a_d_e_1 = new SnippetTitle("a d e 1");
        private SnippetTitle a_d_f_1 = new SnippetTitle("a d f 1");

            private SnippetTitle a_d_f_g_1 = new SnippetTitle("a d f g 1");



    @Test
    public void
    not_return_any_title_if_it_doesent_have_any_of_this_set() {
        assertThat(navigator.get(a_c), hasSize(0));
    }

    @Test(expected = NotAQueryFormattedTitleBecauseItContainsVersionNumber.class)
    public void
    not_allow_a_query_having_a_non_zero_version_title_as_query() {
        navigator.get(a_b_1);
    }

    @Test
    public void
    provide_titles_having_the_most_extended_subset_first() {
        subSetsServiceProvidesFor(a_c_b, setOf(a_b_1,
                                    a_b_h_1,
                                    a_d_1,
                                    a_d_e_1,
                                    a_d_f_1,
                                    a_d_f_g_1));
                                                    //4    //2    //2      //1      //1        //1         (number of sons)
        assertThat(navigator.get(a_c_b), contains(a_d_1, a_b_1, a_d_f_1, a_b_h_1, a_d_e_1, a_d_f_g_1));
    }

    @Test
    public void
    provide_titles_having_the_same_set_of_keywords_orederd_by_version_num() {
        subSetsServiceProvidesFor(a_d, setOf(a_d_2,a_d_1,a_d_3));

       assertThat(navigator.get(a_d),contains(a_d_1,a_d_2,a_d_3));
    }


    private List<SnippetTitle> setOf(SnippetTitle... titles) {
        return Arrays.asList(titles);
    }

    private void subSetsServiceProvidesFor(SnippetTitle snippetTitle, List<SnippetTitle> listOfTitles) {
        given(subSetsService.subsetsOf(snippetTitle)).willReturn(new HashSet<>(listOfTitles));
    }


}
