package mc.mian.indestructible_blocks.common.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import mc.mian.indestructible_blocks.util.DestructibilityState;
import mc.mian.indestructible_blocks.util.IndestructibleUtil;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class IBCommand {
    private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType((object, object2) -> Component.translatable("commands.fill.toobig", new Object[]{object, object2}));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
        dispatcher.register(
                Commands.literal("ib")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("block_id")
                                        .then(Commands.argument("add", BoolArgumentType.bool())
                                                .then(Commands.argument("block_id", ResourceArgument.resource(context, Registries.BLOCK))
                                                        .suggests(((commandContext, suggestionsBuilder) ->
                                                                SharedSuggestionProvider.suggestResource(
                                                                        BuiltInRegistries.BLOCK.keySet().stream(), suggestionsBuilder
                                                                )))
                                                        .executes(source ->
                                                                addBlockId(
                                                                        source.getSource(),
                                                                        BoolArgumentType.getBool(source, "add"),
                                                                        ResourceArgument.getResource(source, "block_id", Registries.BLOCK))))))
                        .then(Commands.literal("set_state")
                                .then(Commands.argument("from", BlockPosArgument.blockPos())
                                        .then(
                                                Commands.argument("to", BlockPosArgument.blockPos())
                                                        .then(
                                                                Commands.argument("indestructible", BoolArgumentType.bool())
                                                                        .executes(commandContext ->
                                                                                setStateOfBlocks(
                                                                                        commandContext.getSource(),
                                                                                BoundingBox.fromCorners(BlockPosArgument.getLoadedBlockPos(commandContext, "from"), BlockPosArgument.getLoadedBlockPos(commandContext, "to")),
                                                                                        BoolArgumentType.getBool(commandContext, "indestructible")))))))
        );
    }

    private static int addBlockId(CommandSourceStack source, boolean add, Holder.Reference<Block> block) throws CommandSyntaxException{
        DestructibilityState state = IndestructibleUtil.setIndestructibilityState(block.unwrapKey().get().location().toString(), add);
        source.sendSuccess(() -> Component.translatable("gui.indestructible_blocks.indestructibility_state", block.unwrapKey().get().location().toString(), state.getSetting()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setStateOfBlocks(CommandSourceStack source, BoundingBox area, boolean indestructible
    ) throws CommandSyntaxException {
        int i = area.getXSpan() * area.getYSpan() * area.getZSpan();
        int j = source.getLevel().getGameRules().getInt(GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
        if (i > j) {
            throw ERROR_AREA_TOO_LARGE.create(j, i);
        } else {
            int amount = 0;
            ServerLevel level = source.getLevel();
            for (BlockPos blockPos : BlockPos.betweenClosed(area.minX(), area.minY(), area.minZ(), area.maxX(), area.maxY(), area.maxZ())) {
                IndestructibleUtil.changeOverride(level, blockPos, indestructible ? DestructibilityState.INDESTRUCTIBLE : DestructibilityState.DESTRUCTIBLE);
                amount++;
            }
            int finalAmount = amount;
            source.sendSuccess(() -> Component.translatable("chat.indestructible_blocks.set_multiple_block_state", finalAmount), true);
            return Command.SINGLE_SUCCESS;
        }
    }
}
