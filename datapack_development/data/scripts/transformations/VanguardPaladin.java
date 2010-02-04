package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class VanguardPaladin extends L2Transformation
{
	public VanguardPaladin()
	{
		// id
		super(312);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 312 || getPlayer().isCursedWeaponEquipped())
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
			getPlayer().setTransformAllowedSkills(new int[]{18,28,196,197,293,400,406,814,816,838,956,957});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{18,28,196,197,400,406,838});
			// Switch Stance
			getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
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
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(956, getPlayer().getLevel() - 43), false, false);
		// Guillotine Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(957, getPlayer().getLevel() - 43), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardPaladin());
	}
}
