package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DollBlader extends L2Transformation
{
	private static final int[] SKILLS = {752,753,754,5491,619};
	public DollBlader()
	{
		// id, colRadius, colHeight
		super(7, 6, 12);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 7 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Doll Blader Clairvoyance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(754, 1), false);
		
		if (getPlayer().getLevel() >= 76)
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 3), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 2), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 1), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 1), false);
		}
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
		// Doll Blader Clairvoyance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(754, 1), false, false);
		
		if (getPlayer().getLevel() >= 76)
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 3), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 2), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 2), false);
		}
		else
		{
			// Doll Blader Sting (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 1), false);
			// Doll Blader Throwing Knife (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 1), false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DollBlader());
	}
}
