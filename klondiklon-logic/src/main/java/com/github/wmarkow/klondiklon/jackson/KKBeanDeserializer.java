package com.github.wmarkow.klondiklon.jackson;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.github.wmarkow.klondiklon.Klondiklon;
import com.github.wmarkow.klondiklon.ServiceRegistry;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.processes.GrowGardenPlantSimulationProcess;
import com.github.wmarkow.klondiklon.simulation.processes.RestoreEnergySimulationProcess;

public class KKBeanDeserializer extends BeanDeserializer
{
    private static final long serialVersionUID = -3404540800090973705L;

    public KKBeanDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties,
            Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown,
            boolean hasViews) {
        super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
    }

    @Override
    public java.lang.Object deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException
    {
        Object result = super.deserialize(p, ctxt);

        if (result instanceof Player)
        {
            ((Player) result).setEventBus(ServiceRegistry.getInstance().getEventBus());
        }
        if (result instanceof RestoreEnergySimulationProcess)
        {
            ((RestoreEnergySimulationProcess) result).setPlayer(Klondiklon.gameplayService.getPlayer());
        }
        if (result instanceof GrowGardenPlantSimulationProcess)
        {
            ((GrowGardenPlantSimulationProcess) result).setMap(Klondiklon.gameplayService.getCurrentWorldMap());
        }

        return result;
    }
}
