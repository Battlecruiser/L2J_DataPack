package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DivineEnchanter extends L2Transformation
{
	private static final int[] SKILLS = {704,705,706,707,708,709,5779,619};
	public DivineEnchanter()
	{
		// id, colRadius, colHeight
		super(257, 8, 18.25);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 257 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Divine Enchanter Water Spirit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(704, 1), false);
		// Divine Enchanter Fire Spirit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(705, 1), false);
		// Divine Enchanter Wind Spirit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(706, 1), false);
		// Divine Enchanter Hero Spirit
		getPlayer().addSkill(SkillTable.getInstance().getInfo(707, 1), false);
		// Divine Enchanter Mass Binding
		getPlayer().addSkill(SkillTable.getInstance().getInfo(708, 1), false);
		// Sacrifice Enchanter
		getPlayer().addSkill(SkillTable.getInstance().getInfo(709, 1), false);
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
		// Divine Enchanter Water Spirit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(704, 1), false, false);
		// Divine Enchanter Fire Spirit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(705, 1), false, false);
		// Divine Enchanter Wind Spirit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(706, 1), false, false);
		// Divine Enchanter Hero Spirit
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(707, 1), false, false);
		// Divine Enchanter Mass Binding
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(708, 1), false, false);
		// Sacrifice Enchanter
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(709, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineEnchanter());
	}
}
