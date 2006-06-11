# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPC
FERRIS = 7847

#MOB
ENCHANTED_IRON_GOLEM = 566

#ITEMS
REINFORCED_STEEL = 7163
ARTISANS_FRAME   = 1891
ORIHARUKON       = 1893

#REWARDS
SEWING_KIT = 7078

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7847-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7847-3.htm" :
     st.takeItems(REINFORCED_STEEL,5)
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   return htmltext

 def onTalk (self,npc,st) :
   htmltext = htmlhead + "I have nothing to say you" + htmlfoot
   id   = st.getState()
   cond = int(st.get("cond"))

   if id == CREATED :
     if st.getPlayer().getLevel() >= 60 :
       if st.getQuestItemsCount(SEWING_KIT) == 0 :
         fwear = st.getPlayer().getQuestState("37_PleaseMakeMeFormalWear")
         if not fwear is None:
           if fwear.get("cond") == "6" :
             htmltext = "7847-0.htm"
             return htmltext
       st.exitQuest(1)
     else :
       htmltext = "7847-5.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif st.getQuestItemsCount(REINFORCED_STEEL) == 5 :
     htmltext = "7847-2.htm"
   elif cond == 3 and st.getQuestItemsCount(ORIHARUKON) >= 10 and st.getQuestItemsCount(ARTISANS_FRAME) >= 10 :
     htmltext = "7847-4.htm"
     st.takeItems(ORIHARUKON,10)
     st.takeItems(ARTISANS_FRAME,10)
     st.giveItems(SEWING_KIT,1)
     st.unset("cond")
     st.setState(COMPLETED)
     st.playSound("ItemSound.quest_finish")
   return htmltext

 def onKill (self,npc,st):
   count = st.getQuestItemsCount(REINFORCED_STEEL)
   if count < 5 :
     st.giveItems(REINFORCED_STEEL,1)
     if count == 4 :
       st.set("cond","2")
       st.set("id","2")
       st.playSound("ItemSound.quest_middle")
     else:
       st.playSound("ItemSound.quest_itemget")
   return

qnum  = 36
qdef  = str(qnum) + "_MakeASewingKit"
qname = "Make a Sewing Kit"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(FERRIS)

CREATED.addTalkId(FERRIS)

STARTED.addTalkId(FERRIS)

COMPLETED.addTalkId(FERRIS)

STARTED.addKillId(ENCHANTED_IRON_GOLEM)

STARTED.addQuestDrop(FERRIS,REINFORCED_STEEL,1)

print "importing quests: " + str(qnum) + ": " + qname