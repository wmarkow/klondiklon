package com.github.wmarkow.klondiklon.objects;

import java.util.HashSet;
import java.util.Set;

import com.github.wmarkow.klondiklon.home.objects.StorageItems;

public class ObjectTypeDescriptor
{
    private String type;
    private String name;
    private GrubbingType grubbingType = GrubbingType.NONE;
    private int energyToGrubb = 0;
    private Set<GrubbingProfit> grubbingProfits = new HashSet<GrubbingProfit>();

    public ObjectTypeDescriptor(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public GrubbingType getGrubbingType()
    {
        return grubbingType;
    }

    public int getEnergyToGrubb()
    {
        return energyToGrubb;
    }

    public String getName()
    {
        return name;
    }

    public Set<GrubbingProfit> getGrubbingProfits()
    {
        grubbingProfits = new HashSet<GrubbingProfit>();
        grubbingProfits.add(new GrubbingProfit(StorageItems.ICE, 4));
        
        return grubbingProfits;
    }

    public ObjectTypeDescriptor setGrubbingType(GrubbingType grubbingType)
    {
        this.grubbingType = grubbingType;

        return this;
    }

    public ObjectTypeDescriptor setEnergyToGrubb(int energyToGrubb)
    {
        this.energyToGrubb = energyToGrubb;

        return this;
    }

}
