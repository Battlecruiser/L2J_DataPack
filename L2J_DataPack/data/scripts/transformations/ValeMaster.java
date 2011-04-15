package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class ValeMaster extends L2Transformation
{
	private static final int[] SKILLS = {742,743,744,745,5491,619};
	public ValeMaster()
	{
		// id, colRadius, colHeight
		super(4, 12, 40);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 4 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		if (getPlayer().getLevel() >= 76)
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(742, 3), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(743, 3), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(744, 3), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(745, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(742, 2), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(743, 2), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(744, 2), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(745, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(742, 1), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(743, 1), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(744, 1), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(745, 1), false);
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
		if (getPlayer().getLevel() >= 76)
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(742, 3), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(743, 3), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(744, 3), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(745, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(742, 2), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(743, 2), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(744, 2), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(745, 2), false);
		}
		else
		{
			// Vale Master Bursting Flame (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(742, 1), false);
			// Vale Master Dark Explosion (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(743, 1), false);
			// Vale Master Dark Flare (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(744, 1), false);
			// Vale Master Dark Cure (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(745, 1), false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new ValeMaster());
	}
}
