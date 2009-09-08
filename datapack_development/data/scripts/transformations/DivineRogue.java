package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class DivineRogue extends L2Transformation
{
	public DivineRogue()
	{
		// id, colRadius, colHeight
		super(254, 10, 28);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 254 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Divine Rogue Stun Shot
		getPlayer().addSkill(SkillTable.getInstance().getInfo(686, 1), false);
		// Divine Rogue Double Shot
		getPlayer().addSkill(SkillTable.getInstance().getInfo(687, 1), false);
		// Divine Rogue Bleed Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(688, 1), false);
		// Divine Rogue Deadly Blow
		getPlayer().addSkill(SkillTable.getInstance().getInfo(689, 1), false);
		// Divine Rogue Agility
		getPlayer().addSkill(SkillTable.getInstance().getInfo(690, 1), false);
		// Sacrifice Rogue
		getPlayer().addSkill(SkillTable.getInstance().getInfo(691, 1), false);
		// Divine Rogue Piercing Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(797, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{686,687,688,689,690,691,797,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Divine Rogue Stun Shot
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(686, 1), false);
		// Divine Rogue Double Shot
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(687, 1), false);
		// Divine Rogue Bleed Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(688, 1), false);
		// Divine Rogue Deadly Blow
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(689, 1), false);
		// Divine Rogue Agility
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(690, 1), false);
		// Sacrifice Rogue
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(691, 1), false);
		// Divine Rogue Piercing Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(797, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineRogue());
	}
}
