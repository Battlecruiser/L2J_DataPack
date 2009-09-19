package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class VanguardTempleKnight extends L2Transformation
{
	public VanguardTempleKnight()
	{
		// id
		super(314);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 314 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		if (getPlayer().getLevel() > 43)
		{
			// Power Divide
			getPlayer().addSkill(SkillTable.getInstance().getInfo(816, getPlayer().getLevel() - 43), false);
			// Full Swing
			getPlayer().addSkill(SkillTable.getInstance().getInfo(814, getPlayer().getLevel() - 43), false);
			// Two handed mastery
			getPlayer().addSkill(SkillTable.getInstance().getInfo(293, getPlayer().getLevel() - 43), false);
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,816,814,293,28,18,10,67,449,400,197});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,28,18,10,67,449,400,197});
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Power Divide
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(816, getPlayer().getLevel() - 43), false);
		// Full Swing
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(814, getPlayer().getLevel() - 43), false);
		// Two handed mastery
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(293, getPlayer().getLevel() - 43), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardTempleKnight());
	}
}
