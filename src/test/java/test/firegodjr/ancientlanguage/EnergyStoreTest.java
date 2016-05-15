package test.firegodjr.ancientlanguage;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.firegodjr.ancientlanguage.Main;
import com.firegodjr.ancientlanguage.api.magic.IBlockStore;
import com.firegodjr.ancientlanguage.api.magic.IItemStore;
import com.firegodjr.ancientlanguage.utils.BlockInstance;
import com.firegodjr.ancientlanguage.utils.VersionUtils;


@Mod(modid = Main.MODID + "energystoretest", name = Main.MODNAME + " EnergyStoreTest")
public class EnergyStoreTest {

	private static boolean ENABLE = false;

	@SidedProxy(serverSide = "test.firegodjr.ancientlanguage.EnergyStoreTest.TestCommon",
			clientSide="test.firegodjr.ancientlanguage.EnergyStoreTest.TestClient")
	private TestCommon p;
	private static Block b = new TestBlock();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(ENABLE) {
			GameRegistry.registerItem(new TestItem(), "testitem");
			GameRegistry.registerBlock(b, "testblock");
			p.init();
		}
	}

	private static class TestCommon {
		public void init() {}
	}

	@SuppressWarnings("unused")
	private static class TestClient extends TestCommon {
		@Override
		public void init() {
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
			.register(Item.getItemFromBlock(b), 0,
					new ModelResourceLocation(Main.MODID + "energystoretest" +
							":" + "testblock", "inventory"));
		}
	}

	private static class TestItem extends Item implements IItemStore {

		public TestItem() {
			this.setUnlocalizedName(Main.MODID + "energystoretest:testitem");
		}

		@Override
		public int pushEnergy(ItemStack io, int energyToPut) {
			NBTTagCompound t = io.getTagCompound();
			if(t == null) {
				t = new NBTTagCompound();
				io.setTagCompound(t);
			}
			int result = this.getStored(io)+energyToPut;
			final int max = this.getMaxStorable(io);
			if(result > max) result -= max;
			else result = 0;
			t.setInteger("TESTMagicalEnergy", Math.min(energyToPut, max));
			return result;
		}

		@Override
		public int getStored(ItemStack io) {
			if(!io.hasTagCompound()) return 0;
			return io.getTagCompound().getInteger("TESTMagicalEnergy");
		}

		@Override
		public int getMaxStorable(ItemStack io) {
			return 100;
		}

		@Override
		public boolean pullEnergy(ItemStack io, int energyToPull) {
			if(!io.hasTagCompound()) return false;
			boolean damaged = false;
			NBTTagCompound t = io.getTagCompound();
			int pullVal = this.getStored(io)-energyToPull;
			if(pullVal < 0) {
				io.attemptDamageItem(-pullVal, itemRand);
				pullVal = 0;
				damaged = true;
			}
			t.setInteger("TESTMagicalEnergy", pullVal);
			return damaged;
		}
	}

	private static class TestBlock extends Block implements IBlockStore {

		private static PropertyInteger prop = PropertyInteger.create("TESTMagicalEnergy", 0, 100);

		protected TestBlock() {
			super(Material.circuits);
		}

		@Override
		public int getStored(BlockInstance io) {
			return (Integer) io.getBlockState().getValue(prop);
		}

		@Override
		public int getMaxStorable(BlockInstance io) {
			return (Integer) prop.getAllowedValues().toArray()[prop.getAllowedValues().size()-1];
		}

		@Override
		public int pushEnergy(BlockInstance io, int energyToPut) {
			int result = this.getStored(io)+energyToPut;
			int max = this.getMaxStorable(io);
			if(result > max) result -= max;
			else result = 0;
			setPropValue(io, Math.min(energyToPut, max));
			return result;
		}

		@Override
		public boolean pullEnergy(BlockInstance io, int energyToPull) {
			boolean damaged = false;
			int pullVal = this.getStored(io)-energyToPull;
			if(pullVal < 0) {
				damaged = io.getWorld().destroyBlock(VersionUtils.createBlockPos(io.getPosition()), true);
				pullVal = 0;
			}
			setPropValue(io, pullVal);
			return damaged;
		}

		private static void setPropValue(BlockInstance io, int value) {
			io.getWorld().setBlockState(VersionUtils.createBlockPos(io.getPosition()),
					io.getBlockState().withProperty(prop, value));
		}
	}
}
