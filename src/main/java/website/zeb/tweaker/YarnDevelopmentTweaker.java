package website.zeb.tweaker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class YarnDevelopmentTweaker implements ITweaker {

    private List<String> arguments = new ArrayList<>();

    @Override
    public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
        arguments.addAll(args);

        if (!args.contains("--version") && profile != null) {
            arguments.add("--version");
            arguments.add(profile);
        }
        if (!args.contains("--assetDir") && assetsDir != null) {
            arguments.add("--assetDir");
            arguments.add(assetsDir.getAbsolutePath());
        }
        if (!args.contains("--gameDir") && gameDir != null) {
            arguments.add("--gameDir");
            arguments.add(gameDir.getAbsolutePath());
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader classLoader) {
        classLoader.registerTransformer("website.zeb.transformer.PublicForcingClassTransformer");

        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.yarn-tweaker.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        MixinBootstrap.getPlatform().inject();
    }

    @Override
    public String getLaunchTarget() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public String[] getLaunchArguments() {
        return this.arguments.toArray(new String[this.arguments.size()]); // returns empty array in production tweaker.
    }

}