package io.github.fabriccompatibilitylayers.fabricatedrift.modmenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class FakeGuiScreen extends GuiScreen {
    private final GuiScreen parent;
    private final Runnable guiOpener;

    public FakeGuiScreen(GuiScreen parent, Runnable guiOpener) {
        this.parent = parent;
        this.guiOpener = guiOpener;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        super.setWorldAndResolution(mc, width, height);

        this.mc.displayGuiScreen(this.parent);
        guiOpener.run();
    }
}
