package services;

import domain.SnippetTitle;
import domain.WeighedSnippetTitle;
import services.exceptions.NotAQueryFormattedTitleBecauseItContainsVersionNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationByMostExtendedTitlesService {
    private SubSetsOfTitlesService subSetsService;

    public NavigationByMostExtendedTitlesService(SubSetsOfTitlesService subSetsService) {
        this.subSetsService = subSetsService;
    }

    public List<SnippetTitle> get(SnippetTitle rootTitle) {
        if( ! rootTitle.isVersionZero()){
            throw new NotAQueryFormattedTitleBecauseItContainsVersionNumber();
        }
        ArrayList<SnippetTitle> snippetTitles = new ArrayList<>(subSetsService.subsetsOf(rootTitle));
        return orderByNumberOfSons(snippetTitles);
    }

    private List<SnippetTitle> orderByNumberOfSons(ArrayList<SnippetTitle> snippetTitles) {
        List<WeighedSnippetTitle> weightedSnippetTitles = snippetTitles.stream().map(title -> this.toWeightedBySonsTitle(title,snippetTitles)).collect(Collectors.toList());
        Collections.sort(weightedSnippetTitles);
        return weightedSnippetTitles.stream().map(wtitle -> wtitle.getTitle()).collect(Collectors.toList());
    }

    private WeighedSnippetTitle toWeightedBySonsTitle(SnippetTitle title, ArrayList<SnippetTitle> snippetTitles) {
        int sons = findSons(title,snippetTitles);
        return new WeighedSnippetTitle(title,sons);
    }

    private int findSons(SnippetTitle title, ArrayList<SnippetTitle> snippetTitles) {
        int sons = 0;
        for (SnippetTitle titleOfAll:
             snippetTitles) {
            if(titleOfAll.isSonOf(title)){
                sons++;
            }
        }
        return sons;
    }
}
