package io.github.bfox1.FTBCurrencyRaw;

import java.util.UUID;

public class FoxCurrency
{

    private int currency;

    public FoxCurrency()
    {
        this.currency = 0;
    }

    /**
     * Get the players current Currency.
     * @return
     */
    public int getCurrency() {
        return currency;
    }

    /**
     * Set the players Currency.
     * This method should only be used under specific actions.
     * @param currency
     */
    public final void setCurrency(int currency)
    {
        this.currency = currency;
    }

    /**
     * Add currency to the player.
     * @param add
     */
    public void addCurrency(int add)
    {
        this.currency += add;
    }

    /**
     * Subtract currency to the player.
     * @param sub
     */
    public void subtractCurrency(int sub)
    {
        this.currency -= sub;
    }
}
