package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Benom extends L2Transformation
{
	private static final int[] SKILLS = new int[]{725,726,727,5491,619};
	
	public Benom()
	{
		// id, colRadius, colHeight
		super(307, 20, 56);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 307 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Benom Power Smash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(725, 1), false);
		// Benom Sonic Storm
		getPlayer().addSkill(SkillTable.getInstance().getInfo(726, 1), false);
		// Benom Disillusion
		getPlayer().addSkill(SkillTable.getInstance().getInfo(727, 1), false);
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
		// Benom Power Smash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(725, 1), false);
		// Benom Sonic Storm
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(726, 1), false);
		// Benom Disillusion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(727, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Benom());
	}
}
