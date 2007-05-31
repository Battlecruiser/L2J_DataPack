# Growth-capable mobs: Polymorphing upon successful feeding.
# Written by Fulminus
# # # # # # # # # # #

import sys
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest
from net.sf.l2j.gameserver.serverpackets import CreatureSay
from net.sf.l2j.gameserver.serverpackets import SocialAction
from net.sf.l2j.gameserver.ai import CtrlIntention
from net.sf.l2j.util import Rnd;

GOLDEN_SPICE = 6643
CRYSTAL_SPICE = 6644
SKILL_GOLDEN_SPICE = 2188
SKILL_CRYSTAL_SPICE = 2189
foodSkill = {GOLDEN_SPICE:SKILL_GOLDEN_SPICE, CRYSTAL_SPICE:SKILL_CRYSTAL_SPICE}

class growing_beasts(JQuest) :

    # init function.  Add in here variables that you'd like to be inherited by subclasses (if any)
    def __init__(self,id,name,descr):
        # firstly, don't forget to call the parent constructor to prepare the event triggering
        # mechanisms etc.
        JQuest.__init__(self,id,name,descr)
        # DEFINE MEMBER VARIABLES FOR THIS AI 
        # mobId: current_growth_level, {food: [list of possible mobs[possible sublist of tamed pets]]}, chance of growth
        self.growthCapableMobs = {
            # Alpen Kookabura
            21451: [0,{GOLDEN_SPICE:[21452,21453, 21454, 21455],CRYSTAL_SPICE:[21456,21457, 21458, 21459]},100],
            21452: [1,{GOLDEN_SPICE:[21460,21462],CRYSTAL_SPICE:[]},40],
            21453: [1,{GOLDEN_SPICE:[21461,21463],CRYSTAL_SPICE:[]},40],
            21454: [1,{GOLDEN_SPICE:[21460,21462],CRYSTAL_SPICE:[]},40],
            21455: [1,{GOLDEN_SPICE:[21461,21463],CRYSTAL_SPICE:[]},40],
            21456: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21464,21466]},40],
            21457: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21465,21467]},40],
            21458: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21464,21466]},40],
            21459: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21465,21467]},40],
            21460: [2,{GOLDEN_SPICE:[[21468,21824],[16017,16018]],CRYSTAL_SPICE:[]},25],
            21461: [2,{GOLDEN_SPICE:[[21469,21825],[16017,16018]],CRYSTAL_SPICE:[]},25],
            21462: [2,{GOLDEN_SPICE:[[21468,21824],[16017,16018]],CRYSTAL_SPICE:[]},25],
            21463: [2,{GOLDEN_SPICE:[[21469,21825],[16017,16018]],CRYSTAL_SPICE:[]},25],
            21464: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21468,21824],[16017,16018]]},25],
            21465: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21469,21825],[16017,16018]]},25],
            21466: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21468,21824],[16017,16018]]},25],
            21467: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21469,21825],[16017,16018]]},25],
            # Alpen Buffalo
            21470: [0,{GOLDEN_SPICE:[21471,21472, 21473, 21474],CRYSTAL_SPICE:[21475,21476, 21477, 21478]},100],
            21471: [1,{GOLDEN_SPICE:[21479,21481],CRYSTAL_SPICE:[]},40],
            21472: [1,{GOLDEN_SPICE:[21481,21482],CRYSTAL_SPICE:[]},40],
            21473: [1,{GOLDEN_SPICE:[21479,21481],CRYSTAL_SPICE:[]},40],
            21474: [1,{GOLDEN_SPICE:[21480,21482],CRYSTAL_SPICE:[]},40],
            21475: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21483,21485]},40],
            21476: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21484,21486]},40],
            21477: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21483,21485]},40],
            21478: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21484,21486]},40],
            21479: [2,{GOLDEN_SPICE:[[21487,21826],[16013,16014]],CRYSTAL_SPICE:[]},25],
            21480: [2,{GOLDEN_SPICE:[[21488,21827],[16013,16014]],CRYSTAL_SPICE:[]},25],
            21481: [2,{GOLDEN_SPICE:[[21487,21826],[16013,16014]],CRYSTAL_SPICE:[]},25],
            21482: [2,{GOLDEN_SPICE:[[21488,21827],[16013,16014]],CRYSTAL_SPICE:[]},25],
            21483: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21487,21826],[16013,16014]]},25],
            21484: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21488,21827],[16013,16014]]},25],
            21485: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21487,21826],[16013,16014]]},25],
            21486: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21488,21827],[16013,16014]]},25],
            # Alpen Cougar
            21489: [0,{GOLDEN_SPICE:[21490,21491, 21492, 21493],CRYSTAL_SPICE:[21494,21495, 21496, 21497]},100],
            21490: [1,{GOLDEN_SPICE:[21498,21500],CRYSTAL_SPICE:[]},40],
            21491: [1,{GOLDEN_SPICE:[21499,21501],CRYSTAL_SPICE:[]},40],
            21492: [1,{GOLDEN_SPICE:[21498,21500],CRYSTAL_SPICE:[]},40],
            21493: [1,{GOLDEN_SPICE:[21499,21501],CRYSTAL_SPICE:[]},40],
            21494: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21502,21504]},40],
            21495: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21503,21505]},40],
            21496: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21502,21504]},40],
            21497: [1,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[21503,21505]},40],
            21498: [2,{GOLDEN_SPICE:[[21506,21828],[16015,16016]],CRYSTAL_SPICE:[]},25],
            21499: [2,{GOLDEN_SPICE:[[21507,21829],[16015,16016]],CRYSTAL_SPICE:[]},25],
            21500: [2,{GOLDEN_SPICE:[[21506,21828],[16015,16016]],CRYSTAL_SPICE:[]},25],
            21501: [2,{GOLDEN_SPICE:[[21507,21829],[16015,16016]],CRYSTAL_SPICE:[]},25],
            21502: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21506,21828],[16015,16016]]},25],
            21503: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21507,21829],[16015,16016]]},25],
            21504: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21506,21828],[16015,16016]]},25],
            21505: [2,{GOLDEN_SPICE:[],CRYSTAL_SPICE:[[21507,21829],[16015,16016]]},25]
            }
        self.Text = [["What did you just do to me?","You want to tame me, huh?","Do not give me this. Perhaps you will be in danger.","Bah bah. What is this unpalatable thing?","My belly has been complaining.  This hit the spot.","What is this? Can I eat it?","You don't need to worry about me.","Delicious food, thanks.","I am starting to like you!","Gulp"], 
                    ["I do not think you have given up on the idea of taming me.","That is just food to me.  Perhaps I can eat your hand too.","Will eating this make me fat? Ha my ha","Why do you always feed me?","Do not trust me.  I may betray you"], 
                    ["Destroy","Look what you have done!","Strange feeling...!  Evil intentions grow in my heart...!","It is happenning!","This is sad...Good is sad...!"]]

        self.feedInfo = [] # will hold tuples of: [objectId of mob, objectId of player feeding it]
        
        for i in self.growthCapableMobs.keys() :
            self.addSkillUseId(i)
            self.addKillId(i)


    def spawnNext(self, npc, growthLevel,player,food) :
        npcId = npc.getNpcId()
        isTrained = 0
        nextNpcId = 0

        # find the next mob to spawn, based on the current npcId, growthlevel, and food.
        if growthLevel == 2:
            rand = Rnd.get(2)
            # if tamed, the mob that will spawn depends on the class type (fighter/mage) of the player!
            if rand == 1 :
                isTrained = 1
                if player.getClassId().isMage() :
                    nextNpcId = self.growthCapableMobs[npcId][1][food][1][1]
                else :
                    nextNpcId = self.growthCapableMobs[npcId][1][food][1][0]
            # if not tamed, there is a small chance that have "mad cow" disease.
            # that is a stronger-than-normal animal that attacks its feeder
            else :
                if Rnd.get(5) == 0 :
                    nextNpcId = self.growthCapableMobs[npcId][1][food][0][1]
                else :
                    nextNpcId = self.growthCapableMobs[npcId][1][food][0][0]
        # all other levels of growth are straight-forward
        else :            
            nextNpcId = self.growthCapableMobs[npcId][1][food][Rnd.get(len(self.growthCapableMobs[npcId][1][food]))]
        
        # remove the feedinfo of the mob that got despawned, if any
        if self.feedInfo.count([npc.getObjectId(),player.getObjectId()]) :
            self.feedInfo.remove([npc.getObjectId(),player.getObjectId()])
        
        # despawn the old mob
        npc.onDecay()
        
        # spawn the new mob
        spawnObjId = self.getPcSpawn(player).addSpawn(nextNpcId,npc,False)
        nextNpc = self.getPcSpawn(player).getSpawn(spawnObjId).getLastSpawn()
        
        # register the player in the feedinfo for the mob that just spawned
        self.feedInfo = self.feedInfo + [[nextNpc.getObjectId(),player.getObjectId()]]

        # if this is finally a trained mob, then despawn any other trained mobs that the
        # player might have.  
        if isTrained :
            oldTrained = player.getTrainedBeast()
            if oldTrained :
                oldTrained.onDecay()
            player.setTrainedBeast(nextNpc)
            nextNpc.setFoodType(foodSkill[food])
        # if not trained, the newly spawned mob will automatically be agro against its feeder
        # (what happened to "never bite the hand that feeds you" anyway?!)
        else :            
            nextNpc.addDamageHate(player,0,99999)
            nextNpc.setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)

    def onSkillUse (self,npc,player,skill):
        # gather some values on local variables
        npcId = npc.getNpcId()
        skillId = skill.getId()
        # check if the npc and skills used are valid for this script.  Exit if invalid.
        if npcId not in self.growthCapableMobs.keys() : return
        if skillId not in [SKILL_GOLDEN_SPICE,SKILL_CRYSTAL_SPICE] : return

        food = 0
        if skillId == SKILL_GOLDEN_SPICE :
            food = GOLDEN_SPICE
        elif skillId == SKILL_CRYSTAL_SPICE :
            food = CRYSTAL_SPICE

        # do nothing if this mob doesn't eat the specified food (food gets consumed but has no effect).
        if len(self.growthCapableMobs[npcId][1][food]) == 0 : return

        # more value gathering on local variables       
        objectId = npc.getObjectId()
        growthLevel = self.growthCapableMobs[npcId][0]

        # display the social action of the beast eating the food.
        npc.broadcastPacket(SocialAction(objectId,2))

        # rare random talk...
        if Rnd.get(20) == 0 :
            npc.broadcastPacket(CreatureSay(objectId,0,npc.getName(),self.Text[growthLevel][Rnd.get(len(self.Text[growthLevel]))]))

        if growthLevel > 0 :
            # check if this is the same player as the one who raised it from growth 0.
            # if no, then do not allow a chance to raise the pet (food gets consumed but has no effect).
            if not self.feedInfo.count([objectId,player.getObjectId()]) : return

        # Polymorph the mob, with a certain chance, given its current growth level
        if Rnd.get(100) < self.growthCapableMobs[npcId][2] :
            self.spawnNext(npc, growthLevel,player,food)
        return

    def onKill (self,npc,player):
        # remove the feedinfo of the mob that got killed, if any
        if self.feedInfo.count([npc.getObjectId(),player.getObjectId()]) :
            self.feedInfo.remove([npc.getObjectId(),player.getObjectId()])

# now call the constructor (starts up the ai)
QUEST		= growing_beasts(-1,"group_template","ai")
    
print "AI: group template: Feedable Beasts...loaded!"
