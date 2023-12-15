package com.gmail.luizjmfilho.sevenwonders.ui

data class CalculationUiState(
    val subScreen: CalculationSubScreen = CalculationSubScreen.ParcialGrid,
    val playersList: List<String> = List(7) { "" },
    val currentCategory: PointCategory = PointCategory.WonderBoard,
    val totalScoreList: List<Int> = List(7) {0},
    val wonderBoardScoreList: List<Int> = List(7) {0},
    val coinScoreList: List<Int> = List(7) {0},
    val warScoreList: List<Int> = List(7) {0},
    val blueCardScoreList: List<Int> = List(7) {0},
    val yellowCardScoreList: List<Int> = List(7) {0},
    val greenCardScoreList: List<Int> = List(7) {0},
    val purpleCardScoreList: List<Int> = List(7) {0},
    val scienceSymbolsCurrentQuantityList: List<Int> = List(3) {0},
)

enum class PointCategory {
    WonderBoard,
    Coin,
    War,
    BlueCard,
    YellowCard,
    GreenCard,
    PurpleCard;
}

enum class CalculationSubScreen {
    TotalGrid,
    ParcialGrid,
    ScienceGrid,
}

enum class ScienceSymbol {
    Compass,
    Stone,
    Gear,
}