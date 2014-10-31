# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MYSTERIOUS_CLOTH = 7076
JEWEL_BOX = 7077
SEWING_KIT = 7078
DRESS_SHOES_BOX = 7113
FORMAL_WEAR = 6408
SIGNET_RING = 7164
ICE_WINE = 7160
BOX_OF_COOKIES = 7159

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7842-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "8520-1.htm" :
     st.giveItems(SIGNET_RING,1)
     st.set("cond","2")
   if event == "8521-1.htm" :
     st.giveItems(ICE_WINE,1)
     st.set("cond","3")
   if event == "8627-1.htm" :
     if st.getQuestItemsCount(ICE_WINE):
       st.takeItems(ICE_WINE,1)
       st.set("cond","4")
     else:
       htmltext = "You don't have enough materials"
   if event == "8521-3.htm" :
     st.giveItems(BOX_OF_COOKIES,1)
     st.set("cond","5")
   if event == "8520-3.htm" :
     st.set("cond","6")
   if event == "8520-5.htm" :
     if st.getQuestItemsCount(MYSTERIOUS_CLOTH) and st.getQuestItemsCount(JEWEL_BOX) and st.getQuestItemsCount(SEWING_KIT) :
       st.takeItems(MYSTERIOUS_CLOTH,1)
       st.takeItems(JEWEL_BOX,1)
       st.takeItems(SEWING_KIT,1)
       st.set("cond","7")
     else :
       htmltext = "You don't have enough materials"
   if event == "8520-7.htm" :
     if st.getQuestItemsCount(DRESS_SHOES_BOX) :
       st.takeItems(DRESS_SHOES_BOX,1)
       st.giveItems(FORMAL_WEAR,1)
       st.setState(COMPLETED)
       st.set("cond","0")
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have enough materials"
   return htmltext

 def onTalk (Self,npc,st):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   cond = int(st.get("cond"))
   if npcId == 7842 and cond == 0 :
     if id == COMPLETED :
       htmltext = "<html><head><body>This quest has already been completed.</body></html>"
     elif st.getPlayer().getLevel() >= 60 :
       htmltext = "7842-0.htm"
       return htmltext
     else:
       htmltext = "7842-2.htm"
       st.exitQuest(1)
   elif npcId == 8520 and cond == 1 :
     htmltext = "8520-0.htm"
   elif npcId == 8521 and st.getQuestItemsCount(SIGNET_RING) :
     st.takeItems(SIGNET_RING,1)
     htmltext = "8521-0.htm"
   elif npcId == 8627 and st.getQuestItemsCount(ICE_WINE) :
     htmltext = "8627-0.htm"
   elif npcId == 8521 and cond == 4 :
     htmltext = "8521-2.htm"
   elif npcId == 8520 and st.getQuestItemsCount(BOX_OF_COOKIES) :
     st.takeItems(BOX_OF_COOKIES,1)
     htmltext = "8520-2.htm"
   elif npcId == 8520 and st.getQuestItemsCount(MYSTERIOUS_CLOTH) and st.getQuestItemsCount(JEWEL_BOX) and st.getQuestItemsCount(SEWING_KIT) :
     htmltext = "8520-4.htm"
   elif npcId == 8520 and st.getQuestItemsCount(DRESS_SHOES_BOX) :
     htmltext = "8520-6.htm"
   return htmltext

QUEST       = Quest(37,"37_PleaseMakeMeFormalWear","Please Make Me Formal Wear")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7842)
CREATED.addTalkId(7842)
STARTED.addTalkId(8520)
STARTED.addTalkId(8521)
STARTED.addTalkId(8627)

print "importing quests: 37: Please Make Me Formal Wear"
