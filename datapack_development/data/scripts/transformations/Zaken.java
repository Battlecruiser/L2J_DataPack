package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Zaken extends L2Transformation
{
	private static final int[] SKILLS = {715,716,717,718,719,5491,619};
	public Zaken()
	{
		// id, colRadius, colHeight
		super(305, 16, 32);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 305 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Zaken Energy Drain (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(715, 4), false);
		// Zaken Hold (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(716, 4), false);
		// Zaken Concentrated Attack (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(717, 4), false);
		// Zaken Dancing Sword (up to 4 levels)
		getPlayer().addSkill(SkillTable.getInstance().getInfo(718, 4), false);
		// Zaken Vampiric Rage
		getPlayer().addSkill(SkillTable.getInstance().getInfo(719, 1), false);
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
		// Zaken Energy Drain (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(715, 4), false);
		// Zaken Hold (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(716, 4), false);
		// Zaken Concentrated Attack (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(717, 4), false);
		// Zaken Dancing Sword (up to 4 levels)
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(718, 4), false);
		// Zaken Vampiric Rage
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(719, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Zaken());
	}
}
