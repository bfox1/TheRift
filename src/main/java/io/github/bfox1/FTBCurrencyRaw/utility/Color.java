package io.github.bfox1.FTBCurrencyRaw.utility;

public enum Color
{
    BLACK(0),
    DARKBLUE(1),
    DARKGREEN(2),
    LIGHTBLUE(3),
    DARKRED(4),
    PURPLE(5),
    ORANGE(6),
    LIGHTGREY(7),
    DARKGREY(8),
    BLUE(9),
    LIGHTGREEN("a"),
    CYAN("b"),
    LIGHRED("c"),
    PINK("d"),
    YELLOW("e"),
    WHITE("f"),
    RANDOM("k");

    private final String colorCode;

    Color(String colorCode)
    {
        this.colorCode = colorCode;
    }

    Color(int colorCode)
    {
        this.colorCode = String.valueOf(colorCode);
    }

    public String getColorCode()
    {
        return "\247"+colorCode;
    }
}
