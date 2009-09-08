package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

/*
 * TODO: Skill levels. How do they work? Transformation is given at level 83, there are 6 levels of the skill. How are they assigned? Based on player level somehow? Based on servitor?
 */
public class MyoRace extends L2Transformation
{
	public MyoRace()
	{
		// id, colRadius, colHeight
		super(219, 10, 23);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 219 || getPlayer().isCursedWeaponEquipped())
			return;
		
		if (getPlayer().getPet() != null)
			getPlayer().getPet().unSummon(getPlayer());

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Rolling Step (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(896, 4), false);
		// Double Blast (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(897, 4), false);
		// Tornado Slash (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(898, 4), false);
		// Cat Roar (up to 6 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(899, 4), false);
		// Energy Blast (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(900, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{896,897,898,899,900,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Rolling Step (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(896, 4), false);
		// Double Blast (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(897, 4), false);
		// Tornado Slash (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(898, 4), false);
		// Cat Roar (up to 6 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(899, 4), false);
		// Energy Blast (up to 6)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(900, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new MyoRace());
	}
}
