package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

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
			// Two handed mastery
			getPlayer().addSkill(SkillTable.getInstance().getInfo(293, getPlayer().getLevel() - 43), false);
			// Full Swing
			getPlayer().addSkill(SkillTable.getInstance().getInfo(814, getPlayer().getLevel() - 43), false);
			// Power Divide
			getPlayer().addSkill(SkillTable.getInstance().getInfo(816, getPlayer().getLevel() - 43), false);
			// Boost Morale
			getPlayer().addSkill(SkillTable.getInstance().getInfo(956, getPlayer().getLevel() - 43), false);
			// Guillotine Attack
			getPlayer().addSkill(SkillTable.getInstance().getInfo(957, getPlayer().getLevel() - 43), false);
			getPlayer().setTransformAllowedSkills(new int[]{10,18,28,67,197,293,400,449,814,816,838,956,957,5491});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{10,18,28,67,197,400,449,838,5491});
			// Switch Stance
			getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
			// Decrease Bow/Crossbow Attack Speed
			getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Two handed mastery
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(293, getPlayer().getLevel() - 43), false);
		// Full Swing
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(814, getPlayer().getLevel() - 43), false);
		// Power Divide
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(816, getPlayer().getLevel() - 43), false);
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Boost Morale
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(956, getPlayer().getLevel() - 43), false);
		// Guillotine Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(957, getPlayer().getLevel() - 43), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardTempleKnight());
	}
}
