package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class EpicQuestFrog extends L2Transformation
{
	private static final int[] SKILLS = { 5437, 959 };
	
	public EpicQuestFrog()
	{
		// id, colRadius, colHeight
		super(111, 20, 10);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 111 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		// Frog Jump
		getPlayer().addSkill(SkillTable.getInstance().getInfo(959, 1), false);

		getPlayer().setTransformAllowedSkills(SKILLS);
	}

	@Override
	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		// Frog Jump
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(959, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new EpicQuestFrog());
	}
}
