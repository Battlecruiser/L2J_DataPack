package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class EpicQuestNative extends L2Transformation
{
	private static final int[] SKILLS = { 5437, 961 };
	
	public EpicQuestNative()
	{
		// id, colRadius, colHeight
		super(124, 8, 23.5);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 124 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Dissonance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		// Swift Dash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(961, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Dissonance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5437, 1), false);
		// Swift Dash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(961, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new EpicQuestNative());
	}
}
