package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class VanguardDarkAvenger extends L2Transformation
{
	public VanguardDarkAvenger()
	{
		// id
		super(313);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 313 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 42)
			lvl = (getPlayer().getLevel() - 42);
		
		// Dual Weapon Mastery
		getPlayer().addSkill(SkillTable.getInstance().getInfo(144, lvl), false);
		// Blade Hurricane
		getPlayer().addSkill(SkillTable.getInstance().getInfo(815, lvl), false);
		// Double Strike
		getPlayer().addSkill(SkillTable.getInstance().getInfo(817, lvl), false);
		// Boost Morale
		getPlayer().addSkill(SkillTable.getInstance().getInfo(956, lvl), false);
		// Triple Blade Slash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(958, lvl), false);
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Set allowed skills
		getPlayer().setTransformAllowedSkills(new int[]{18,28,65,86,144,283,815,817,838,956,958,401});
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 42)
			lvl = (getPlayer().getLevel() - 42);
		
		// Dual Weapon Mastery
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(144, lvl), false);
		// Blade Hurricane
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(815, lvl), false);
		// Double Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(817, lvl), false);
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Boost Morale
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(956, lvl), false, false);
		// Triple Blade Slash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(958, lvl), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardDarkAvenger());
	}
}
