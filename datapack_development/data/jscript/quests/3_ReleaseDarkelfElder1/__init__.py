# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

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

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED :
     if st.getPlayer().getRace().ordinal() != 2 :
       htmltext = "7141-00.htm"
       st.exitQuest(1)
     elif st.getPlayer().getLevel() >= 16 :
       htmltext = "7141-02.htm"
     else:
       htmltext = "7141-01.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == TALLOTH and cond == 1 :
     htmltext = "7141-04.htm"
   elif npcId == TALLOTH and cond == 2 :
     htmltext = "7141-06.htm"
     st.takeItems(ONYX_BEAST_EYE,-1)
     st.takeItems(TAINT_STONE,-1)
     st.takeItems(SUCCUBUS_BLOOD,-1)
     st.giveItems(ADENA,4900)
     st.addExpAndSp(5000,0)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   cond  = st.getInt("cond")
   count_OBE = st.getQuestItemsCount(ONYX_BEAST_EYE)
   count_TS  = st.getQuestItemsCount(TAINT_STONE)
   count_SB  = st.getQuestItemsCount(SUCCUBUS_BLOOD)

   if cond == 1 :
     if npcId == OMEN_BEAST and count_OBE == 0 :
       st.giveItems(ONYX_BEAST_EYE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == TAINTED_ZOMBIE and count_TS == 0 :
       st.giveItems(TAINT_STONE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == STINK_ZOMBIE and count_TS == 0 :
       st.giveItems(TAINT_STONE,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS and count_SB == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS_TUREN and count_SB == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif npcId == LESSER_SUCCUBUS_TILFO and count_SB == 0 :
       st.giveItems(SUCCUBUS_BLOOD,1)
       st.playSound("ItemSound.quest_itemget")
     elif count_OBE >= 1 and count_TS >= 1 and count_SB >= 1 :
       st.set("cond","2")
       st.set("id","2")
       st.playSound("ItemSound.quest_middle")
   return

qnum  = 3
qdef  = str(qnum) + "_ReleaseDarkelfElder1"
qname = "Will the Seal be Broken?"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(TALLOTH)

CREATED.addTalkId(TALLOTH)

STARTED.addTalkId(TALLOTH)

COMPLETED.addTalkId(TALLOTH)

STARTED.addKillId(OMEN_BEAST)
STARTED.addKillId(TAINTED_ZOMBIE)
STARTED.addKillId(STINK_ZOMBIE)
STARTED.addKillId(LESSER_SUCCUBUS)
STARTED.addKillId(LESSER_SUCCUBUS_TUREN)
STARTED.addKillId(LESSER_SUCCUBUS_TILFO)

for i in range(1081,1083) :
   STARTED.addQuestDrop(TALLOTH,i,1)

print "importing quests: " + str(qnum) + ": " + qname