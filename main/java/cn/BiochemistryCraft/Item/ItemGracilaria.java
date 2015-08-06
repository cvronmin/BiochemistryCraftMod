package cn.BiochemistryCraft.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.BiochemistryCraft.BiochemistryCraft;
import cn.BiochemistryCraft.Register.BCCRegisterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemGracilaria extends ItemFood implements IPlantable{
	public ItemGracilaria(){
		super(2, 0.8F, false);
		setTextureName(BiochemistryCraft.MODID+":gracilaria");
		setCreativeTab(BiochemistryCraft.biocreativetab);
		setUnlocalizedName("gracilaria");
	}
	
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10){
		if (par7 != 1){
			return false;
		}else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack)){
            Block soil = par3World.getBlock(par4, par5 + 1, par6);
            if (soil != null && canPlaceBlockOn(soil) && par3World.isAirBlock(par4, par5 + 2, par6)) {
                par3World.setBlock(par4, par5 + 2, par6, BCCRegisterBlock.gracilariaCorp);
                --par1ItemStack.stackSize;
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
	}
	
	@Override
	public Block getPlant(IBlockAccess arg0, int arg1, int arg2, int arg3) {
		return BCCRegisterBlock.gracilariaCorp;
	}

	@Override
	public int getPlantMetadata(IBlockAccess arg0, int arg1, int arg2, int arg3) {
		return 0;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess arg0, int arg1, int arg2, int arg3) {
		return EnumPlantType.Crop;
	}
	
	private boolean canPlaceBlockOn(Block arg0){
		return arg0 == Blocks.water;
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
	    MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);
	    if(movingobjectposition == null){
	    	return stack;
	    }
	    if(movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK){
	    	int i = movingobjectposition.blockX;
	    	int j = movingobjectposition.blockY;
	    	int k = movingobjectposition.blockZ;
	    	if(!world.canMineBlock(player, i, j, k) || !player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) {
	    		return stack;
	    	}
	    	if((world.getBlock(i, j, k).getMaterial() == Material.water) && (world.getBlockMetadata(i, j, k) == 0) && (world.isAirBlock(i, j + 1, k))){
	    		BlockSnapshot blocksnapshot = BlockSnapshot.getBlockSnapshot(world, i, j + 1, k);
	    		world.setBlock(i, j + 1, k, BCCRegisterBlock.gracilariaCorp);
	    		if(ForgeEventFactory.onPlayerBlockPlace(player, blocksnapshot, ForgeDirection.UP).isCanceled()){
	    			blocksnapshot.restore(true, false);
	    			return stack;
	    		}
	    		if(!player.capabilities.isCreativeMode){
	    			stack.stackSize -= 1;
	    		}
	    	}
	    }
	    return stack;
	}
}
