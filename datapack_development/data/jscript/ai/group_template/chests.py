# # # # # # # # # # #
# Chest AI implementation.
# Written by Fulminus
# # # # # # # # # # #
import sys
from net.sf.l2j.gameserver.ai import CtrlIntention
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.util import Rnd;

SKILL_DELUXE_KEY = 2229

class chests(JQuest) :

    # init function.  Add in here variables that you'd like to be inherited by subclasses (if any)
    def __init__(self,id,name,descr):
        # firstly, don't forget to call the parent constructor to prepare the event triggering
        # mechanisms etc.
        JQuest.__init__(self,id,name,descr)

        self.chests = [18265,18266,18267,18268,18269,18270,18271,18272,18273,18274, \
                       18275,18276,18277,18278,18279,18280,18281,18282,18283,18284, \
                       18285,18286,21801,21802,21803,21804,21805,21806,21807,21808, \
                       21809,21810,21811,21812,21813,21814,21815,21816,21817,21818, \
                       21819,21820,21821,21822]

        for i in self.chests :
            self.addSkillUseId(i)
            self.addAttackId(i)

    def onSkillUse (self,npc,player,skill):
        npcId = npc.getNpcId()
        skillId = skill.getId()
        skillLevel= skill.getLevel()

        # check if the npc and skills used are valid for this script.  Exit if invalid.
        if npcId not in self.chests : return

        # if this was a mimic, set the target, start the skills and become agro
        if not npc.isInteracted() :
            npc.setInteracted()
            if npc.isBox() :
                if skillId == SKILL_DELUXE_KEY :
                    # check the chance to open the box
                    chance = 0
                    keyLevelNeeded = npc.getLevel()/10

                    if skillLevel > keyLevelNeeded :
                        player.sendMessage("The key seems not to be adapted.")
                        player.sendPacket(PlaySound("interfacesound.system_close_01"))
                    else :
                        chance = 100 - 40 * (keyLevelNeeded - skillLevel)

                    # success, pretend-death with rewards:  npc.reduceCurrentHp(99999999, player)
                    if Rnd.get(100) < chance :
                        npc.setSpecialDrop();
                        npc.reduceCurrentHp(99999999, player)
                    # failure, despawn without any rewards:  npc.deleteMe()
                    else :
                        # todo: perhaps a self-destruct bomb with random chance goes here?
                        npc.onDecay()
                # any other skill used: despawn without any rewards
                else :
                    # todo: perhaps a self-destruct bomb with random chance goes here?
                    npc.onDecay()
            else :  # if this weren't a box, upon interaction start the mimic behaviors...
                # todo: perhaps a self-buff (skill id 4245) with random chance goes here?
                npc.addDamageHate(player,0,999)
                npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)
        return

    def onAttack(self,npc,player) :
        npcId = npc.getNpcId()
        # check if the npc and skills used are valid for this script.  Exit if invalid.
        if npcId not in self.chests : return

        # if this was a mimic, set the target, start the skills and become agro
        if not npc.isInteracted() :
            npc.setInteracted()
            if npc.isBox() :
                npc.onDecay()
            else :  # if this weren't a box, upon interaction start the mimic behaviors...
                # todo: perhaps a self-buff (skill id 4245) with random chance goes here?
                npc.addDamageHate(player,0,999)
                npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)
        return

# now call the constructor (starts up the ai)
QUEST           = chests(-1,"group_template","ai")

print "AI: group template: Treasure Chests...loaded!"
