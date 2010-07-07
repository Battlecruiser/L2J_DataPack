package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Ranku extends L2Transformation
{
	private static final int[] SKILLS = {731,732,5491,619};
	public Ranku()
	{
		// id, colRadius, colHeight
		super(309, 13, 29);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 309 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Ranku Dark Explosion
		getPlayer().addSkill(SkillTable.getInstance().getInfo(731, 1), false);
		// Ranku Stun Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(732, 1), false);
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
		// Ranku Dark Explosion
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(731, 1), false);
		// Ranku Stun Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(732, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Ranku());
	}
}
