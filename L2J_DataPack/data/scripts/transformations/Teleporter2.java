package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Teleporter2 extends L2Transformation
{
	private static final int[] SKILLS = {5656,5657,5658,5491,8248};
	public Teleporter2()
	{
		// id, colRadius, colHeight
		super(107, 8, 25);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 107 || getPlayer().isCursedWeaponEquipped())
			return;

		transformedSkills();
	}

	public void transformedSkills()
	{
		/*
		Commented out until we figure out how to remove the skills properly.
		What happens if a player transforms at level 40, gets the level 40 version of the skill, then somehow levels up?
		Then when we untransform, the script will look for the level 41 version of the skill, right?
		Or will it still remove the level 40 skill? Needs to be tested.
		// Gatekeeper Aura Flare
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5656, getPlayer().getLevel()), false);
		// Gatekeeper Prominence
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5657, getPlayer().getLevel()), false);
		// Gatekeeper Flame Strike
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5658, getPlayer().getLevel()), false);
		// Gatekeeper Berserker Spirit (there are two levels, when do players get access to level 2?)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5659, 1), false);
		 */
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Cancel Gatekeeper Transformation
		getPlayer().addSkill(SkillTable.getInstance().getInfo(8248, 1), false);

		getPlayer().setTransformAllowedSkills(SKILLS);
	}

	@Override
	public void onUntransform()
	{
		removeSkills();
	}

	public void removeSkills()
	{
/*
Commented out until we figure out how to remove the skills properly.
What happens if a player transforms at level 40, gets the level 40 version of the skill, then somehow levels up?
Then when we untransform, the script will look for the level 41 version of the skill, right?
Or will it still remove the level 40 skill? Needs to be tested.
		// Gatekeeper Aura Flare
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5656, getPlayer().getLevel()), false);
		// Gatekeeper Prominence
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5657, getPlayer().getLevel()), false);
		// Gatekeeper Flame Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5658, getPlayer().getLevel()), false);
		// Gatekeeper Berserker Spirit (there are two levels, when do players get access to level 2?)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5659, 1), false);
*/
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Cancel Gatekeeper Transformation
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(8248, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Teleporter2());
	}
}
