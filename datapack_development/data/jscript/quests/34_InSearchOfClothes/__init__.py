# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SPINNERET = 7528
SUEDE = 1866
THREAD = 1868
SPIDERSILK = 1493
MYSTERIOUS_CLOTH = 7076

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7088-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "7294-1.htm" :
     st.set("cond","2")
   if event == "7088-3.htm" :
     st.set("cond","3")
   if event == "7165-1.htm" :
     st.set("cond","4")
   if event == "7165-3.htm" :
     if st.getQuestItemsCount(SPINNERET) == 10 :
       st.takeItems(SPINNERET,10)
       st.giveItems(SPIDERSILK,1)
       st.set("cond","6")
     else :
       htmltext = "You don't have enough materials"
   if event == "7088-5.htm" :
     if st.getQuestItemsCount(SUEDE) >= 3000 and st.getQuestItemsCount(THREAD) >= 5000 and st.getQuestItemsCount(SPIDERSILK) == 1 :
       st.takeItems(SUEDE,3000)
       st.takeItems(THREAD,5000)
       st.takeItems(SPIDERSILK,1)
       st.giveItems(MYSTERIOUS_CLOTH,1)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
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
   if npcId == 7088 and cond == 0 and st.getQuestItemsCount(MYSTERIOUS_CLOTH) == 0 :
     fwear=st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
     if fwear :
       if fwear.get("cond") == "6" :
         htmltext = "7088-0.htm"
       else :
         st.exitQuest(1)
     else :
       st.exitQuest(1)
   elif npcId == 7294 and cond == 1 :
     htmltext = "7294-0.htm"
   elif npcId == 7088 and cond == 2 :
     htmltext = "7088-2.htm"
   elif npcId == 7165 and cond == 3 :
     htmltext = "7165-0.htm"
   elif npcId == 7165 and cond == 5 :
     htmltext = "7165-2.htm"
   elif npcId == 7088 and cond == 6 :
      htmltext = "7088-4.htm"
   else : htmltext = "<html><head><body>I have nothing to say you</body></html>"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(SPINNERET)
   if count < 10 :
     st.giveItems(SPINNERET,1)
     if count == 9 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","5")
     else :
       st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(34,"34_InSearchOfClothes","In Search of Clothes")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7088)
CREATED.addTalkId(7088)
STARTED.addTalkId(7088)
STARTED.addTalkId(7165)
STARTED.addTalkId(7294)
STARTED.addKillId(560)
STARTED.addQuestDrop(560,SPINNERET,1)

print "importing quests: 34: In Search of Clothes"
