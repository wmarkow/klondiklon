package com.github.wmarkow.klondiklon.simulation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public interface Simulable
{
    public void stepEverySecond();

    public void stepInit();
}
