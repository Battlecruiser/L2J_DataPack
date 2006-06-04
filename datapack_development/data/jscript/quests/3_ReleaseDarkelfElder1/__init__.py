# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

#NPC
TALLOTH = 7141

#ITEMS
ONYX_BEAST_EYE = 1081
TAINT_STONE    = 1082
SUCCUBUS_BLOOD = 1083

#MOBS
OMEN_BEAST            = 31
TAINTED_ZOMBIE        = 41
STINK_ZOMBIE          = 46
LESSER_SUCCUBUS       = 48
LESSER_SUCCUBUS_TUREN = 52
LESSER_SUCCUBUS_TILFO = 57

#REWARDS
ADENA = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7141-03.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()

   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")

   cond = st.getInt("cond")
   onlyone = st.getInt("onlyone")

   if npcId == TALLOTH and cond == 0 and onlyone == 0 :
     if int(st.get("cond"))<15 :
       if st.getPlayer().getRace().ordinal() != 2 :
         htmltext = "7141-00.htm"
         st.exitQuest(1)
       elif st.getPlayer().getLevel() >= 16 :
         htmltext = "7141-02.htm"
       else:
         htmltext = "7141-01.htm"
         st.exitQuest(1)
     else:
       htmltext = "7141-01.htm"
       st.exitQuest(1)
   elif npcId == TALLOTH and cond == 0 and onlyone == 1 :
     htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == TALLOTH and cond == 1 and onlyone == 0 :
     htmltext = "7141-04.htm"
   elif npcId == TALLOTH and cond == 2 and onlyone == 0 :
     htmltext = "7141-06.htm"
     st.takeItems(ONYX_BEAST_EYE,-1)
     st.takeItems(TAINT_STONE,-1)
     st.takeItems(SUCCUBUS_BLOOD,-1)
     st.giveItems(ADENA,4900)
     st.addExpAndSp(5000,0)
     st.set("cond","0")
     st.set("onlyone","1")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   cond = st.getInt("cond")

   if cond == 1 :
     if npcId == OMEN_BEAST and st.getQuestItemsCount(ONYX_BEAST_EYE) == 0 :
       st.giveItems(ONYX_BEAST_EYE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == TAINTED_ZOMBIE and st.getQuestItemsCount(TAINT_STONE) == 0 :
       st.giveItems(TAINT_STONE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == STINK_ZOMBIE and st.getQuestItemsCount(TAINT_STONE) == 0 :
       st.giveItems(TAINT_STONE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS and st.getQuestItemsCount(SUCCUBUS_BLOOD) == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS_TUREN and st.getQuestItemsCount(SUCCUBUS_BLOOD) == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS_TILFO and st.getQuestItemsCount(SUCCUBUS_BLOOD) == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif st.getQuestItemsCount(ONYX_BEAST_EYE) >= 1 and st.getQuestItemsCount(TAINT_STONE) >= 1 and st.getQuestItemsCount(SUCCUBUS_BLOOD) >= 1 :
       st.set("cond","2")
       st.set("id","2")       
       st.playSound("ItemSound.quest_middle")
   return

QUEST     = Quest(3,"3_ReleaseDarkelfElder1","Will the Seal be Broken?")
CREATED   = State('Start',     QUEST)
STARTING  = State('Starting',  QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(TALLOTH)

STARTING.addTalkId(TALLOTH)

STARTED.addTalkId(TALLOTH)

STARTED.addKillId(OMEN_BEAST)
STARTED.addKillId(TAINTED_ZOMBIE)
STARTED.addKillId(STINK_ZOMBIE)
STARTED.addKillId(LESSER_SUCCUBUS)
STARTED.addKillId(LESSER_SUCCUBUS_TUREN)
STARTED.addKillId(LESSER_SUCCUBUS_TILFO)

STARTED.addQuestDrop(TALLOTH,ONYX_BEAST_EYE,1)
STARTED.addQuestDrop(TALLOTH,TAINT_STONE,1)
STARTED.addQuestDrop(TALLOTH,TAINT_STONE,1)
STARTED.addQuestDrop(TALLOTH,SUCCUBUS_BLOOD,1)
STARTED.addQuestDrop(TALLOTH,SUCCUBUS_BLOOD,1)
STARTED.addQuestDrop(TALLOTH,SUCCUBUS_BLOOD,1)

print "importing quests: 3: Will the Seal be Broken?"
