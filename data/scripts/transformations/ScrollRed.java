package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

/**
 * TODO: Buffs disappear once you get transformed, but reappear after the transformed state wears off.
 * Skills involved in the minigame but are not assigned directly to players:
 *     Flip Nearby Blocks - 5847 - For Flip Block, there are two skills, one for each side (makes sense). For this, there is only one skill. Thus it is probably not assigned to the transformation.
 *     Block Trigger Slow - 5848 - This may be assigned to players, unsure.
 *     Decrease Speed - 5849 - This is possibly assigned to all players to set all players to the same running speed for the duration of the game.
 *     Block Trigger Stun - 5849 - From L2Vault: "The squares gives drops of "bond" and "landmine". I wasn't able to figure out what the bond did as it wasn't anything that seemed to go into your inventory. However, Landmine did appear in your inventory which allows you to use it before flipping a square which will give the other team a state of stun when they attempt to flip the same square (from what I can gather, it all happens so quickly ;) "
 *     Shock - 5851 - Stun effect from 5849
 * More Info: http://l2vault.ign.com/wiki/index.php/Handy%E2%80%99s_Block_Checker
 */
public class ScrollRed extends L2Transformation
{
	public ScrollRed()
	{
		// id, colRadius, colHeight
		super(121, 12, 28);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 121 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Flip Block
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5853, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{5853,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Flip Block
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5853, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new ScrollRed());
	}
}
