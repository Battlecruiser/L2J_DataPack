# Maked by Mr. - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

BLACK_SOULSTONE = 1475
RED_SOULSTONE = 1476
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event in ["7566-03.htm","7566-08.htm"] : # -i'll continue- event kept here for backwards compatibility only.. should be removed some day
      st.set("cond","1")
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "7566-07.htm" :
      st.exitQuest(1)
      st.playSound("ItemSound.quest_finish")
    return htmltext

 def onTalk (self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id in [CREATED,COMPLETED] :
     st.set("cond","0")
   if int(st.get("cond"))==0 :
     if st.getPlayer().getRace().ordinal() != 3 :
        htmltext = "7566-00.htm"
        st.exitQuest(1)
     elif st.getPlayer().getLevel() < 6 :
        htmltext = "7566-01.htm"
        st.exitQuest(1)
     else:
        htmltext = "7566-02.htm"
   else :
     red=st.getQuestItemsCount(RED_SOULSTONE)
     black=st.getQuestItemsCount(BLACK_SOULSTONE)
     if red+black == 0 :
        htmltext = "7566-04.htm"
     elif red == 0 :
        htmltext = "7566-05.htm"
        st.giveItems(ADENA,black*3)
        st.takeItems(BLACK_SOULSTONE,black)
        st.playSound("ItemSound.quest_finish")
     else:
        htmltext = "7566-06.htm"
        if black :
           st.giveItems(ADENA,black*3)
           st.takeItems(BLACK_SOULSTONE,black)
        st.giveItems(ADENA,red*5)
        st.takeItems(RED_SOULSTONE,red)
        st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 311 : chance = 90
   if npcId == 312 : chance = 87
   if npcId == 313 : chance = 77
   if st.getRandom(100) <= chance :
      st.giveItems(BLACK_SOULSTONE,1)
   else:
      st.giveItems(RED_SOULSTONE,1)
   st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(273,"273_InvadersOfHolyland","Invaders Of Holyland")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7566)

CREATED.addTalkId(7566)
STARTING.addTalkId(7566)
STARTED.addTalkId(7566)
COMPLETED.addTalkId(7566)

STARTED.addKillId(311)
STARTED.addKillId(312)
STARTED.addKillId(313)

STARTED.addQuestDrop(311,BLACK_SOULSTONE,1)
STARTED.addQuestDrop(313,RED_SOULSTONE,1)

print "importing quests: 273: Invaders Of Holyland"
