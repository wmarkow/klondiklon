package com.github.wmarkow.klondiklon.objects;

import java.util.HashSet;
import java.util.Set;

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
        return grubbingProfits;
    }
    
    public void addGrubbingProfit(GrubbingProfit grubbingProfit)
    {
        grubbingProfits.add(grubbingProfit);
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
