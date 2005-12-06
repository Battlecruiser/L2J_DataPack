# Made by Mr. Have fun! - Version 0.3 by Drlecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MONSTER_EYE_CARCASS_ID = 1347
MONSTER_EYE_LENS_ID = 1366
BASILISK_GIZZARD_ID = 1348
ADENA_ID = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7436-03.htm"
    elif event == "7436_1" :
            htmltext = "7436-06.htm"
            st.playSound("ItemSound.quest_finish")
            st.exitQuest(1)
    elif event == "7436_2" :
            htmltext = "7436-07.htm"
    return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7436 and int(st.get("cond"))==0 :
     if st.getPlayer().getLevel() >= 21 :
        htmltext = "7436-02.htm"
        return htmltext
     else:
        htmltext = "7436-01.htm"
        st.exitQuest(1)
   elif npcId == 7436 and int(st.get("cond"))==1 :
     if st.getQuestItemsCount(MONSTER_EYE_CARCASS_ID)+st.getQuestItemsCount(MONSTER_EYE_LENS_ID)+st.getQuestItemsCount(BASILISK_GIZZARD_ID) > 0 :
        st.giveItems(ADENA_ID,30*st.getQuestItemsCount(MONSTER_EYE_CARCASS_ID)+2000*st.getQuestItemsCount(MONSTER_EYE_LENS_ID)+75*st.getQuestItemsCount(BASILISK_GIZZARD_ID))
        st.takeItems(MONSTER_EYE_CARCASS_ID,-1)
        st.takeItems(MONSTER_EYE_LENS_ID,-1)
        st.takeItems(BASILISK_GIZZARD_ID,-1)
        htmltext = "7436-05.htm"
     else:
        htmltext = "7436-04.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   n = st.getRandom(100)
   if npcId == 55 :
      if n<50 :
         st.giveItems(MONSTER_EYE_CARCASS_ID,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<51 :
         st.giveItems(MONSTER_EYE_LENS_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 59 :
      if n<54 :
         st.giveItems(MONSTER_EYE_CARCASS_ID,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<55 :
         st.giveItems(MONSTER_EYE_LENS_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 67 :
      if n<67 :
         st.giveItems(MONSTER_EYE_CARCASS_ID,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<69 :
         st.giveItems(MONSTER_EYE_LENS_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 68 :
      if n<72 :
         st.giveItems(MONSTER_EYE_CARCASS_ID,1)
         st.playSound("ItemSound.quest_itemget")
      elif n<74 :
         st.giveItems(MONSTER_EYE_LENS_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 70 :
      if n<50 :
         st.giveItems(BASILISK_GIZZARD_ID,1)
         st.playSound("ItemSound.quest_itemget")
   elif npcId == 72 :
      if n<53 :
         st.giveItems(BASILISK_GIZZARD_ID,1)
         st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(328,"328_SenseForBusiness","Sense For Business")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(7436)
CREATED.addTalkId(7436)
STARTED.addTalkId(7436)

for i in [ 0,4,12,13,15,17 ] :
    STARTED.addKillId(55+i)

STARTED.addQuestDrop(55,MONSTER_EYE_CARCASS_ID,1)
STARTED.addQuestDrop(55,MONSTER_EYE_LENS_ID,1)
STARTED.addQuestDrop(70,BASILISK_GIZZARD_ID,1)

print "importing quests: 328: Sense For Business"
