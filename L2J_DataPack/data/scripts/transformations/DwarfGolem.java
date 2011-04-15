package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DwarfGolem extends L2Transformation
{
	private static final int[] SKILLS = {806,807,808,809,5491,619};
	public DwarfGolem()
	{
		// id, colRadius, colHeight
		super(259, 31, 51.8);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 259 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Magic Obstacle
		getPlayer().addSkill(SkillTable.getInstance().getInfo(806, 1), false);
		// Over-hit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(807, 1), false);
		// Golem Punch
		getPlayer().addSkill(SkillTable.getInstance().getInfo(808, 1), false);
		// Golem Tornado Swing
		getPlayer().addSkill(SkillTable.getInstance().getInfo(809, 1), false);
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
		// Magic Obstacle
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(806, 1), false);
		// Over-hit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(807, 1), false);
		// Golem Punch
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(808, 1), false);
		// Golem Tornado Swing
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(809, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DwarfGolem());
	}
}
