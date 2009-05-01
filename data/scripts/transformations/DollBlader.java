package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

/**
 * Description: <br>
 * This will handle the transformation, giving the skills, and removing them, when the player logs out and is transformed these skills
 * do not save. 
 * When the player logs back in, there will be a call from the enterworld packet that will add all their skills.
 * The enterworld packet will transform a player.
 * 
 * @author Ahmed
 *
 */
public class DollBlader extends L2Transformation
{
	public DollBlader()
	{
		// id, duration (secs), colRadius, colHeight
		super(7, 1800, 6.0, 12.0);
	}

	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 7 || getPlayer().isCursedWeaponEquipped())
			return;

		// give transformation skills
		transformedSkills();
	}

	public void transformedSkills()
	{
		// Transfrom Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		if (getPlayer().getLevel() >= 76)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(754, 1), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 3), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(754, 1), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 2), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 2), false);
		}
		else if (getPlayer().getLevel() >= 70)
		{
			getPlayer().addSkill(SkillTable.getInstance().getInfo(754, 1), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(752, 1), false);
			getPlayer().addSkill(SkillTable.getInstance().getInfo(753, 1), false);
		}

		getPlayer().setTransformAllowedSkills(new int[]{619,5491,752,753,754});
	}

	public void onUntransform()
	{
		// remove transformation skills
		removeSkills();
	}

	public void removeSkills()
	{
		// Transfrom Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		if (getPlayer().getLevel() >= 76)
		{
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 3), false);
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 3), false);
		}
		else if (getPlayer().getLevel() >= 73)
		{
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 2), false);
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 2), false);
		}
		else
		{
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(752, 1), false);
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(753, 1), false);
		}
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(754, 1), false, false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DollBlader());
	}
}