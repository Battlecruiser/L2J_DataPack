package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class GrailApostleNormal extends L2Transformation
{
	public GrailApostleNormal()
	{
		// id, colRadius, colHeight
		super(202, 8, 30);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 202 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Spear (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(559, 3), false);
		// Power Slash (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(560, 3), false);
		// Bless of Angel (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(561, 3), false);
		// Wind of Angel (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(562, 3), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{559,560,561,562,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Spear (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(559, 3), false);
		// Power Slash (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(560, 3), false);
		// Bless of Angel (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(561, 3), false, false);
		// Wind of Angel (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(562, 3), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new GrailApostleNormal());
	}
}
