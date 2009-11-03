package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class Ranku extends L2Transformation
{
	public Ranku()
	{
		// id, colRadius, colHeight
		super(309, 13, 29);
	}

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
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{731,732,5491,619});
	}

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
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Ranku());
	}
}
