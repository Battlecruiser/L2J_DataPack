# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
ELLIE  = 7091
FELTON = 7879

#MOB
ALLIGATOR = 135

#ITEMS
ROUGH_JEWEL   = 7162
ORIHARUKON    = 1893
SILVER_NUGGET = 1873
THONS         = 4044

#REWARD
JEWEL_BOX = 7077

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7091-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7879-1.htm" :
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7091-3.htm" :
     st.takeItems(ROUGH_JEWEL,10)
     st.set("cond","4")
     st.set("id","4")
     st.playSound("ItemSound.quest_middle")
   elif event == "7091-5.htm" :
     st.takeItems(ORIHARUKON,5)
     st.takeItems(SILVER_NUGGET,500)
     st.takeItems(THONS,150)
     st.giveItems(JEWEL_BOX,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED :
     if st.getPlayer().getLevel() >= 60 :
       if st.getQuestItemsCount(JEWEL_BOX) == 0 :
         fwear = st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
         if not fwear is None :
           if fwear.get("cond") == "7" :
             htmltext = "7091-0.htm"
             return htmltext
       st.exitQuest(1)
     else :
       htmltext = "7091-6.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == FELTON and cond == 1 :
     htmltext = "7879-0.htm"
   elif npcId == ELLIE and st.getQuestItemsCount(ROUGH_JEWEL) == 10 :
     htmltext = "7091-2.htm"
   elif npcId == ELLIE and cond == 4 and st.getQuestItemsCount(ORIHARUKON) >= 5 and st.getQuestItemsCount(SILVER_NUGGET) >= 500 and st.getQuestItemsCount(THONS) >= 150 :
     htmltext = "7091-4.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(ROUGH_JEWEL)
   if count < 10 :
     st.giveItems(ROUGH_JEWEL,1)
     if st.getQuestItemsCount(ROUGH_JEWEL) == 10 :
       st.set("cond","3")
       st.set("id","3")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

qnum  = 35
qdef  = str(qnum) + "_FindGlitteringJewelry"
qname = "Find Glittering Jewelry"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ELLIE)

CREATED.addTalkId(ELLIE)

STARTED.addTalkId(ELLIE)
STARTED.addTalkId(FELTON)

COMPLETED.addTalkId(ELLIE)

STARTED.addKillId(ALLIGATOR)

STARTED.addQuestDrop(ELLIE,ROUGH_JEWEL,1)

print "importing quests: " + str(qnum) + ": " + qname