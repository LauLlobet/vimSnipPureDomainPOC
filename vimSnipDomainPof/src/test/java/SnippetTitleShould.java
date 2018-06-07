import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

public class SnippetTitleShould {
    @Test
    public void
    be_equal_to_another_title_that_has_the_same_keywords() {
       assertEquals(new SnippetTitle("Arbol    Balance Casa 0"),new SnippetTitle("Casa Balance Arbol 0"));
    }

    @Test
    public void
    be_serializable() {
       assertEquals(new SnippetTitle("Balance Arbol    Casa 0").toString(), "Arbol Balance Casa 0");
    }

    @Test
    public void
    become_zero_version_having_been_constructed_without_number_in_the_title() {
        SnippetTitle snippetTitle = new SnippetTitle("Arbol Casa 0");
        assertThat(snippetTitle.toString(),is("Arbol Casa 0"));
    }

    @Test(expected= TwoOrMoreVersionsProvidedForSnippetCreation.class)
    public void
    prevent_from_creating_itself_with_two_numbers() {
        new SnippetTitle("Arbol 2 3");
    }

    @Test
    public void
    be_version_0_having_his_argument_with_no_number() {
        SnippetTitle snippetTitle = new SnippetTitle("Arbol Casa");
        assertThat(snippetTitle.getVersion(),is(0));
        assertThat(snippetTitle.toString(),is("Arbol Casa 0"));
    }

    @Test
    public void
    be_version_N_having_his_argument_with_a_N_number() {
        SnippetTitle snippetTitle = new SnippetTitle("Arbol Casa 36");
        assertThat(snippetTitle.getVersion(),is(36));
    }

    @Test
    public void
    upgrade_its_version() {
        assertThat(new SnippetTitle("Arbol Casa 36").upgradeVersion().toString(),is("Arbol Casa 37"));
    }

    @Test
    public void
    downgrade_its_version() {
       assertThat(new SnippetTitle("Arbol Casa 36").downgradedVersion().toString(),is("Arbol Casa 35"));
    }


    @Test
    public void
    distinct_same_title_with_different_versions() {
       assertNotEquals(new SnippetTitle("Arbol Casa 1" ),new SnippetTitle("Arbol Casa 2").upgradeVersion());
    }




}
