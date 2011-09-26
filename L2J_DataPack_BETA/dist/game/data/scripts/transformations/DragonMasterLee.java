package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DragonMasterLee extends L2Transformation
{
	private static final int[] SKILLS = {5491,619,20002,20004,20005};
	public DragonMasterLee()
	{
		// id, colRadius, colHeight
		super(20005, 8, 19.3);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 20005 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Dragon Slash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(20002, 1), false);
		// Dragon Dash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(20004, 1), false);
		// Dragon Aura
		getPlayer().addSkill(SkillTable.getInstance().getInfo(20005, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Dragon Slash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(20002, 1), false);
		// Dragon Dash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(20004, 1), false);
		// Dragon Aura
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(20005, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DragonMasterLee());
	}
}