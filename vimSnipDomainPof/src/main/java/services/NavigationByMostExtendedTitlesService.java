package services;

import domain.SnippetTitle;
import services.exceptions.NotAQueryFormattedTitleBecauseItContainsVersionNumber;

import java.util.ArrayList;

public class NavigationByMostExtendedTitlesService {
    private SubSetsOfTitlesService subSetsService;

    public NavigationByMostExtendedTitlesService(SubSetsOfTitlesService subSetsService) {
        this.subSetsService = subSetsService;
    }

    public ArrayList<SnippetTitle> get(SnippetTitle rootTitle) {
        if( ! rootTitle.isVersionZero()){
            throw new NotAQueryFormattedTitleBecauseItContainsVersionNumber();
        }
        return new ArrayList<>( subSetsService.subsetsOf(rootTitle) );
    }
}
