# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
RADIA   = 7088
RALFORD = 7165
VARAN   = 7294

#MOB
TRISALIM_SPIDER = 560

#ITEMS
SPINNERET  = 7528
SUEDE      = 1866
THREAD     = 1868
SPIDERSILK = 1493

#REWARD
MYSTERIOUS_CLOTH = 7076

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7088-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7294-1.htm" :
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7088-3.htm" :
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif event == "7165-1.htm" :
     st.set("cond","4")
     st.set("id","4")
     st.playSound("ItemSound.quest_middle")
   elif event == "7165-3.htm" :
     if st.getQuestItemsCount(SPINNERET) == 10 :
       st.takeItems(SPINNERET,10)
       st.giveItems(SPIDERSILK,1)
       st.set("cond","6")
       st.set("id","6")
       st.playSound("ItemSound.quest_middle")
     else :
       htmltext = "You don't have enough materials"
   elif event == "7088-5.htm" :
     if st.getQuestItemsCount(SUEDE) >= 3000 and st.getQuestItemsCount(THREAD) >= 5000 and st.getQuestItemsCount(SPIDERSILK) == 1 :
       st.takeItems(SUEDE,3000)
       st.takeItems(THREAD,5000)
       st.takeItems(SPIDERSILK,1)
       st.giveItems(MYSTERIOUS_CLOTH,1)
       st.unset("cond")
       st.setState(COMPLETED)
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have enough materials"
   return htmltext

 def onTalk (self,npc,st):
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   npcId = npc.getNpcId()
   id    = st.getState()
   cond  = st.getInt("cond")

   if id == CREATED :
     if st.getPlayer().getLevel() >= 60 :
       if st.getQuestItemsCount(MYSTERIOUS_CLOTH) == 0 :
         fwear = st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
         if not fwear is None :
           if fwear.get("cond") == "7" :
             htmltext = "7088-0.htm"
             return htmltext
       st.exitQuest(1)
     else :
       htmltext = "7088-6.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == VARAN and cond == 1 :
     htmltext = "7294-0.htm"
   elif npcId == RADIA and cond == 2 :
     htmltext = "7088-2.htm"
   elif npcId == RALFORD and cond == 3 :
     htmltext = "7165-0.htm"
   elif npcId == RALFORD and cond == 5 :
     htmltext = "7165-2.htm"
   elif npcId == RADIA and cond == 6 :
      htmltext = "7088-4.htm"
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(SPINNERET)
   if count < 10 :
     st.giveItems(SPINNERET,1)
     if count == 9 :
       st.set("cond","5")
       st.set("id","5")
       st.playSound("ItemSound.quest_middle")
     else :
       st.playSound("ItemSound.quest_itemget")
   return

qnum  = 34
qdef  = str(qnum) + "_InSearchOfClothes"
qname = "In Search of Cloth"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(RADIA)

CREATED.addTalkId(RADIA)

STARTED.addTalkId(RADIA)
STARTED.addTalkId(RALFORD)
STARTED.addTalkId(VARAN)

COMPLETED.addTalkId(RADIA)

STARTED.addKillId(TRISALIM_SPIDER)

STARTED.addQuestDrop(RADIA,SPINNERET,1)

print "importing quests: " + str(qnum) + ": " + qname