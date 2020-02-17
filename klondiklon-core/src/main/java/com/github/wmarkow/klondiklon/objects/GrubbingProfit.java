package com.github.wmarkow.klondiklon.objects;

public class GrubbingProfit
{
    private String storageItem;
    private int amount;

    public GrubbingProfit(String storageItem, int amount) {
        this.storageItem = storageItem;
        this.amount = amount;
    }

    public String getStorageItem()
    {
        return storageItem;
    }

    public int getAmount()
    {
        return amount;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + amount;
        result = prime * result + ((storageItem == null) ? 0 : storageItem.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GrubbingProfit other = (GrubbingProfit) obj;
        if (amount != other.amount)
            return false;
        if (storageItem == null)
        {
            if (other.storageItem != null)
                return false;
        } else if (!storageItem.equals(other.storageItem))
            return false;
        return true;
    }
}
