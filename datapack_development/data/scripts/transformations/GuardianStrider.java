package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class GuardianStrider extends L2Transformation
{
	public GuardianStrider()
	{
		// id, colRadius, colHeight
		super(123, 13, 40);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 123 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Dismount
		getPlayer().addSkill(SkillTable.getInstance().getInfo(839, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{839});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Dismount
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(839, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new GuardianStrider());
	}
}
