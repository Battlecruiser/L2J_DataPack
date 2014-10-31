package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class DivineKnight extends L2Transformation
{
	public DivineKnight()
	{
		// id, colRadius, colHeight
		super(252, 16, 30);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 252 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Divine Knight Hate
		getPlayer().addSkill(SkillTable.getInstance().getInfo(680, 1), false);
		// Divine Knight Hate Aura
		getPlayer().addSkill(SkillTable.getInstance().getInfo(681, 1), false);
		// Divine Knight Stun Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(682, 1), false);
		// Divine Knight Thunder Storm
		getPlayer().addSkill(SkillTable.getInstance().getInfo(683, 1), false);
		// Divine Knight Ultimate Defense
		getPlayer().addSkill(SkillTable.getInstance().getInfo(684, 1), false);
		// Sacrifice Knight
		getPlayer().addSkill(SkillTable.getInstance().getInfo(685, 1), false);
		// Divine Knight Brandish
		getPlayer().addSkill(SkillTable.getInstance().getInfo(795, 1), false);
		// Divine Knight Explosion
		getPlayer().addSkill(SkillTable.getInstance().getInfo(796, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{680,681,682,683,684,685,795,796,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Divine Knight Hate
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(680, 1), false);
		// Divine Knight Hate Aura
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(681, 1), false);
		// Divine Knight Stun Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(682, 1), false);
		// Divine Knight Thunder Storm
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(683, 1), false);
		// Divine Knight Ultimate Defense
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(684, 1), false, false);
		// Sacrifice Knight
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(685, 1), false);
		// Divine Knight Brandish
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(795, 1), false);
		// Divine Knight Explosion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(796, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineKnight());
	}
}
