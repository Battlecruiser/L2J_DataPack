package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class DwarfGolem extends L2Transformation
{
	public DwarfGolem()
	{
		// id, colRadius, colHeight
		super(259, 31, 51.8);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 259 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Magic Obstacle
		getPlayer().addSkill(SkillTable.getInstance().getInfo(806, 1), false);
		// Over-hit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(807, 1), false);
		// Golem Punch
		getPlayer().addSkill(SkillTable.getInstance().getInfo(808, 1), false);
		// Golem Tornado Swing
		getPlayer().addSkill(SkillTable.getInstance().getInfo(809, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{806,807,808,809,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Magic Obstacle
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(806, 1), false);
		// Over-hit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(807, 1), false);
		// Golem Punch
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(808, 1), false);
		// Golem Tornado Swing
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(809, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DwarfGolem());
	}
}
