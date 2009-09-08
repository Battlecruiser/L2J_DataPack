package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class Benom extends L2Transformation
{
	public Benom()
	{
		// id, colRadius, colHeight
		super(307, 20, 56);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 307 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Benom Power Smash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(725, 1), false);
		// Benom Sonic Storm
		getPlayer().addSkill(SkillTable.getInstance().getInfo(726, 1), false);
		// Benom Disillusion
		getPlayer().addSkill(SkillTable.getInstance().getInfo(727, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{725,726,727,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Benom Power Smash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(725, 1), false);
		// Benom Sonic Storm
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(726, 1), false);
		// Benom Disillusion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(727, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Benom());
	}
}
