package org.atom.api.shader;

import net.minecraft.client.shader.Framebuffer;
import org.atom.Client;
import org.atom.api.shader.blur.GaussianBloom;
import org.atom.api.shader.blur.GaussianBlur;
import org.atom.api.shader.blur.KawaseBloom;
import org.atom.api.shader.blur.KawaseBlur;
import org.atom.api.shader.misc.EventShader;
import org.atom.api.value.impl.BooleanValue;
import org.atom.api.value.impl.NumberValue;
import org.atom.api.value.impl.StringValue;
import org.atom.client.module.Category;
import org.atom.client.module.Module;
import org.atom.client.module.ModuleInfo;

@ModuleInfo(name = "PostProcessing",category = Category.Render)
public class PostProcessing extends Module {

    public static PostProcessing instance = new PostProcessing();
    public final StringValue blurMode = new StringValue("BlurMode", "GaussianBlur", "KawaseBlur", "KawaseBlur");
    public final BooleanValue blur = new BooleanValue("Blur", true);
    private final NumberValue iterations = new NumberValue("Blur Iterations", 5, 1, 8, 1);
    private final NumberValue offset = new NumberValue("Blur Offset", 5, 1, 10, 1);
    public final StringValue bloomMode = new StringValue("BlurMode", "GaussianBloom", "KawaseBloom", "KawaseBloom");
    private final BooleanValue bloom = new BooleanValue("Bloom", true);
    private final NumberValue shadowRadius = new NumberValue("Bloom Iterations", 5, 1, 8, 1);
    private final NumberValue shadowOffset = new NumberValue("Bloom Offset", 5, 1, 10, 1);


    public PostProcessing() {
        addValues(blurMode, blur, iterations, offset, bloomMode, bloom, shadowRadius, shadowOffset);
    }

    private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);

    public void blurScreen() {
        if (blur.getValue()) {

            if (blurMode.getValue() == "KawaseBlur") {
                stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);

                stencilFramebuffer.framebufferClear();
                stencilFramebuffer.bindFramebuffer(false);
                Client.getInstance().getEventManager().call(new EventShader(false));
                stencilFramebuffer.unbindFramebuffer();
                KawaseBlur.renderBlur(stencilFramebuffer.framebufferTexture, iterations.getValue().intValue(), offset.getValue().intValue());
            } else {
                GaussianBlur.startBlur();
                Client.getInstance().getEventManager().call(new EventShader(false));
                GaussianBlur.endBlur(iterations.getValue().intValue(), offset.getValue().intValue());
            }

        }


        if (bloom.getValue()) {
            stencilFramebuffer = RenderUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);

            Client.getInstance().getEventManager().call(new EventShader(true));

            stencilFramebuffer.unbindFramebuffer();

            if (bloomMode.getValue() == "KawaseBloom") {
                KawaseBloom.renderBlur(stencilFramebuffer.framebufferTexture, shadowRadius.getValue().intValue(), shadowOffset.getValue().intValue());
            } else {
                GaussianBloom.renderBlur(stencilFramebuffer.framebufferTexture, shadowRadius.getValue().intValue(), shadowOffset.getValue().intValue());
            }

        }
    }

}
