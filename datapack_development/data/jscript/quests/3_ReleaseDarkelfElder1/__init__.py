# Made by Mr - Version 0.3 by DrLecter
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
TALLOTH = 30141

#ITEMS
ONYX_BEAST_EYE,TAINT_STONE,SUCCUBUS_BLOOD = range(1081,1084)

#MOBS
OMEN_BEAST            = 20031
TAINTED_ZOMBIE        = 20041
STINK_ZOMBIE          = 20046
LESSER_SUCCUBUS       = 20048
LESSER_SUCCUBUS_TUREN = 20052
LESSER_SUCCUBUS_TILFO = 20057

#REWARDS
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "30141-03.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if id == COMPLETED :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif cond == 0 :
      if st.getPlayer().getRace().ordinal() != 2 :
         htmltext = "30141-00.htm"
         st.exitQuest(1)
      elif st.getPlayer().getLevel() >= 16 :
         htmltext = "30141-02.htm"
      else :
         htmltext = "30141-01.htm"
         st.exitQuest(1)
   elif cond == 1 :
     htmltext = "30141-04.htm"
   elif cond == 2 :
     htmltext = "30141-06.htm"
     st.takeItems(ONYX_BEAST_EYE,-1)
     st.takeItems(TAINT_STONE,-1)
     st.takeItems(SUCCUBUS_BLOOD,-1)
     st.giveItems(956,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if st.getInt("cond") == 1 :
     if npcId == OMEN_BEAST and not st.getQuestItemsCount(ONYX_BEAST_EYE) :
       st.giveItems(ONYX_BEAST_EYE,1)
     elif npcId in [TAINTED_ZOMBIE,STINK_ZOMBIE] and not st.getQuestItemsCount(TAINT_STONE) :
       st.giveItems(TAINT_STONE,1)
     elif npcId in [LESSER_SUCCUBUS,LESSER_SUCCUBUS_TUREN,LESSER_SUCCUBUS_TILFO] and not st.getQuestItemsCount(SUCCUBUS_BLOOD) :
       st.giveItems(SUCCUBUS_BLOOD,1)
     if st.getQuestItemsCount(ONYX_BEAST_EYE) and st.getQuestItemsCount(TAINT_STONE) and st.getQuestItemsCount(SUCCUBUS_BLOOD) :
       st.set("cond","2")
       st.playSound("ItemSound.quest_middle")
     else :
       st.playSound("ItemSound.quest_itemget")
   return

QUEST     = Quest(3,"3_ReleaseDarkelfElder1","Will the Seal be Broken?")
CREATED   = State('Start',     QUEST)
STARTING  = State('Starting',  QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(TALLOTH)

CREATED.addTalkId(TALLOTH)
STARTING.addTalkId(TALLOTH)
STARTED.addTalkId(TALLOTH)
COMPLETED.addTalkId(TALLOTH)

STARTED.addKillId(OMEN_BEAST)
STARTED.addKillId(TAINTED_ZOMBIE)
STARTED.addKillId(STINK_ZOMBIE)
STARTED.addKillId(LESSER_SUCCUBUS)
STARTED.addKillId(LESSER_SUCCUBUS_TUREN)
STARTED.addKillId(LESSER_SUCCUBUS_TILFO)

STARTED.addQuestDrop(TALLOTH,ONYX_BEAST_EYE,1)
STARTED.addQuestDrop(TALLOTH,TAINT_STONE,1)
STARTED.addQuestDrop(TALLOTH,SUCCUBUS_BLOOD,1)

print "importing quests: 3: Will the Seal be Broken?"
