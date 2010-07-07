package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Gordon extends L2Transformation
{
	private static final int[] SKILLS = {728,729,730,5491,619};
	public Gordon()
	{
		// id, colRadius, colHeight
		super(308, 43, 46.6);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 308 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Gordon Beast Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(728, 1), false);
		// Gordon Sword Stab
		getPlayer().addSkill(SkillTable.getInstance().getInfo(729, 1), false);
		// Gordon Press
		getPlayer().addSkill(SkillTable.getInstance().getInfo(730, 1), false);
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
		// Gordon Beast Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(728, 1), false);
		// Gordon Sword Stab
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(729, 1), false);
		// Gordon Press
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(730, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Gordon());
	}
}
