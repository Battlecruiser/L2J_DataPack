# Created by CubicVirtuoso
# Any problems feel free to drop by #l2j-datapack on irc.freenode.net
import sys
from net.sf.l2j.gameserver.model.quest        import State
from net.sf.l2j.gameserver.model.quest        import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

htmlhead = "<html><head><body>"
htmlfoot = "</body></html>"

#NPCs
GALLADUCCI = 7097
GENTLER    = 7094
SANDRA     = 7090
DUSTIN     = 7116

#ITEMS
GALLADUCCIS_ORDER_DOCUMENT_1 = 7563
GALLADUCCIS_ORDER_DOCUMENT_2 = 7564
GALLADUCCIS_ORDER_DOCUMENT_3 = 7565
MAGIC_SWORD_HILT             = 7568
GEMSTONE_POWDER              = 7567
PURIFIED_MAGIC_NECKLACE      = 7566
MARK_OF_TRAVELER             = 7570

#REWARD
SCROLL_OF_ESCAPE_SPECIAL = 7557

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
   htmltext = event
   if event == "7097-03.htm" :
     st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_1,1)
     st.set("cond","1")
     st.set("id","1")
     st.setState(STARTED)
     st.playSound("ItemSound.quest_accept")
   elif event == "7094-02.htm" :
     st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_1,1)
     st.giveItems(MAGIC_SWORD_HILT,1)
     st.set("cond","2")
     st.set("id","2")
     st.playSound("ItemSound.quest_middle")
   elif event == "7097-06.htm" :
     st.takeItems(MAGIC_SWORD_HILT,1)
     st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_2,1)
     st.set("cond","3")
     st.set("id","3")
     st.playSound("ItemSound.quest_middle")
   elif event == "7090-02.htm" :
     st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_2,1)
     st.giveItems(GEMSTONE_POWDER,1)
     st.set("cond","4")
     st.set("id","4")
     st.playSound("ItemSound.quest_middle")
   elif event == "7097-09.htm" :
     st.takeItems(GEMSTONE_POWDER,1)
     st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_3,1)
     st.set("cond","5")
     st.set("id","5")
     st.playSound("ItemSound.quest_middle")
   elif event == "7116-02.htm" :
     st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_3,1)
     st.giveItems(PURIFIED_MAGIC_NECKLACE,1)
     st.set("cond","6")
     st.set("id","6")
     st.playSound("ItemSound.quest_middle")
   elif event == "7097-12.htm" :
     st.takeItems(PURIFIED_MAGIC_NECKLACE,1)
     st.giveItems(SCROLL_OF_ESCAPE_SPECIAL,1)
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
     if st.getPlayer().getLevel() >= 3 :
       if st.getPlayer().getRace().ordinal() == 3 and st.getQuestItemsCount(MARK_OF_TRAVELER) > 0:
         htmltext = "7097-02.htm"
       else :
         htmltext = "7097-01.htm"
         st.exitQuest(1)
     else :
       htmltext = htmlhead + "Quest for characters level 3 and above." + htmlfoot
       st.exitQuest(1)
   elif id == COMPLETED :
     htmltext = htmlhead + "I can't supply you with another Scroll of Escape. Sorry traveller" + htmlfoot
   elif npcId == GALLADUCCI and cond == 1 :
     htmltext = "7097-04.htm"
   elif npcId == GALLADUCCI and cond == 2 :
     htmltext = "7097-05.htm"
   elif npcId == GALLADUCCI and cond == 3 :
     htmltext = "7097-07.htm"
   elif npcId == GALLADUCCI and cond == 4 :
     htmltext = "7097-08.htm"
   elif npcId == GALLADUCCI and cond == 5 :
     htmltext = "7097-10.htm"
   elif npcId == GALLADUCCI and cond == 6 :
     htmltext = "7097-11.htm"
   elif npcId == GENTLER and cond == 1 :
     htmltext = "7094-01.htm"
   elif npcId == GENTLER and cond == 2 :
     htmltext = "7094-03.htm"
   elif npcId == SANDRA and cond == 3 :
     htmltext = "7090-01.htm"
   elif npcId == SANDRA and cond == 4 :
     htmltext = "7090-03.htm"
   elif npcId == DUSTIN and cond == 5 :
     htmltext = "7116-01.htm"
   elif npcId == DUSTIN and cond == 6 :
     htmltext = "7116-03.htm"
   return htmltext

qnum  = 48
qdef  = str(qnum) + "_ToTheImmortalPlateau"
qname = "To the Immortal Plateau"

QUEST     = Quest(qnum,qdef,qname)
CREATED   = State('Start',     QUEST)
STARTED   = State('Started',   QUEST)
COMPLETED = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(GALLADUCCI)

CREATED.addTalkId(GALLADUCCI)

STARTED.addTalkId(GALLADUCCI)
STARTED.addTalkId(GENTLER)
STARTED.addTalkId(SANDRA)
STARTED.addTalkId(DUSTIN)

COMPLETED.addTalkId(GALLADUCCI)

STARTED.addQuestDrop(GALLADUCCI,GALLADUCCIS_ORDER_DOCUMENT_1,1)
STARTED.addQuestDrop(GALLADUCCI,GALLADUCCIS_ORDER_DOCUMENT_2,1)
STARTED.addQuestDrop(GALLADUCCI,GALLADUCCIS_ORDER_DOCUMENT_3,1)
STARTED.addQuestDrop(GALLADUCCI,MAGIC_SWORD_HILT,1)
STARTED.addQuestDrop(GALLADUCCI,GEMSTONE_POWDER,1)
STARTED.addQuestDrop(GALLADUCCI,PURIFIED_MAGIC_NECKLACE,1)

print "importing quests: " + str(qnum) + ": " + qname