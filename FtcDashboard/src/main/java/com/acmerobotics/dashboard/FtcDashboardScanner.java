package com.acmerobotics.dashboard;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.config.reflection.ReflectionConfig;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import dev.frozenmilk.sinister.Scanner;
import dev.frozenmilk.sinister.sdk.apphooks.SDKOpModeRegistrar;
import dev.frozenmilk.sinister.sdk.apphooks.SinisterOpModeRegistrarScanner;
import dev.frozenmilk.sinister.sdk.opmodes.OpModeScanner;
import dev.frozenmilk.sinister.targeting.NarrowSearch;
import dev.frozenmilk.sinister.targeting.SearchTarget;
import dev.frozenmilk.sinister.util.log.Logger;
import dev.frozenmilk.util.graph.Graph;
import dev.frozenmilk.util.graph.rule.AdjacencyRule;
import dev.frozenmilk.util.graph.rule.AdjacencyRules;

@SuppressWarnings("unused")
public final class FtcDashboardScanner implements Scanner {
    public static final FtcDashboardScanner INSTANCE = new FtcDashboardScanner();
    public static final String TAG = FtcDashboardScanner.class.getSimpleName();
    private FtcDashboardScanner() {}
    @Override
    public void scan(@NonNull ClassLoader classLoader, @NonNull Class<?> cls) {
        Config annotation = cls.getAnnotation(Config.class);
        if (annotation == null
                || cls.isAnnotationPresent(Disabled.class)) {
            return;
        }

        String name = cls.getSimpleName();
        String altName = annotation.value();
        FtcDashboard.getInstance().withConfigRoot(root -> {
            if (!altName.isEmpty()) {
                Logger.v(TAG, "Registering @Config class " + cls.getName() + " named " + altName);
                root.putVariable(altName, ReflectionConfig.createVariableFromClass(cls));
            }
            else {
                Logger.v(TAG, "Registering @Config class " + cls.getName() + " named " + name);
                root.putVariable(name, ReflectionConfig.createVariableFromClass(cls));
            }
        });
    }

    @Override
    public void afterScan(@NonNull ClassLoader loader) {
        Logger.v(TAG, "updating OpMode List");
        FtcDashboard.getInstance().sendOpModes();
    }

    @Override
    public void unload(@NonNull ClassLoader classLoader, @NonNull Class<?> cls) {
        Config annotation = cls.getAnnotation(Config.class);
        if (annotation == null
                || cls.isAnnotationPresent(Disabled.class)) {
            return;
        }

        String name = cls.getSimpleName();
        String altName = annotation.value();
        FtcDashboard.getInstance().withConfigRoot(root -> {
            if (!altName.isEmpty()) {
                root.removeVariable(altName);
            }
            else {
                root.removeVariable(name);
            }
        });
    }

    @Override
    public void afterUnload(@NonNull ClassLoader loader) {
        Logger.v(TAG, "updating OpMode List");
        FtcDashboard.getInstance().sendOpModes();
    }

    private final SearchTarget searchTarget = new NarrowSearch();
    @NonNull
    @Override
    public SearchTarget getTargets() {
        return searchTarget;
    }

    // runs after opmode scanning
    private final AdjacencyRule<Scanner, Graph<Scanner>> loadAdjacencyRule =
            AdjacencyRules.dependsOnClass(this, OpModeScanner.class)
                    .and(AdjacencyRules.dependsOn(this, SDKOpModeRegistrar.INSTANCE));
    @NonNull
    @Override
    public AdjacencyRule<Scanner, Graph<Scanner>> getLoadAdjacencyRule() {
        return loadAdjacencyRule;
    }

    // runs after opmode scanning
    private final AdjacencyRule<Scanner, Graph<Scanner>> unloadAdjacencyRule =
            AdjacencyRules.dependsOnClass(this, OpModeScanner.class)
                    .and(AdjacencyRules.dependsOn(this, SDKOpModeRegistrar.INSTANCE));
    @NonNull
    @Override
    public AdjacencyRule<Scanner, Graph<Scanner>> getUnloadAdjacencyRule() {
        return unloadAdjacencyRule;
    }
};
