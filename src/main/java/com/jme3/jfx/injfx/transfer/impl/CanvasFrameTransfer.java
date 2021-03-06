package com.jme3.jfx.injfx.transfer.impl;

import com.jme3.jfx.injfx.processor.FrameTransferSceneProcessor.TransferMode;
import com.jme3.texture.FrameBuffer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class for transferring content from the jME to {@link Canvas}.
 *
 * @author JavaSaBr
 */
public class CanvasFrameTransfer extends AbstractFrameTransfer<Canvas> {

    @NotNull
    private GraphicsContext g;

    public CanvasFrameTransfer(@NotNull Canvas canvas, @NotNull TransferMode transferMode, int width, int height) {
        this(canvas, transferMode, null, width, height);
    }

    public CanvasFrameTransfer(
            @NotNull Canvas canvas,
            @NotNull TransferMode transferMode,
            @Nullable FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        super(canvas, transferMode, frameBuffer, width, height);
        g = canvas.getGraphicsContext2D();
        g.setFill(new Color(1, 1, 1, 1));
    }

    @Override
    protected void writeFrame() {
        pixelBuffer.updateBuffer(pixBuf -> updatedBuffer);

        g.fillRect(0, 0, width, height);
        g.drawImage(img, 0, 0);
    }
}
