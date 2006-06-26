# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

ROUGH_JEWEL = 7162
ORIHARUKON = 1893
SILVER_NUGGET = 1873
THONS = 4044
JEWEL_BOX = 7077

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7091-1.htm" :
     st.set("cond","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   if event == "7879-1.htm" :
     st.set("cond","2")
   if event == "7091-3.htm" :
     st.takeItems(ROUGH_JEWEL,10)
     st.set("cond","4")
   if event == "7091-5.htm" :
     if st.getQuestItemsCount(ORIHARUKON) >= 5 and st.getQuestItemsCount(SILVER_NUGGET) >= 500 and st.getQuestItemsCount(THONS) >= 150 :
       st.takeItems(ORIHARUKON,5)
       st.takeItems(SILVER_NUGGET,500)
       st.takeItems(THONS,150)
       st.giveItems(JEWEL_BOX,1)
       st.playSound("ItemSound.quest_finish")
       st.exitQuest(1)
     else :
       htmltext = "You don't have enough materials"
   return htmltext

 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
   if npcId == 7091 and int(st.get("cond")) == 0 and st.getQuestItemsCount(JEWEL_BOX) == 0 :
     fwear=st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
     if not fwear is None :
       if fwear.get("cond") == "6" :
         htmltext = "7091-0.htm"
         return htmltext
       else:
         htmltext = "<html><head><body>I have nothing to say you</body></html>"
         st.exitQuest(1)
     else:
       htmltext = "<html><head><body>I have nothing to say you</body></html>"
       st.exitQuest(1)
   elif npcId == 7879 and int(st.get("cond")) == 1 :
     htmltext = "7879-0.htm"
   elif npcId == 7091 and st.getQuestItemsCount(ROUGH_JEWEL) == 10 :
     htmltext = "7091-2.htm"
   elif npcId == 7091 and int(st.get("cond")) == 4 and st.getQuestItemsCount(ORIHARUKON) >= 5 and st.getQuestItemsCount(SILVER_NUGGET) >= 500 and st.getQuestItemsCount(THONS) >= 150 :
     htmltext = "7091-4.htm"
   else : htmltext = "<html><head><body>I have nothing to say you</body></html>"
   return htmltext


 def onKill (self,npc,st):
   count = st.getQuestItemsCount(ROUGH_JEWEL)
   if count<10 :
     st.giveItems(ROUGH_JEWEL,1)
     if st.getQuestItemsCount(ROUGH_JEWEL) == 10 :
       st.playSound("ItemSound.quest_middle")
       st.set("cond","3")
     else:
       st.playSound("ItemSound.quest_itemget")	
   return

QUEST       = Quest(35,"35_FindGlitteringJewelry","Find Glittering Jewelry")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7091)
CREATED.addTalkId(7091)
STARTED.addTalkId(7091)
STARTED.addTalkId(7879)
STARTED.addKillId(135)
STARTED.addQuestDrop(135,ROUGH_JEWEL,1)

print "importing quests: 35: Find Glittering Jewelry"
