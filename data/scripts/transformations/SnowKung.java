package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class SnowKung extends L2Transformation
{
	private static final int[] SKILLS = {940,943,5437,619};
	public SnowKung()
	{
		// id, colRadius, colHeight
		super(114, 28, 30);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 114 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Fake Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(940, 1), false);
		// Special Motion 
		getPlayer().addSkill(SkillTable.getInstance().getInfo(943, 1), false);
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 2), false);
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
		// Fake Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(940, 1), false);
		// Special Motion 
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(943, 1), false);
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 2), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new SnowKung());
	}
}
