package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class VanguardShilienKnight extends L2Transformation
{
	public VanguardShilienKnight()
	{
		// id
		super(315);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 315 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		if (getPlayer().getLevel() > 43)
		{
			// Dual Weapon Mastery
			getPlayer().addSkill(SkillTable.getInstance().getInfo(144, getPlayer().getLevel() - 43), false);
			// Blade Hurricane
			getPlayer().addSkill(SkillTable.getInstance().getInfo(815, getPlayer().getLevel() - 43), false);
			// Double Strike
			getPlayer().addSkill(SkillTable.getInstance().getInfo(817, getPlayer().getLevel() - 43), false);
			// Boost Morale
			getPlayer().addSkill(SkillTable.getInstance().getInfo(956, getPlayer().getLevel() - 43), false);
			// Triple Blade Slash
			getPlayer().addSkill(SkillTable.getInstance().getInfo(958, getPlayer().getLevel() - 43), false);
			getPlayer().setTransformAllowedSkills(new int[]{18,22,28,33,144,278,279,289,401,815,817,838,956,958});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{18,22,28,33,278,279,289,401,838});
			// Switch Stance
			getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Dual Weapon Mastery
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(144, getPlayer().getLevel() - 43), false);
		// Blade Hurricane
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(815, getPlayer().getLevel() - 43), false);
		// Double Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(817, getPlayer().getLevel() - 43), false);
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Boost Morale
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(956, getPlayer().getLevel() - 43), false, false);
		// Triple Blade Slash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(958, getPlayer().getLevel() - 43), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardShilienKnight());
	}
}
