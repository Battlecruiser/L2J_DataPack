package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Transformation;

public class VanguardShilienKnight extends L2Transformation
{
	public VanguardShilienKnight()
	{
		// id, duration (secs), colRadius, colHeight
		super(315, Integer.MAX_VALUE, 6.0, 23.0);
	}
	
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 315 || getPlayer().isCursedWeaponEquipped())
			return;

		// give transformation skills
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		if (getPlayer().getLevel() > 43)
		{
			// Double Strike
			getPlayer().addSkill(SkillTable.getInstance().getInfo(817, getPlayer().getLevel() - 43), false);
			// Blade Hurricane
			getPlayer().addSkill(SkillTable.getInstance().getInfo(815, getPlayer().getLevel() - 43), false);
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,817,815,28,18,22,33,401,278,289,279});
		}
		else
			getPlayer().setTransformAllowedSkills(new int[]{838,5491,28,18,22,33,401,278,289,279});
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}
	
	public void onUntransform()
	{
		// remove transformation skills
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Double Strike
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(817, getPlayer().getLevel() - 43), false);
		// Blade Hurricane
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(815, getPlayer().getLevel() - 43), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false); 
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);

		getPlayer().setTransformAllowedSkills(new int[]{});
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new VanguardShilienKnight());
	}
}