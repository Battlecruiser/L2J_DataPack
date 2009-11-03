package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class DivineWarrior extends L2Transformation
{
	public DivineWarrior()
	{
		// id, colRadius, colHeight
		super(253, 14.5, 29);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 253 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Cross Slash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(675, 1), false);
		// Sonic Blaster
		getPlayer().addSkill(SkillTable.getInstance().getInfo(676, 1), false);
		// Transfixition of Earth
		getPlayer().addSkill(SkillTable.getInstance().getInfo(677, 1), false);
		// Divine Warrior War Cry
		getPlayer().addSkill(SkillTable.getInstance().getInfo(678, 1), false);
		// Sacrifice Warrior
		getPlayer().addSkill(SkillTable.getInstance().getInfo(679, 1), false);
		// Divine Warrior Assault Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(798, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{675,676,677,678,679,798,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Cross Slash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(675, 1), false);
		// Sonic Blaster
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(676, 1), false);
		// Transfixition of Earth
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(677, 1), false);
		// Divine Warrior War Cry
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(678, 1), false);
		// Sacrifice Warrior
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(679, 1), false);
		// Divine Warrior Assault Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(798, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineWarrior());
	}
}
