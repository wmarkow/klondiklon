package com.github.wmarkow.klondiklon;

import com.github.wmarkow.klondiklon.objects.ObjectTypeDescriptorsManager;
import com.github.wmarkow.klondiklon.objects.StorageItemDescriptorsManager;
import com.github.wmarkow.klondiklon.ui.KKUi;

public class Klondiklon
{
    public final static ObjectTypeDescriptorsManager objectTypeDescriptorsManager = new ObjectTypeDescriptorsManager();
    public final static StorageItemDescriptorsManager storageItemDescriptorsManager = new StorageItemDescriptorsManager();

    public static KKUi ui;
}
