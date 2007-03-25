# Maked by Mr. Have fun! Version 0.2
print "importing quests: 410: Path To Palus Knight"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "410_PathToPalusKnight"

PALLUS_TALISMAN_ID = 1237
LYCANTHROPE_SKULL_ID = 1238
VIRGILS_LETTER_ID = 1239
MORTE_TALISMAN_ID = 1240
PREDATOR_CARAPACE_ID = 1241
TRIMDEN_SILK_ID = 1242
COFFIN_ETERNAL_REST_ID = 1243
GAZE_OF_ABYSS_ID = 1244

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "30329-06.htm"
        st.giveItems(PALLUS_TALISMAN_ID,1)
    elif event == "410_1" :
          if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x1f and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 0 :
            htmltext = "30329-05.htm"
            return htmltext
          elif st.getPlayer().getClassId().getId() != 0x1f :
              if st.getPlayer().getClassId().getId() == 0x20 :
                htmltext = "30329-02a.htm"
              else:
                htmltext = "30329-03.htm"
          elif st.getPlayer().getLevel()<19 and st.getPlayer().getClassId().getId() == 0x1f :
              htmltext = "30329-02.htm"
          elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x1f and st.getQuestItemsCount(GAZE_OF_ABYSS_ID) == 1 :
              htmltext = "30329-04.htm"
    elif event == "30329_2" :
            htmltext = "30329-10.htm"
            st.takeItems(PALLUS_TALISMAN_ID,1)
            st.takeItems(LYCANTHROPE_SKULL_ID,st.getQuestItemsCount(LYCANTHROPE_SKULL_ID))
            st.giveItems(VIRGILS_LETTER_ID,1)
            st.set("cond","3")
    elif event == "30422_1" :
          htmltext = "30422-02.htm"
          st.takeItems(VIRGILS_LETTER_ID,1)
          st.giveItems(MORTE_TALISMAN_ID,1)
          st.set("cond","4")
    elif event == "30422_2" :
            htmltext = "30422-06.htm"
            st.takeItems(MORTE_TALISMAN_ID,1)
            st.takeItems(TRIMDEN_SILK_ID,st.getQuestItemsCount(TRIMDEN_SILK_ID))
            st.takeItems(PREDATOR_CARAPACE_ID,st.getQuestItemsCount(PREDATOR_CARAPACE_ID))
            st.giveItems(COFFIN_ETERNAL_REST_ID,1)
            st.set("cond","6")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30329 and id != STARTED : return htmltext

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30329 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          htmltext = "30329-01.htm"
        else:
          htmltext = "30329-01.htm"
   elif npcId == 30329 and int(st.get("cond")) :
        if st.getQuestItemsCount(PALLUS_TALISMAN_ID) == 1 and st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) == 0 :
          htmltext = "30329-07.htm"
        elif st.getQuestItemsCount(PALLUS_TALISMAN_ID) == 1 and st.getQuestItemsCount(LYCANTHROPE_SKULL_ID)>0 and st.getQuestItemsCount(LYCANTHROPE_SKULL_ID)<13 :
            htmltext = "30329-08.htm"
        elif st.getQuestItemsCount(PALLUS_TALISMAN_ID) == 1 and st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) >= 13 :
            htmltext = "30329-09.htm"
        elif st.getQuestItemsCount(COFFIN_ETERNAL_REST_ID) == 1 :
            htmltext = "30329-11.htm"
            st.takeItems(COFFIN_ETERNAL_REST_ID,1)
            st.giveItems(GAZE_OF_ABYSS_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
        elif st.getQuestItemsCount(MORTE_TALISMAN_ID) or st.getQuestItemsCount(VIRGILS_LETTER_ID) :
            htmltext = "30329-12.htm"
   elif npcId == 30422 and int(st.get("cond")) :
        if st.getQuestItemsCount(VIRGILS_LETTER_ID) :
          htmltext = "30422-01.htm"
        elif st.getQuestItemsCount(MORTE_TALISMAN_ID) and st.getQuestItemsCount(TRIMDEN_SILK_ID) == 0 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID) == 0 :
            htmltext = "30422-03.htm"
        elif st.getQuestItemsCount(MORTE_TALISMAN_ID) and st.getQuestItemsCount(TRIMDEN_SILK_ID)>0 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID) == 0 :
            htmltext = "30422-04.htm"
        elif st.getQuestItemsCount(MORTE_TALISMAN_ID) and st.getQuestItemsCount(TRIMDEN_SILK_ID) == 0 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID)>0 :
            htmltext = "30422-04.htm"
        elif st.getQuestItemsCount(MORTE_TALISMAN_ID) and st.getQuestItemsCount(TRIMDEN_SILK_ID) >= 5 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID)>0 :
            htmltext = "30422-05.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 
   
   npcId = npc.getNpcId()
   if npcId == 20049 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PALLUS_TALISMAN_ID) == 1 and st.getQuestItemsCount(LYCANTHROPE_SKULL_ID)<13 :
          st.giveItems(LYCANTHROPE_SKULL_ID,1)
          if st.getQuestItemsCount(LYCANTHROPE_SKULL_ID) == 13 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","2")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 20038 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(MORTE_TALISMAN_ID) == 1 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID)<1 :
          st.giveItems(PREDATOR_CARAPACE_ID,1)
          st.playSound("ItemSound.quest_middle")
          if st.getQuestItemsCount(TRIMDEN_SILK_ID) >= 5 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID)>0 :
            st.set("cond","5")
   elif npcId == 20043 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(MORTE_TALISMAN_ID) == 1 and st.getQuestItemsCount(TRIMDEN_SILK_ID)<5 :
          st.giveItems(TRIMDEN_SILK_ID,1)
          if st.getQuestItemsCount(TRIMDEN_SILK_ID) == 5 :
            st.playSound("ItemSound.quest_middle")
            if st.getQuestItemsCount(TRIMDEN_SILK_ID) >= 5 and st.getQuestItemsCount(PREDATOR_CARAPACE_ID)>0 :
              st.set("cond","5")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(410,qn,"Path To Palus Knight")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30329)

QUEST.addTalkId(30329)

QUEST.addTalkId(30422)

QUEST.addKillId(20038)
QUEST.addKillId(20043)
QUEST.addKillId(20049)

STARTED.addQuestDrop(30329,PALLUS_TALISMAN_ID,1)
STARTED.addQuestDrop(20049,LYCANTHROPE_SKULL_ID,1)
STARTED.addQuestDrop(30422,COFFIN_ETERNAL_REST_ID,1)
STARTED.addQuestDrop(30422,MORTE_TALISMAN_ID,1)
STARTED.addQuestDrop(30329,VIRGILS_LETTER_ID,1)
STARTED.addQuestDrop(20043,TRIMDEN_SILK_ID,1)
STARTED.addQuestDrop(20038,PREDATOR_CARAPACE_ID,1)
