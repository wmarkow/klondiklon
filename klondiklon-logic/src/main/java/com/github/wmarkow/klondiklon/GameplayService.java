package com.github.wmarkow.klondiklon;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.mapeditor.io.TMXMapWriter;

import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.github.wmarkow.klondiklon.map.KKMapIf;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptor;
import com.github.wmarkow.klondiklon.player.Player;
import com.github.wmarkow.klondiklon.simulation.Simulable;
import com.github.wmarkow.klondiklon.simulation.Simulation;
import com.github.wmarkow.klondiklon.simulation.processes.GrowGardenPlantSimulationProcess;
import com.github.wmarkow.klondiklon.simulation.processes.RestoreEnergySimulationProcess;
import com.github.wmarkow.klondiklon.warehouse.Warehouse;
import com.github.wmarkow.klondiklon.worlds.WorldsManager;

public class GameplayService
{
    private WorldsManager worldsManager = new WorldsManager();
    private Player player;
    private Warehouse warehouse;
    private Simulation simulation;
    private KKMapIf currentWorldMap;

    public KKMapIf getCurrentWorldMap()
    {
        return currentWorldMap;

    }

    public Player getPlayer()
    {
        return player;
    }

    public Warehouse getWarehouse()
    {
        return warehouse;
    }

    public boolean simulateStep(long currentSimulationTimeInMillis)
    {
        return simulation.simulateStep(currentSimulationTimeInMillis);
    }

    public void loadGameContext()
    {
        // load world
        worldsManager.copyHomeWorldFromClasspathToInternal(false);
        currentWorldMap = worldsManager.readHomeWorld();

        player = loadPlayer();
        warehouse = loadWarehouse();

        // load simulation
        simulation = loadSimulation();
        simulation.catchUp(Instant.now().toEpochMilli());
    }

    public void saveGameContext()
    {
        // save world
        TMXMapWriter tmxMapWriter = new TMXMapWriter();
        tmxMapWriter.settings.compressLayerData = false;
        tmxMapWriter.settings.layerEncodingMethod = TMXMapWriter.Settings.LAYER_ENCODING_METHOD_CSV;

        String tmxFilePath = Gdx.files.getLocalStoragePath() + "/worlds/home/home.tmx";
        try
        {
            tmxMapWriter.writeMap(currentWorldMap.getTmxMap(), tmxFilePath);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        savePlayer(player);
        saveWarehouse(warehouse);
        saveSimulation(simulation);
    }

    public void addGardenSimulation(int gardenId, StorageItemDescriptor seedItemDescriptor)
    {
        simulation.addSimulable(new GrowGardenPlantSimulationProcess(gardenId, seedItemDescriptor.getStorageItemType(),
                currentWorldMap));
    }

    public void stopGardenSimulation(int gardenId)
    {
        for (Simulable simulable : simulation.getSimulables())
        {
            if (simulable instanceof GrowGardenPlantSimulationProcess == false)
            {
                continue;
            }

            GrowGardenPlantSimulationProcess ggpsp = (GrowGardenPlantSimulationProcess) simulable;
            if (ggpsp.getGardenObjectId() == gardenId)
            {
                simulation.removeSimulable(simulable);

                return;
            }
        }
    }

    private void savePlayer(Player player)
    {
        ObjectMapper mapper = createObjectMapper();
        String filePath = Gdx.files.getLocalStoragePath() + "/player.json";
        try
        {
            mapper.writeValue(new File(filePath), player);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Player loadPlayer()
    {
        ObjectMapper mapper = createObjectMapper();
        String filePath = Gdx.files.getLocalStoragePath() + "/player.json";

        try
        {
            return mapper.readValue(new File(filePath), Player.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveWarehouse(Warehouse warehouse)
    {
        ObjectMapper mapper = createObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/warehouse.json";

        try
        {
            mapper.writeValue(new File(filePath), warehouse);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Warehouse loadWarehouse()
    {
        ObjectMapper mapper = createObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/warehouse.json";

        try
        {
            return mapper.readValue(new File(filePath), Warehouse.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void saveSimulation(Simulation simulation)
    {
        ObjectMapper mapper = createObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/simulation.json";

        try
        {
            mapper.writeValue(new File(filePath), simulation);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Simulation loadSimulation()
    {
        // long epochMilli = Instant.now().toEpochMilli();
        // simulation = new Simulation(epochMilli);
        // simulation.addSimulable(new RestoreEnergySimulationProcess(player));

        ObjectMapper mapper = createObjectMapper();

        String filePath = Gdx.files.getLocalStoragePath() + "/simulation.json";

        try
        {
            return mapper.readValue(new File(filePath), Simulation.class);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper createObjectMapper()
    {
        ObjectMapper mapper = new CustomObjectMapper();
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    private class KKBeanDeserializer extends BeanDeserializer
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
                ((RestoreEnergySimulationProcess) result).setPlayer(player);
            }
            if (result instanceof GrowGardenPlantSimulationProcess)
            {
                ((GrowGardenPlantSimulationProcess) result).setMap(currentWorldMap);
            }

            return result;
        }
    }

    private class CustomObjectMapper extends ObjectMapper
    {
        private static final long serialVersionUID = 7862801075351651020L;

        public CustomObjectMapper() {
            this(null, null, null);
        }

        public CustomObjectMapper(JsonFactory jf, DefaultSerializerProvider sp, DefaultDeserializationContext dc) {
            super(jf, sp, dc);

            _deserializationContext = (dc == null)
                    ? new DefaultDeserializationContext.Impl(new KKBeanDeserializerFactory())
                    : dc;
        }
    }

    private class KKBeanDeserializerFactory extends BeanDeserializerFactory
    {
        private static final long serialVersionUID = -5418900912456305680L;

        public KKBeanDeserializerFactory() {
            super(new DeserializerFactoryConfig());
        }

        protected BeanDeserializerBuilder constructBeanDeserializerBuilder(DeserializationContext ctxt,
                BeanDescription beanDesc)
        {
            return new KKBeanDeserializerBuilder(beanDesc, ctxt);
        }
    }

    private class KKBeanDeserializerBuilder extends BeanDeserializerBuilder
    {
        public KKBeanDeserializerBuilder(BeanDescription beanDesc, DeserializationContext ctxt) {
            super(beanDesc, ctxt);
        }

        public JsonDeserializer<?> build()
        {
            Collection<SettableBeanProperty> props = _properties.values();
            _fixAccess(props);
            BeanPropertyMap propertyMap = BeanPropertyMap.construct(props,
                    _config.isEnabled(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES), _collectAliases(props));
            propertyMap.assignIndexes();

            // view processing must be enabled if:
            // (a) fields are not included by default (when deserializing with view), OR
            // (b) one of properties has view(s) to included in defined
            boolean anyViews = !_config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
            if (!anyViews)
            {
                for (SettableBeanProperty prop : props)
                {
                    if (prop.hasViews())
                    {
                        anyViews = true;
                        break;
                    }
                }
            }

            // one more thing: may need to create virtual ObjectId property:
            if (_objectIdReader != null)
            {
                /*
                 * 18-Nov-2012, tatu: May or may not have annotations for id property; but no
                 * easy access. But hard to see id property being optional, so let's consider
                 * required at this point.
                 */
                ObjectIdValueProperty prop = new ObjectIdValueProperty(_objectIdReader, PropertyMetadata.STD_REQUIRED);
                propertyMap = propertyMap.withProperty(prop);
            }

            return new KKBeanDeserializer(this, _beanDesc, propertyMap, _backRefProperties, _ignorableProps,
                    _ignoreAllUnknown, anyViews);
        }
    }
}
