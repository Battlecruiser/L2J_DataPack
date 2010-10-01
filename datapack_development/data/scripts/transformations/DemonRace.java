package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

/*
 * TODO: Skill levels. How do they work? Transformation is given at level 83, there are 6 levels of the skill. How are they assigned? Based on player level somehow? Based on servitor?
 */
public class DemonRace extends L2Transformation
{
	private static final int[] SKILLS = {901,902,903,904,905,5491,619};
	public DemonRace()
	{
		// id, colRadius, colHeight
		super(221, 11, 27);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 221 || getPlayer().isCursedWeaponEquipped())
			return;
		
		if (getPlayer().getPet() != null)
			getPlayer().getPet().unSummon(getPlayer());
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Dark Strike (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(901, 4), false);
		// Bursting Flame (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(902, 4), false);
		// Stratum Explosion (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(903, 4), false);
		// Corpse Burst (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(904, 4), false);
		// Dark Detonation (up to 6)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(905, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Dark Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(901, 4), false);
		// Bursting Flame
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(902, 4), false);
		// Stratum Explosion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(903, 4), false);
		// Corpse Burst
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(904, 4), false);
		// Dark Detonation
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(905, 4), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DemonRace());
	}
}
