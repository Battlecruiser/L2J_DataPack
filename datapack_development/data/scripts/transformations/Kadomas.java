package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Kadomas extends L2Transformation
{
	private static final int[] SKILLS = {23154,619};
	public Kadomas()
	{
		// id, colRadius, colHeight
		super(20000, 24.5, 14);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 20000 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		//Kadomas Special Skill - Fireworks
		getPlayer().addSkill(SkillTable.getInstance().getInfo(23154, 1), false);
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
		//Kadomas Special Skill - Fireworks
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(23154, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Kadomas());
	}
}
