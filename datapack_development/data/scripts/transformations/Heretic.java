package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class Heretic extends L2Transformation
{
	public Heretic()
	{
		// id, colRadius, colHeight
		super(3, 7.7, 28.4);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 3 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		if (getPlayer().getLevel() >= 76)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 3), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 3), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 3), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 2), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 2), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 2), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(738, 1), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(739, 1), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(740, 1), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(741, 1), false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{738,739,740,741,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		if (getPlayer().getLevel() >= 76)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 3), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 3), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 3), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 3), false, false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 2), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 2), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 2), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 2), false, false);
		}
		else
		{
			// Heretic Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(738, 1), false);
			// Heretic Battle Heal (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(739, 1), false);
			// Heretic Resurrection (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(740, 1), false);
			// Heretic Heal Side Effect (up to 3 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(741, 1), false, false);
		}
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Heretic());
	}
}
