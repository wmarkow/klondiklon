package com.github.wmarkow.klondiklon.objects;

public class GrubbingProfit
{
    private StorageItemDescriptor storageItemDescriptor;
    private int amount;

    public GrubbingProfit(StorageItemDescriptor storageItemDescriptor, int amount) {
        if (storageItemDescriptor == null)
        {
            throw new IllegalArgumentException("StorageItemDescriptor may not be null");
        }
        if (amount < 0)
        {
            throw new IllegalArgumentException("Amount may not be negative");
        }

        this.storageItemDescriptor = storageItemDescriptor;
        this.amount = amount;
    }

    public StorageItemDescriptor getStorageItemDescriptor()
    {
        return storageItemDescriptor;
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
        result = prime * result + ((storageItemDescriptor == null) ? 0 : storageItemDescriptor.hashCode());
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
        if (storageItemDescriptor == null)
        {
            if (other.storageItemDescriptor != null)
                return false;
        } else if (!storageItemDescriptor.equals(other.storageItemDescriptor))
            return false;
        return true;
    }
}
