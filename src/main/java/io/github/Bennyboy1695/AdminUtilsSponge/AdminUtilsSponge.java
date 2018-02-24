package io.github.Bennyboy1695.AdminUtilsSponge;

import com.google.inject.Inject;
import io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Commands.*;
import io.github.Bennyboy1695.AdminUtilsSponge.Sponge.Handlers.EventTrackers;
import io.github.Bennyboy1695.AdminUtilsSponge.Utils.PermissionStrings;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.Setting;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

@Plugin(id = "adminutilssponge", name = "AdminUtilsSponge", description = "Sponge version of my Adminutils mod, this is mainly for perms on commands :P", authors = {"Bennyboy1695"})
public class AdminUtilsSponge {

    public static AdminUtilsSponge instance;

    @Inject
    public Logger logger;

    @Inject
    public PluginContainer pluginContainer;

    @Listener
    public void preInit(GamePreInitializationEvent event) {
        try {
            loadConfig();
            AdminUtilsSponge.getInstance().getLogger().info("Loading Config!");
        }catch (Exception e) {
            AdminUtilsSponge.getInstance().getLogger().info("Failed Loading Config!");
        }
    }


    @Listener
    public void onServerStart(GameStartedServerEvent event) {
     registerCommands();
     Sponge.getEventManager().registerListeners(this, new EventTrackers());
    }

    public Cause getCause() {
        return Cause.source(this.pluginContainer).build();
    }

    public static AdminUtilsSponge getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return this.logger;
    }

    @Listener
    public void onReload(GameReloadEvent event) {
        this.getLogger().info("Reloaded Config!");
        loadConfig();
    }

    public void registerCommands() {
        CommandSpec invview = CommandSpec.builder()
                .arguments(GenericArguments.user(Text.of("player")))
                .description(Text.of("Allows you to open a players inventory by using forge methods! This also means extra inventories like baubles can be used!"))
                .permission(PermissionStrings.inv)
                .executor(new CommandInvView())
                .build();

        Sponge.getCommandManager().register(this, invview, "invview", "oi", "iv","vi","io", "invsee", "seeinv","inventoryview","adminutilinvview");

        CommandSpec endview = CommandSpec.builder()
                .arguments(GenericArguments.user(Text.of("player")))
                .description(Text.of("Allows you to open a players enderchest by using forge methods! This also means extra inventories like baubles can be used!"))
                .permission(PermissionStrings.ender)
                .executor(new CommandEndView())
                .build();

        Sponge.getCommandManager().register(this, endview, "endview", "oe", "eo", "ev", "ve", "endsee", "seeend", "enderchestview", "adminutilendview");

        CommandSpec uuid = CommandSpec.builder()
                .arguments(GenericArguments.user(Text.of("player")))
                .description(Text.of("Allows you to get the uuid of a player!"))
                .permission(PermissionStrings.uuid)
                .executor(new CommandUUID())
                .build();

        Sponge.getCommandManager().register(this, uuid, "uuid", "name", "adminutilsuuid");

        CommandSpec restore = CommandSpec.builder()
                .arguments(GenericArguments.user(Text.of("player")))
                .description(Text.of("Restores the players file from their latest death!"))
                .permission(PermissionStrings.restore)
                .executor(new CommandRestore())
                .build();

        Sponge.getCommandManager().register(this, restore, "restore", "deathrestore", "adminutilsrestore", "aurestore");

        CommandSpec adminutils = CommandSpec.builder()
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("perms")), GenericArguments.string(Text.of("commands"))))
                .description(Text.of("Shows you the help menu for this plugin and the perms needed if you have the correct perm!"))
                .permission(PermissionStrings.help)
                .executor(new CommandAdminUtils())
                .build();

        Sponge.getCommandManager().register(this, adminutils, "adminutils", "adminhelp", "auhelp");

        CommandSpec test = CommandSpec.builder()
                .description(Text.of("Test command for me to test things!"))
                //.arguments(GenericArguments.user(Text.of("player")))
                .permission("adminutils.test")
                .executor(new CommandTest())
                .build();

        Sponge.getCommandManager().register(this, test, "test", "adminutilstest", "autest");

        CommandSpec warnings = CommandSpec.builder()
                .description(Text.of("Turns worldwarnings on or off permanently for the runner of the command!"))
                .arguments(GenericArguments.optional(GenericArguments.string(Text.of("off"))), GenericArguments.optional(GenericArguments.string(Text.of("on"))))
                .permission("adminutils.warnings")
                .executor(new CommandWorldWarnings())
                .build();

        Sponge.getCommandManager().register(this, warnings, "warnings", "worldwarnings", "ww");

        //CommandSpec playerlist CommandSpec.builder()
        //        .description(Text.of("List all online players nicer then nucleus does :P"))
        //        .permission(PermissionStrings.list)
        //        .executor(new CommandList())
        //        .build();
//
        //Sponge.getCommandManager().register(this, playerlist, "list", "playerlist", "online", "players");
    }

    @Listener
    public void construction(GameConstructionEvent event) {
        instance = this;
    }


    /* Config Stuffs */

    @Inject
    @DefaultConfig(sharedRoot = true)
    private File defaultCfg;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> cfgMgr;

    private CommentedConfigurationNode cfg;

    Date date = new Date();

    @Setting(value = "savepath", comment = "Sets the name of the folder AdminUtils will save its stuff into!")
    String savepath = "AdminUtils";
    String prefix = "AdminUtils";
    double version = 0.001;


    public void loadConfig() {
        try {
            if (!this.defaultCfg.exists()) {
                AdminUtilsSponge.getInstance().getLogger().info("Creating config!");
                this.defaultCfg.createNewFile();

                this.cfg = getCfgMgr().load();

                this.cfg.getNode("amisc", "version").setValue(version).setComment("DO NOT TOUCH! This will be for auto config updating!");

                this.cfg.getNode("playersave").setComment("This section is config options for the playersaving half of AdminUtils!");
                this.cfg.getNode("playersave", "savepath").setValue(savepath).setComment("This is the name of the folder used to save the player files!");
                this.cfg.getNode("playersave", "prefix").setValue(prefix).setComment("This sets the prefix used in the message when a player dies!");
                this.cfg.getNode("main").setComment("This section contains other things that don't fit into the other sections!");
                this.cfg.getNode("main", "worldlist").setValue(new ArrayList<String>(){{add("world");}}).setComment("Here is the dimension names of which you don't want the world reset warning!");
                this.cfg.getNode("main", "worldwarningboolean").setValue(true).setComment("This sets whether the warning is given to the player or not!");

                this.getCfgMgr().save(cfg);
            }else {
                this.cfg = getCfgMgr().load();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationNode getCfg() {
        return this.cfg;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getCfgMgr() {
        return this.cfgMgr;
    }

    public String getSavePath() {
        String save = "";
        try {
            save = this.cfg.getNode("playersave", "savepath").getString();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return save;
    }

    public String getPrefix() {
        String prefix = "";
        try {
            prefix = this.cfg.getNode("playersave", "prefix").getString();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return prefix;
    }

    public ArrayList<String> getWorldList() {
        try {
            this.cfg = getCfgMgr().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Function<Object, String> stringTransformer = new Function<Object, String>() {
            public String apply(Object input) {
                if (input instanceof String) {
                    return (String) input;
                } else {
                    return null;
                }
            }
        };

        ArrayList<String> finallist = new ArrayList();
        this.cfg.getNode("main", "worldlist").getList(stringTransformer).forEach((x) -> {
            finallist.add(x);
        });
        return finallist;
    }
    
    public boolean getWorldWarningBoolean() {
        boolean warning = false;
        try {
            warning = this.cfg.getNode("main", "worldwarningboolean").getBoolean();
        }catch (Exception e) {
            
        }
        return warning;
    }

}