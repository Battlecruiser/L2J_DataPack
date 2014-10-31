package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class FlyingFinalForm extends L2Transformation
{
	public FlyingFinalForm()
	{
		// id, colRadius, colHeight
		super(260, 9, 38);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 260 || getPlayer().isCursedWeaponEquipped())
			return;

		getPlayer().setIsFlyingMounted(true);

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Life to Soul
		getPlayer().addSkill(SkillTable.getInstance().getInfo(953, 1), false);
		// Soul Sucking
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1545, 1), false);

		int lvl = getPlayer().getLevel() -78;

		if (lvl > 0)
		{
			// Nail Attack (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(950, lvl), false);
			// Wing Assault (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(951, lvl), false);
			// Death Beam (up to 7 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(1544, lvl), false);
		}
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{932,950,951,953,1544,1545,619});
	}

	public void onUntransform()
	{
		getPlayer().setIsFlyingMounted(false);

		removeSkills();
	}

	public void removeSkills()
	{
		// Life to Soul
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(953, 1), false);
		// Soul Sucking
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1545, 1), false);

		int lvl = getPlayer().getLevel() -78;

		if (lvl > 0)
		{
			// Nail Attack (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(950, lvl), false);
			// Wing Assault (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(951, lvl), false);
			// Death Beam (up to 7 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(1544, lvl), false);
		}
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new FlyingFinalForm());
	}
}
