package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class Treykan extends L2Transformation
{
	private static final int[] SKILLS = new int[]
	{
		619, 967, 968, 969, 5437
	};
	
	public Treykan()
	{
		// id, colRadius, colHeight
		super(126, 25, 27.00);
	}
	
	@Override
	public void onTransform()
	{
		if ((getPlayer().getTransformationId() != 126) || getPlayer().isCursedWeaponEquipped())
		{
			return;
		}
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Cursed Body
		getPlayer().addSkill(SkillTable.getInstance().getInfo(967, 1), false);
		// Treykan Claw
		getPlayer().addSkill(SkillTable.getInstance().getInfo(968, 1), false);
		// Treykan Dash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(969, 1), false);
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		// Cursed Body
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(967, 1), false);
		// Treykan Claw
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(968, 1), false);
		// Treykan Dash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(969, 1), false);
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Treykan());
	}
}
