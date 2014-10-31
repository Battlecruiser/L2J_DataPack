package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DivineHealer extends L2Transformation
{
	private static final int[] SKILLS = {648,803,1490,698,699,700,701,702,703,5491,619};
	public DivineHealer()
	{
		// id, colRadius, colHeight
		super(255, 10, 25);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 255 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Divine Healer Major Heal
		getPlayer().addSkill(SkillTable.getInstance().getInfo(698, 1), false);
		// Divine Healer Battle Heal
		getPlayer().addSkill(SkillTable.getInstance().getInfo(699, 1), false);
		// Divine Healer Group Heal
		getPlayer().addSkill(SkillTable.getInstance().getInfo(700, 1), false);
		// Divine Healer Resurrection
		getPlayer().addSkill(SkillTable.getInstance().getInfo(701, 1), false);
		// Divine Healer Cleanse
		getPlayer().addSkill(SkillTable.getInstance().getInfo(702, 1), false);
		// Sacrifice Healer
		getPlayer().addSkill(SkillTable.getInstance().getInfo(703, 1), false);
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
		// Divine Healer Major Heal
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(698, 1), false);
		// Divine Healer Battle Heal
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(699, 1), false);
		// Divine Healer Group Heal
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(700, 1), false);
		// Divine Healer Resurrection
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(701, 1), false);
		// Divine Healer Clans
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(702, 1), false);
		// Sacrifice Healer
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(703, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineHealer());
	}
}
