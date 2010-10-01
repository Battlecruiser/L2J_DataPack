package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class GrailApostleWeak extends L2Transformation
{
	private static final int[] SKILLS = {559,560,561,562,5491,619};
	public GrailApostleWeak()
	{
		// id, colRadius, colHeight
		super(203, 10, 35);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 203 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Spear (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(559, 2), false);
		// Power Slash (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(560, 2), false);
		// Bless of Angel (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(561, 2), false);
		// Wind of Angel (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(562, 2), false);
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
		// Spear (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(559, 2), false);
		// Power Slash (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(560, 2), false);
		// Bless of Angel (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(561, 2), false, false);
		// Wind of Angel (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(562, 2), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new GrailApostleWeak());
	}
}
