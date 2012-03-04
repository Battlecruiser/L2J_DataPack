# Contributed by t0rm3nt0r (tormentor2000@mail.ru) to the Official L2J Datapack Project
# Visit http://www.l2jdp.com/forum/ for more details.

import sys
from com.l2jserver.gameserver.model.quest import State
from com.l2jserver.gameserver.model.quest import QuestState
from com.l2jserver.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jserver.gameserver.network.serverpackets import SocialAction

qn = "175_TheWayOfTheWarrior"

#NPC'S
PERWAN = 32133
KEKROPUS = 32138

#ITEM'S
WARRIORS_SWORD = 9720
LESSER_HEALING_POTIONS = 1060
ECHO = range(4412,4417)
SOULSHOT_FOR_BEGINNERS = 5789
WOLF_TAIL = 9807
MUERTOS_CLAW = 9808

#MOBS
MOUNTAIN_WEREWOLF = 22235
MUERTOS = [22236]+range(22239,22241)+range(22242,22244)+range(22245,22247)

NEWBIE_REWARD = 16
class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
 
 def onAdvEvent (self,event,npc, player) :
     htmltext = event
     st = player.getQuestState(qn)
     if not st : return
     player = st.getPlayer()
     if event == "32138-04.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
     elif event == "32133-06.htm" :
       st.set("cond","6") 
       st.playSound("ItemSound.quest_accept")
     elif event == "32138-09.htm" :
       st.set("cond","7") 
       st.playSound("ItemSound.quest_accept")  
     elif event == "32138-12.htm" :
       st.takeItems(MUERTOS_CLAW,-1)
       st.giveItems(57,8799)
       st.giveItems(LESSER_HEALING_POTIONS,100)
       for item in ECHO :
         st.giveItems(item,10)
       newbie = player.getNewbie()
       if newbie | NEWBIE_REWARD != newbie :
         player.setNewbie(newbie|NEWBIE_REWARD)
         st.giveItems(SOULSHOT_FOR_BEGINNERS,7000)
         st.playTutorialVoice("tutorial_voice_026")
       st.giveItems(WARRIORS_SWORD,1)
       st.addExpAndSp(20739,1777)
       player.sendPacket(SocialAction(player.getObjectId(),3))
       player.sendPacket(SocialAction(player.getObjectId(),15))
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(False)
     return htmltext

 def onTalk (self,npc,player):
     npcId = npc.getNpcId()
     htmltext = Quest.getNoQuestMsg(player)
     st = player.getQuestState(qn)
     if not st : return htmltext
     id = st.getState()
     cond = st.getInt("cond")
     if id == State.COMPLETED :
       htmltext = Quest.getAlreadyCompletedMsg(player)
     elif id == State.CREATED and npcId == KEKROPUS :
       if st.getPlayer().getLevel() >= 10 and player.getRace().ordinal() == 5 :
         htmltext = "32138-01.htm"
       else :
         htmltext = "32138-02.htm"
         st.exitQuest(1)
     elif id == State.STARTED :
       if npcId == KEKROPUS : 
         if cond == 1 :
           htmltext = "32138-05.htm"
         elif cond == 4 :
           htmltext = "32138-06.htm"
           st.set("cond","5")
           st.playSound("ItemSound.quest_middle")
         elif cond == 5 :
           htmltext = "32138-07.htm"
         elif cond == 6 :
           htmltext = "32138-08.htm"
         elif cond == 7 :
           htmltext = "32138-10.htm"
         elif cond == 8 :
           htmltext = "32138-11.htm"
       elif npcId == PERWAN :
         if cond == 1 :
           htmltext = "32133-01.htm"
           st.set("cond","2")
           st.playSound("ItemSound.quest_middle")
         elif cond == 2 :
           htmltext = "32133-02.htm"
         elif cond == 3 :
           st.takeItems(WOLF_TAIL,-1)
           htmltext = "32133-03.htm"
           st.set("cond","4")
           st.playSound("ItemSound.quest_middle")
         elif cond == 4 :
           htmltext = "32133-04.htm" 
         elif cond == 5 :
           htmltext = "32133-05.htm" 
         elif cond == 6 :
           htmltext = "32133-07.htm"          
     return htmltext

 def onKill(self,npc,player,isPet) :
     st = player.getQuestState(qn)
     if not st: return
     if st.getState() == State.STARTED :
       npcId = npc.getNpcId()
       cond = st.getInt("cond")
       chance = getRandom(100)
       tails = st.getQuestItemsCount(WOLF_TAIL)
       claws = st.getQuestItemsCount(MUERTOS_CLAW)
       if npcId == MOUNTAIN_WEREWOLF and (chance < 50) and (cond == 2) and (tails < 5) : #Retail statistic info. 10 mob's - 5 tails
         st.giveItems(WOLF_TAIL,1)
         st.playSound("ItemSound.quest_itemget")
         if st.getQuestItemsCount(WOLF_TAIL) == 5 :
           st.set("cond","3")
           st.playSound("ItemSound.quest_middle")
       elif (npcId in MUERTOS) and (claws < 10) and (cond == 7): #Retail statistic info. 10 mob's - 10 claws
         st.giveItems(MUERTOS_CLAW,1)
         st.playSound("ItemSound.quest_itemget") 
         if st.getQuestItemsCount(MUERTOS_CLAW) == 10 :
           st.set("cond","8")
           st.playSound("ItemSound.quest_middle")
     return

QUEST       = Quest(175, qn, "The Way of the Warrior")

QUEST.addStartNpc(KEKROPUS)

QUEST.addTalkId(KEKROPUS)
QUEST.addTalkId(PERWAN)

QUEST.addKillId(MOUNTAIN_WEREWOLF)
for mob in MUERTOS :
    QUEST.addKillId(mob)
