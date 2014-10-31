package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class InquisitorShilienElder extends L2Transformation
{
	public InquisitorShilienElder()
	{
		// id
		super(318);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 318 || getPlayer().isCursedWeaponEquipped())
			return;
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 43)
			lvl = (getPlayer().getLevel() - 43);
		
		// Divine Punishment
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1523, lvl), false);
		// Divine Flash
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1528, lvl), false);
		// Holy Weapon
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1043, 1), false);
		// Surrender to the Holy
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1524, lvl), false);
		// Divine Curse
		getPlayer().addSkill(SkillTable.getInstance().getInfo(1525, lvl), false);
		// Set allowed skills
		getPlayer().setTransformAllowedSkills(new int[]{838,1523,1528,1524,1525,1430,1303,1059,1043});
		// Switch Stance
		getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
	}
	
	@Override
	public void onUntransform()
	{
		removeSkills();
	}
	
	public void removeSkills()
	{
		int lvl = 1;
		if (getPlayer().getLevel() > 43)
			lvl = (getPlayer().getLevel() - 43);
		
		// Divine Punishment
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1523, lvl), false);
		// Divine Flash
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1528, lvl), false);
		// Holy Weapon
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1043, 1), false, false);
		// Surrender to the Holy
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1524, lvl), false);
		// Divine Curse
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(1525, lvl), false);
		// Switch Stance
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InquisitorShilienElder());
	}
}
