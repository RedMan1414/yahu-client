package com.yahu.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class CommandManager {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerBindCommand(dispatcher);
            registerConfigCommand(dispatcher);
            registerModuleCommand(dispatcher);
            registerFriendCommand(dispatcher);
            registerWaypointCommand(dispatcher);
            registerAccountCommand(dispatcher);
            registerThemeCommand(dispatcher);
            registerNameProtectCommand(dispatcher);
            registerBypassCommand(dispatcher);
            registerGotoCommand(dispatcher);
            registerMineCommand(dispatcher);
            registerFollowCommand(dispatcher);
        });
    }

    private static void registerBindCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("bind")
                .then(Commands.argument("module", StringArgumentType.word())
                        .then(Commands.argument("key", StringArgumentType.word())
                                .executes(ctx -> {
                                    String moduleName = StringArgumentType.getString(ctx, "module");
                                    String keyName = StringArgumentType.getString(ctx, "key");
                                    return BindCommand.execute(moduleName, keyName, false);
                                })
                                .then(Commands.literal("hold")
                                        .executes(ctx -> {
                                            String moduleName = StringArgumentType.getString(ctx, "module");
                                            String keyName = StringArgumentType.getString(ctx, "key");
                                            return BindCommand.execute(moduleName, keyName, true);
                                        }))
                        )
                )
        );
    }

    private static void registerConfigCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("config")
                .then(Commands.literal("load")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ConfigCommand.load(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("save")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ConfigCommand.save(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("delete")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ConfigCommand.delete(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> ConfigCommand.list()))
                .then(Commands.literal("export")
                        .executes(ctx -> ConfigCommand.export()))
                .then(Commands.literal("import")
                        .then(Commands.argument("data", StringArgumentType.greedyString())
                                .executes(ctx -> ConfigCommand.importConfig(StringArgumentType.getString(ctx, "data")))))
        );
    }

    private static void registerModuleCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("module")
                .then(Commands.literal("enable")
                        .then(Commands.argument("module", StringArgumentType.word())
                                .executes(ctx -> ModuleCommand.enable(StringArgumentType.getString(ctx, "module")))))
                .then(Commands.literal("disable")
                        .then(Commands.argument("module", StringArgumentType.word())
                                .executes(ctx -> ModuleCommand.disable(StringArgumentType.getString(ctx, "module")))))
                .then(Commands.literal("toggle")
                        .then(Commands.argument("module", StringArgumentType.word())
                                .executes(ctx -> ModuleCommand.toggle(StringArgumentType.getString(ctx, "module")))))
                .then(Commands.literal("list")
                        .executes(ctx -> ModuleCommand.list()))
        );
    }

    private static void registerFriendCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("friend")
                .then(Commands.literal("add")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> FriendCommand.add(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> FriendCommand.remove(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> FriendCommand.list()))
                .then(Commands.literal("color")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .then(Commands.argument("color", StringArgumentType.word())
                                        .executes(ctx -> FriendCommand.color(StringArgumentType.getString(ctx, "name"), StringArgumentType.getString(ctx, "color"))))
                        )
        );
    }

    private static void registerWaypointCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("waypoint")
                .then(Commands.literal("add")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .then(Commands.argument("x", IntegerArgumentType.integer())
                                        .then(Commands.argument("y", IntegerArgumentType.integer())
                                                .then(Commands.argument("z", IntegerArgumentType.integer())
                                                        .executes(ctx -> WaypointCommand.add(
                                                                StringArgumentType.getString(ctx, "name"),
                                                                IntegerArgumentType.getInteger(ctx, "x"),
                                                                IntegerArgumentType.getInteger(ctx, "y"),
                                                                IntegerArgumentType.getInteger(ctx, "z")
                                                        ))))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> WaypointCommand.remove(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("tp")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> WaypointCommand.teleport(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> WaypointCommand.list()))
        );
    }

    private static void registerAccountCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("account")
                .then(Commands.literal("add")
                        .then(Commands.argument("type", StringArgumentType.word())
                                .then(Commands.argument("data", StringArgumentType.greedyString())
                                        .executes(ctx -> AccountCommand.add(
                                                StringArgumentType.getString(ctx, "type"),
                                                StringArgumentType.getString(ctx, "data"))))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> AccountCommand.remove(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("switch")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> AccountCommand.switchAccount(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> AccountCommand.list()))
        );
    }

    private static void registerThemeCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("theme")
                .then(Commands.literal("load")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ThemeCommand.load(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("save")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ThemeCommand.save(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("delete")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> ThemeCommand.delete(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> ThemeCommand.list()))
        );
    }

    private static void registerNameProtectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("nameprotect")
                .then(Commands.literal("add")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> NameProtectCommand.add(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(ctx -> NameProtectCommand.remove(StringArgumentType.getString(ctx, "name")))))
                .then(Commands.literal("list")
                        .executes(ctx -> NameProtectCommand.list()))
                .then(Commands.literal("clear")
                        .executes(ctx -> NameProtectCommand.clear()))
        );
    }

    private static void registerBypassCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("bypass")
                .then(Commands.argument("profile", StringArgumentType.word())
                        .executes(ctx -> BypassCommand.execute(StringArgumentType.getString(ctx, "profile"))))
        );
    }

    private static void registerGotoCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("goto")
                .then(Commands.argument("x", IntegerArgumentType.integer())
                        .then(Commands.argument("y", IntegerArgumentType.integer())
                                .then(Commands.argument("z", IntegerArgumentType.integer())
                                        .executes(ctx -> GotoCommand.execute(
                                                IntegerArgumentType.getInteger(ctx, "x"),
                                                IntegerArgumentType.getInteger(ctx, "y"),
                                                IntegerArgumentType.getInteger(ctx, "z")
                                        )))))
        );
    }

    private static void registerMineCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mine")
                .then(Commands.argument("block", StringArgumentType.word())
                        .executes(ctx -> MineCommand.execute(StringArgumentType.getString(ctx, "block"))))
        );
    }

    private static void registerFollowCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("follow")
                .then(Commands.argument("player", StringArgumentType.word())
                        .executes(ctx -> FollowCommand.execute(StringArgumentType.getString(ctx, "player"))))
                .then(Commands.literal("stop")
                        .executes(ctx -> FollowCommand.stop()))
        );
    }
}