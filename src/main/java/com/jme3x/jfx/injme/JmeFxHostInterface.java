/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3x.jfx.injme;

import com.jme3x.jfx.util.JFXPlatform;
import com.ss.rlib.common.logging.Logger;
import com.ss.rlib.common.logging.LoggerLevel;
import com.ss.rlib.common.logging.LoggerManager;
import com.sun.javafx.cursor.CursorFrame;
import com.sun.javafx.embed.AbstractEvents;
import com.sun.javafx.embed.EmbeddedSceneInterface;
import com.sun.javafx.embed.EmbeddedStageInterface;
import com.sun.javafx.embed.HostInterface;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The implementation of {@link HostInterface} to listen requests or notifications from embedded scene.
 *
 * @author JavaSaBr
 */
public class JmeFxHostInterface implements HostInterface {

    @NotNull
    private static final Logger LOGGER = LoggerManager.getLogger(JFXPlatform.class);

    /**
     * The JavaFX container.
     */
    @NotNull
    private final JmeFXContainerInternal container;

    public JmeFxHostInterface(@NotNull final JmeFXContainerInternal container) {
        this.container = container;
    }

    /**
     * Gets the JavaFX container.
     *
     * @return the JavaFX container.
     */
    private @NotNull JmeFXContainerInternal getContainer() {
        return container;
    }

    @Override
    public boolean grabFocus() {
        LOGGER.debug(this, "Called grabFocus()");
        return true;
    }

    @Override
    public void repaint() {
        LOGGER.debug(this, "Called repaint()");
        getContainer().requestRedraw();
    }

    @Override
    public boolean requestFocus() {
        LOGGER.debug(this, "Called requestFocus()");
        return getContainer().requestFocus();
    }

    @Override
    public void setCursor(@NotNull final CursorFrame cursorFrame) {
        LOGGER.debug(this, cursorFrame, frame -> "Called setCursor(" + frame + ")");
        getContainer().requestShowingCursor(cursorFrame);
    }

    @Override
    public void setEmbeddedScene(@Nullable final EmbeddedSceneInterface sceneInterface) {

        if (sceneInterface == null) {
            LOGGER.debug(this, "Called setEmbeddedScene(null)");
        } else {
            LOGGER.debug(this, sceneInterface, scene -> "Called setEmbeddedScene(" + scene + ")");
        }

        final JmeFXContainerInternal container = getContainer();
        final EmbeddedSceneInterface currentSceneInterface = container.getSceneInterface();
        if (currentSceneInterface != null) {
            // FIXME release all things
        }

        container.setSceneInterface(sceneInterface);

        if (sceneInterface == null) {
            return;
        }

        var scaleFactor = container.getPixelScaleFactor();

        sceneInterface.setPixelScaleFactors(scaleFactor, scaleFactor);

        final int width = container.getSceneWidth();
        final int height = container.getSceneHeight();

        if (width > 0 && height > 0) {
            sceneInterface.setSize(width, height);
        }

        sceneInterface.setDragStartListener(new JmeFxDNDHandler(container));
    }

    @Override
    public void setEmbeddedStage(@Nullable final EmbeddedStageInterface stageInterface) {

        if (stageInterface == null) {
            LOGGER.debug(this, "Called setEmbeddedStage(null)");
        } else {
            LOGGER.debug(this, stageInterface, stage -> "Called setEmbeddedStage(" + stage + ")");
        }

        final JmeFXContainerInternal container = getContainer();
        final EmbeddedStageInterface currentStageInterface = container.getStageInterface();
        if (currentStageInterface != null) {
            // FIXME release all things
        }

        container.setStageInterface(stageInterface);

        if (stageInterface == null) {
            return;
        }

        final int width = container.getSceneWidth();
        final int height = container.getSceneHeight();

        if (width > 0 && height > 0) {
            stageInterface.setSize(width, height);
        }

        stageInterface.setFocused(true, AbstractEvents.FOCUSEVENT_ACTIVATED);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        LOGGER.debug(this, enabled, val -> "Called setEnabled(" + val + ")");
        getContainer().requestEnabled(enabled);
    }

    @Override
    public void setPreferredSize(final int width, final int height) {

        if (LOGGER.isEnabled(LoggerLevel.DEBUG)) {
            LOGGER.debug(this, "Called setPreferredSize(" + width + ", " + height + ")");
        }

        getContainer().requestPreferredSize(width, height);
    }

    @Override
    public boolean traverseFocusOut(final boolean forward) {
        LOGGER.debug(this, forward, val -> "Called traverseFocusOut(" + val + ")");
        return true;
    }

    @Override
    public void ungrabFocus() {
        LOGGER.debug(this, "Called ungrabFocus()");
    }
}
