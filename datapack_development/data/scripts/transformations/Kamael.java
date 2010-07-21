package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Kamael extends L2Transformation
{
	private static final int[] SKILLS = {539,540,1471,1472,5491,619};
	public Kamael()
	{
		// id, duration (secs), colRadius, colHeight
		super(251, 9, 38);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 251 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Nail Attack
		getPlayer().addSkill(SkillTable.getInstance().getInfo(539, 1), false);
		// Wing Assault
		getPlayer().addSkill(SkillTable.getInstance().getInfo(540, 1), false);
		// Soul Sucking
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1471, 1), false);
		// Death Beam
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1472, 1), false);
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
		// Nail Attack
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(539, 1), false);
		// Wing Assault
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(540, 1), false);
		// Soul Sucking
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1471, 1), false);
		// Death Beam
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1472, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Kamael());
	}
}
