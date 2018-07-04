package org.dimdev.testmod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandSource;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.ResourceLocation;
import org.dimdev.rift.listener.*;

import java.util.Collection;
import java.util.Collections;

public class TestMod implements BlockAdder, ItemAdder, FluidAdder, TextureAdder, PacketAdder, CommandAdder {
    public static final Block WHITE_BLOCK = new Block(Block.Builder.create(Material.ROCK));
    public static final Block TRANSLUCENT_WHITE_BLOCK = new BlockStainedGlass(EnumDyeColor.WHITE, Block.Builder.create(Material.GLASS));
    public static final FlowingFluid WHITE_FLUID = new WhiteFluid.Source();
    public static final FlowingFluid FLOWING_WHITE_FLUID = new WhiteFluid.Flowing();
    public static final BlockFlowingFluid BLOCK_WHITE_FLUID = new BlockFlowingFluid(WHITE_FLUID, Block.Builder.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100F, 100F)) {
        @Override
        public boolean isVariableOpacity() {
            return true;
        }
    };
    public static final Item PACKET_TESTER = new ItemPacketTester(new Item.Builder());

    @Override
    public void registerBlocks(BlockRegistrationReceiver receiver) {
        receiver.registerBlock(new ResourceLocation("testmod", "white_block"), WHITE_BLOCK);
        receiver.registerBlock(new ResourceLocation("testmod", "translucent_white_block"), TRANSLUCENT_WHITE_BLOCK);
        receiver.registerBlock(new ResourceLocation("testmod", "white_fluid"), BLOCK_WHITE_FLUID);
    }

    @Override
    public void registerItems(ItemRegistrationReceiver receiver) {
        receiver.registerItemBlock(WHITE_BLOCK, ItemGroup.BUILDING_BLOCKS);
        receiver.registerItemBlock(TRANSLUCENT_WHITE_BLOCK, ItemGroup.BUILDING_BLOCKS);
        receiver.registerItem(new ResourceLocation("testmod", "packet_tester"), PACKET_TESTER);
    }

    @Override
    public void registerFluids(FluidRegistrationReceiver receiver) {
        receiver.registerFluid(new ResourceLocation("testmod", "white_fluid"), WHITE_FLUID);
        receiver.registerFluid(new ResourceLocation("testmod", "flowing_white_fluid"), FLOWING_WHITE_FLUID);
    }

    @Override
    public Collection<? extends ResourceLocation> getBuiltinTextures() {
        return Collections.singletonList(new ResourceLocation("testmod", "block/white_fluid_flow"));
    }

    @Override
    public void registerHandshakingPackets(PacketRegistrationReceiver receiver) {}

    @Override
    public void registerPlayPackets(PacketRegistrationReceiver receiver) {
        receiver.registerPacket(EnumPacketDirection.SERVERBOUND, CPacketTest.class);
    }

    @Override
    public void registerStatusPackets(PacketRegistrationReceiver receiver) {}

    @Override
    public void registerLoginPackets(PacketRegistrationReceiver receiver) {}

    @Override
    public void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        ExplosionCommand.register(dispatcher);
    }
}
