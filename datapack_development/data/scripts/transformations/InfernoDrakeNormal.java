package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class InfernoDrakeNormal extends L2Transformation
{
	private static final int[] SKILLS = {619,5491,576,577,578,579};
	public InfernoDrakeNormal()
	{
		// id, colRadius, colHeight
		super(214, 15, 24);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 214 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Paw Strike (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(579, 3), false);
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
		// Paw Strike (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(576, 3), false);
		// Fire Breath (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(577, 3), false);
		// Blaze Quake (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(578, 3), false);
		// Fire Armor (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(579, 3), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InfernoDrakeNormal());
	}
}
