package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class AurabirdFalcon extends L2Transformation
{
	private static final int[] SKILLS = new int[]{884,885,886,888,890,891,894,911,932,619};
	
	public AurabirdFalcon()
	{
		// id, colRadius, colHeight
		super(8, 38, 14.25);
	}

	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 8 || getPlayer().isCursedWeaponEquipped())
			return;
		getPlayer().setIsFlyingMounted(true);

		transformedSkills();
	}

	public void transformedSkills()
	{
		// Air Blink
		if (getPlayer().getLevel() >= 75)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(885, 1), false);
		
		// Exhilarate
		if (getPlayer().getLevel() >= 83)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(894, 1), false);

		int lvl = getPlayer().getLevel() -74;

		if (lvl > 0)
		{
			// Air Assault (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(884, lvl), false);
			// Air Shock Bomb (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(886, lvl), false);
			// Energy Storm (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(888, lvl), false);
			// Prodigious Flare (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(890, lvl), false);
			// Energy Shot (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(891, lvl), false);
			// Energy Burst (up to 11 levels)
			getPlayer().addSkill(SkillTable.getInstance().getInfo(911, lvl), false);
		}
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(SKILLS);
	}

	@Override
	public void onUntransform()
	{
		getPlayer().setIsFlyingMounted(false);

		removeSkills();
	}

	public void removeSkills()
	{
		// Air Blink
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(885, 1), false);
		
		// Exhilarate
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(894, 1), false);

		int lvl = getPlayer().getLevel() -74;

		if (lvl > 0)
		{
			// Air Assault (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(884, lvl), false);
			// Air Shock Bomb (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(886, lvl), false);
			// Energy Storm (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(888, lvl), false);
			// Prodigious Flare (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(890, lvl), false);
			// Energy Shot (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(891, lvl), false);
			// Energy Burst (up to 11 levels)
			getPlayer().removeSkill(SkillTable.getInstance().getInfo(911, lvl), false);
		}
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);

		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new AurabirdFalcon());
	}
}
