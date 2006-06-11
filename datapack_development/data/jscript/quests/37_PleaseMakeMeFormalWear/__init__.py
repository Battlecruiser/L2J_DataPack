# Made by disKret
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
ALEXIS = 7842
LEIKAR = 8520
JEREMY = 8521
MIST   = 8627

#ITEMS
MYSTERIOUS_CLOTH = 7076
JEWEL_BOX        = 7077
SEWING_KIT       = 7078
DRESS_SHOES_BOX  = 7113
BOX_OF_COOKIES   = 7159
ICE_WINE         = 7160
SIGNET_RING      = 7164

#REWARD
FORMAL_WEAR = 6408

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7842-1.htm" :
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "8520-1.htm" :
     st.giveItems(SIGNET_RING,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "8521-1.htm" :
     st.giveItems(ICE_WINE,1)
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif event == "8627-1.htm" :
     st.takeItems(ICE_WINE,1)
     st.set("cond","4")
     st.set("id","4")
     st.playSound("ItemSound.quest_middle")
   elif event == "8521-3.htm" :
     st.giveItems(BOX_OF_COOKIES,1)
     st.set("cond","5")
     st.set("id","5")
     st.playSound("ItemSound.quest_middle")
   elif event == "8520-3.htm" :
     st.set("cond","6")
     st.set("id","6")
     st.playSound("ItemSound.quest_middle")
   elif event == "8520-5.htm" :
     if st.getQuestItemsCount(MYSTERIOUS_CLOTH) and st.getQuestItemsCount(JEWEL_BOX) and st.getQuestItemsCount(SEWING_KIT) :
       st.takeItems(MYSTERIOUS_CLOTH,1)
       st.takeItems(JEWEL_BOX,1)
       st.takeItems(SEWING_KIT,1)
       st.set("cond","7")
       st.set("id","7")
       st.playSound("ItemSound.quest_middle")
     else :
       htmltext = "You don't have enough materials"
   elif event == "8520-7.htm" :
     if st.getQuestItemsCount(DRESS_SHOES_BOX) :
       st.takeItems(DRESS_SHOES_BOX,1)
       st.giveItems(FORMAL_WEAR,1)
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
   cond  = int(st.get("cond"))

   if id == CREATED :
     if st.getPlayer().getLevel() >= 60 :
       htmltext = "7842-0.htm"
     else:
       htmltext = "7842-2.htm"
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "This quest has already been completed." + htmlfoot
   elif npcId == LEIKAR and cond == 1 :
     htmltext = "8520-0.htm"
   elif npcId == JEREMY and st.getQuestItemsCount(SIGNET_RING) :
     st.takeItems(SIGNET_RING,1)
     htmltext = "8521-0.htm"
   elif npcId == MIST and st.getQuestItemsCount(ICE_WINE) :
     htmltext = "8627-0.htm"
   elif npcId == JEREMY and cond == 4 :
     htmltext = "8521-2.htm"
   elif npcId == LEIKAR and st.getQuestItemsCount(BOX_OF_COOKIES) :
     st.takeItems(BOX_OF_COOKIES,1)
     htmltext = "8520-2.htm"
   elif npcId == LEIKAR and st.getQuestItemsCount(MYSTERIOUS_CLOTH) and st.getQuestItemsCount(JEWEL_BOX) and st.getQuestItemsCount(SEWING_KIT) :
     htmltext = "8520-4.htm"
   elif npcId == LEIKAR and st.getQuestItemsCount(DRESS_SHOES_BOX) :
     htmltext = "8520-6.htm"
   return htmltext

qnum  = 37
qdef  = str(qnum) + "_PleaseMakeMeFormalWear"
qname = "Make Formal Wear"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(ALEXIS)

CREATED.addTalkId(ALEXIS)

STARTED.addTalkId(LEIKAR)
STARTED.addTalkId(JEREMY)
STARTED.addTalkId(MIST)

COMPLETED.addTalkId(ALEXIS)

print "importing quests: " + str(qnum) + ": " + qname