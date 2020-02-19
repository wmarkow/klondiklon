package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.ui.KKUi;
import com.github.wmarkow.klondiklon.warehouse.Warehouse;

public class Klondiklon
{
    public final static ObjectTypeDescriptorsManager objectTypeDescriptorsManager = new ObjectTypeDescriptorsManager();
    public final static StorageItemDescriptorsManager storageItemDescriptorsManager = new StorageItemDescriptorsManager();
    public final static Warehouse warehouse = new Warehouse();

    public static KKUi ui;
}
