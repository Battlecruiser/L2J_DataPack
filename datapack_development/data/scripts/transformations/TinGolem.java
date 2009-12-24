package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class TinGolem extends L2Transformation
{
	public TinGolem()
	{
		// id, colRadius, colHeight
		super(116, 13, 18.5);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 116 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Fake Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(940, 1), false);
		// Special Motion 
		getPlayer().addSkill(SkillTable.getInstance().getInfo(941, 1), false);
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 2), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{940,941,5437,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Fake Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(940, 1), false);
		// Special Motion 
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(941, 1), false);
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 2), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new TinGolem());
	}
}
