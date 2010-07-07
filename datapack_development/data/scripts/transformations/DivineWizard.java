package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DivineWizard extends L2Transformation
{
	private static final int[] SKILLS = {692,693,694,695,696,697,5491,619};
	public DivineWizard()
	{
		// id, colRadius, colHeight
		super(256, 10, 26);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 256 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Divine Wizard Holy Flare
		getPlayer().addSkill(SkillTable.getInstance().getInfo(692, 1), false);
		// Divine Wizard Holy Strike
		getPlayer().addSkill(SkillTable.getInstance().getInfo(693, 1), false);
		// Divine Wizard Holy Curtain
		getPlayer().addSkill(SkillTable.getInstance().getInfo(694, 1), false);
		// Divine Wizard Holy Cloud
		getPlayer().addSkill(SkillTable.getInstance().getInfo(695, 1), false);
		// Divine Wizard Surrender to Holy
		getPlayer().addSkill(SkillTable.getInstance().getInfo(696, 1), false);
		// Sacrifice Wizard
		getPlayer().addSkill(SkillTable.getInstance().getInfo(697, 1), false);
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
		// Divine Wizard Holy Flare
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(692, 1), false);
		// Divine Wizard Holy Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(693, 1), false);
		// Divine Wizard Holy Curtain
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(694, 1), false);
		// Divine Wizard Holy Cloud
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(695, 1), false);
		// Divine Wizard Surrender to Holy
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(696, 1), false);
		// Sacrifice Wizard
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(697, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineWizard());
	}
}
