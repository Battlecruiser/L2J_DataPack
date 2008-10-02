package transformations;

import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.instancemanager.TransformationManager;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2Transformation;

public class InquisitorBishop extends L2Transformation
{
	public InquisitorBishop()
	{
		// id, duration (secs), colRadius, colHeight
		super(316, Integer.MAX_VALUE, 8.0, 22.0);
	}
	
    public void onTransform()
    {
            // Disable all character skills.
            for (L2Skill sk : this.getPlayer().getAllSkills())
            {
                    if (sk != null && !sk.isPassive())
                    {
                            switch (sk.getId())
                            {
                        	// Invocation
                			case 1430:
                			// Holy Weapon
                			case 1043:
                			// Hold Undead
                			case 1042:
                			// Turn Undead
                			case 1400:
                			// Celestial Shield
                			case 1418:	
                                    {
                                            // Those Skills wont be removed.
                                            break;
                                    }
                                    default:
                                    {
                                            this.getPlayer().removeSkill(sk, false, false);
                                            break;
                                    }
                            }
                    }
                            
            }
            if (this.getPlayer().transformId() > 0 && !this.getPlayer().isCursedWeaponEquipped())
            {
                    // give transformation skills
                    transformedSkills();
                    return;
            }
            // give transformation skills
            transformedSkills();
    }
    
    public void transformedSkills()
    {
        if (this.getPlayer().getLevel() > 43)
    {
        // Divine Punishment
        this.getPlayer().addSkill(SkillTable.getInstance().getInfo(1523, this.getPlayer().getLevel()-43), false);
        // Divine Flash
        this.getPlayer().addSkill(SkillTable.getInstance().getInfo(1528, this.getPlayer().getLevel()-43), false);
        // Surrender to the Holy
        this.getPlayer().addSkill (SkillTable.getInstance().getInfo(1524, this.getPlayer().getLevel()-43), false);
        // Divine Curse
        this.getPlayer().addSkill (SkillTable.getInstance().getInfo(1525, this.getPlayer().getLevel()-43), false);
        // Switch Stance
		this.getPlayer().addSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Send a Server->Client packet StatusUpdate to the L2PcInstance.
        this.getPlayer().sendSkillList();
    	}
    }
        
        public void onUntransform()  
        {	
		// remove transformation skills
		removeSkills();
        }

	public void removeSkills()
	{
		// Divine Punishment
        this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(1523, this.getPlayer().getLevel()-43), false);
        // Divine Flash
        this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(1528, this.getPlayer().getLevel()-43), false);
        // Surrender to the Holy
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(1524, this.getPlayer().getLevel()-43), false);
		// Divine Curse
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(1525, this.getPlayer().getLevel()-43), false);
		// Switch Stance
		this.getPlayer().removeSkill(SkillTable.getInstance().getInfo(838, 1), false);
		// Send a Server->Client packet StatusUpdate to the L2PcInstance.
        this.getPlayer().sendSkillList();
	}

	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new InquisitorBishop());
	}
}