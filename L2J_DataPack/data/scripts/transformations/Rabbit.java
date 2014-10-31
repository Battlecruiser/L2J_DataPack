package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class Rabbit extends L2Transformation
{
	public Rabbit()
	{
		// id, colRadius, colHeight
		super(105, 5, 4.5);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 105 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Rabbit Magic Eye
		getPlayer().addSkill(SkillTable.getInstance().getInfo(629, 1), false);
		// Rabbit Tornado
		getPlayer().addSkill(SkillTable.getInstance().getInfo(630, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{629,630,5491,619});
	}

	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
		// Rabbit Magic Eye
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(629, 1), false);
		// Rabbit Tornado
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(630, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Rabbit());
	}
}
