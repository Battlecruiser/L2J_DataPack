package transformations;

import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.instancemanager.TransformationManager;
import com.l2jserver.gameserver.model.L2Transformation;

public class DivineSummoner extends L2Transformation
{
	private static final int[] SKILLS = {710,711,712,713,714,5779,619};
	public DivineSummoner()
	{
		// id, colRadius, colHeight
		super(258, 10, 25);
	}
	
	@Override
	public void onTransform()
	{
		if (getPlayer().getTransformationId() != 258 || getPlayer().isCursedWeaponEquipped())
			return;
		
		if (getPlayer().getPet() != null)
			getPlayer().getPet().unSummon(getPlayer());
		
		transformedSkills();
	}
	
	public void transformedSkills()
	{
		// Divine Summoner Summon Divine Beast
		getPlayer().addSkill(SkillTable.getInstance().getInfo(710, 1), false);
		// Divine Summoner Transfer Pain
		getPlayer().addSkill(SkillTable.getInstance().getInfo(711, 1), false);
		// Divine Summoner Final Servitor
		getPlayer().addSkill(SkillTable.getInstance().getInfo(712, 1), false);
		// Divine Summoner Servitor Hill
		getPlayer().addSkill(SkillTable.getInstance().getInfo(713, 1), false);
		// Sacrifice Summoner
		getPlayer().addSkill(SkillTable.getInstance().getInfo(714, 1), false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().addSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().addSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(SKILLS);
	}
	
	@Override
	public void onUntransform()
	{
		if (getPlayer().getPet() != null)
			getPlayer().getPet().unSummon(getPlayer());
		
		removeSkills();
	}
	
	public void removeSkills()
	{
		// Divine Summoner Summon Divine Beast
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(710, 1), false);
		// Divine Summoner Transfer Pain
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(711, 1), false);
		// Divine Summoner Final Servitor
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(712, 1), false);
		// Divine Summoner Servitor Hill
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(713, 1), false);
		// Sacrifice Summoner
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(714, 1), false, false);
		// Decrease Bow/Crossbow Attack Speed
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(5491, 1), false);
		// Transform Dispel
		getPlayer().removeSkill(SkillTable.getInstance().getInfo(619, 1), false);
		
		getPlayer().setTransformAllowedSkills(EMPTY_ARRAY);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineSummoner());
	}
}
