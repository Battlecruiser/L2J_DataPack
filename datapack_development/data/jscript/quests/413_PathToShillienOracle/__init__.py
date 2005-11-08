# Maked by Mr. Have fun! Version 0.2
print "importing quests: 413: Path To Shillien Oracle"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

SIDRAS_LETTER1_ID = 1262
BLANK_SHEET1_ID = 1263
BLOODY_RUNE1_ID = 1264
GARMIEL_BOOK_ID = 1265
PRAYER_OF_ADON_ID = 1266
PENITENTS_MARK_ID = 1267
ASHEN_BONES_ID = 1268
ANDARIEL_BOOK_ID = 1269
ORB_OF_ABYSS_ID = 1270

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
        st.set("id","0")
        htmltext = "7330-06.htm"
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        st.giveItems(SIDRAS_LETTER1_ID,1)
    elif event == "413_1" :
          if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x26 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 0 :
            htmltext = "7330-05.htm"
            return htmltext
          elif st.getPlayer().getClassId().getId() != 0x26 :
              if st.getPlayer().getClassId().getId() == 0x2a :
                htmltext = "7330-02a.htm"
              else:
                htmltext = "7330-03.htm"
          elif st.getPlayer().getLevel()<19 and st.getPlayer().getClassId().getId() == 0x26 :
              htmltext = "7330-02.htm"
          elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x26 and st.getQuestItemsCount(ORB_OF_ABYSS_ID) == 1 :
              htmltext = "7330-04.htm"
    elif event == "7377_1" :
          htmltext = "7377-02.htm"
          st.takeItems(SIDRAS_LETTER1_ID,1)
          st.giveItems(BLANK_SHEET1_ID,5)
          st.set("cond","2")
    elif event == "7375_1" :
          htmltext = "7375-02.htm"
    elif event == "7375_2" :
            htmltext = "7375-03.htm"
    elif event == "7375_3" :
            htmltext = "7375-04.htm"
            st.takeItems(PRAYER_OF_ADON_ID,1)
            st.giveItems(PENITENTS_MARK_ID,1)
            st.set("cond","5")
    return htmltext


 def onTalk (Self,npc,st):

   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7330 and int(st.get("cond"))==0 :
        if int(st.get("cond"))<15 :
          htmltext = "7330-01.htm"
        else:
          htmltext = "7330-01.htm"
   elif npcId == 7330 and int(st.get("cond")) :
        if st.getQuestItemsCount(SIDRAS_LETTER1_ID) == 1 :
          htmltext = "7330-07.htm"
        elif st.getQuestItemsCount(BLANK_SHEET1_ID)>0 or st.getQuestItemsCount(BLOODY_RUNE1_ID) == 1 :
            htmltext = "7330-08.htm"
        elif st.getQuestItemsCount(ANDARIEL_BOOK_ID) == 0 and st.getQuestItemsCount(PRAYER_OF_ADON_ID)+st.getQuestItemsCount(GARMIEL_BOOK_ID)+st.getQuestItemsCount(PENITENTS_MARK_ID)+st.getQuestItemsCount(ASHEN_BONES_ID)>0 :
            htmltext = "7330-09.htm"
        elif st.getQuestItemsCount(ANDARIEL_BOOK_ID) == 1 and st.getQuestItemsCount(GARMIEL_BOOK_ID) == 1 :
            htmltext = "7330-10.htm"
            st.takeItems(ANDARIEL_BOOK_ID,1)
            st.takeItems(GARMIEL_BOOK_ID,1)
            st.giveItems(ORB_OF_ABYSS_ID,1)
            st.set("cond","0")
            st.setState(COMPLETED)
            st.playSound("ItemSound.quest_finish")
   elif npcId == 7377 and int(st.get("cond")) :
        if st.getQuestItemsCount(SIDRAS_LETTER1_ID) == 1 :
          htmltext = "7377-01.htm"
        elif st.getQuestItemsCount(BLANK_SHEET1_ID) == 5 and st.getQuestItemsCount(BLOODY_RUNE1_ID) == 0 :
            htmltext = "7377-03.htm"
        elif st.getQuestItemsCount(BLOODY_RUNE1_ID)>0 and st.getQuestItemsCount(BLOODY_RUNE1_ID)<5 :
            htmltext = "7377-04.htm"
        elif st.getQuestItemsCount(BLOODY_RUNE1_ID) >= 5 :
            htmltext = "7377-05.htm"
            st.takeItems(BLOODY_RUNE1_ID,st.getQuestItemsCount(BLOODY_RUNE1_ID))
            st.giveItems(GARMIEL_BOOK_ID,1)
            st.giveItems(PRAYER_OF_ADON_ID,1)
            st.set("cond","4")
        elif st.getQuestItemsCount(PRAYER_OF_ADON_ID)+st.getQuestItemsCount(PENITENTS_MARK_ID)+st.getQuestItemsCount(ASHEN_BONES_ID)>0 :
            htmltext = "7377-06.htm"
        elif st.getQuestItemsCount(ANDARIEL_BOOK_ID) == 1 and st.getQuestItemsCount(GARMIEL_BOOK_ID) == 1 :
            htmltext = "7377-07.htm"
   elif npcId == 7375 and int(st.get("cond")) :
      if st.getQuestItemsCount(PRAYER_OF_ADON_ID) == 1 :
        htmltext = "7375-01.htm"
      elif st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID) == 0 and st.getQuestItemsCount(ANDARIEL_BOOK_ID) == 0 :
          htmltext = "7375-05.htm"
      elif st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID)<10 and st.getQuestItemsCount(ASHEN_BONES_ID)>0 :
          htmltext = "7375-06.htm"
      elif st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID) >= 10 :
          htmltext = "7375-07.htm"
          st.takeItems(ASHEN_BONES_ID,st.getQuestItemsCount(ASHEN_BONES_ID))
          st.takeItems(PENITENTS_MARK_ID,st.getQuestItemsCount(PENITENTS_MARK_ID))
          st.giveItems(ANDARIEL_BOOK_ID,1)
          st.set("cond","7")
      elif st.getQuestItemsCount(ANDARIEL_BOOK_ID) == 1 :
          htmltext = "7375-08.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId == 776 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(BLANK_SHEET1_ID)>0 :
          st.giveItems(BLOODY_RUNE1_ID,1)
          st.takeItems(BLANK_SHEET1_ID,1)
          if st.getQuestItemsCount(BLANK_SHEET1_ID) == 0 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","3")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 514 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID)<10 :
          st.giveItems(ASHEN_BONES_ID,1)
          if st.getQuestItemsCount(ASHEN_BONES_ID) == 10 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 515 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID)<10 :
          st.giveItems(ASHEN_BONES_ID,1)
          if st.getQuestItemsCount(ASHEN_BONES_ID) == 10 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 457 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID)<10 :
          st.giveItems(ASHEN_BONES_ID,1)
          if st.getQuestItemsCount(ASHEN_BONES_ID) == 10 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
          else:
            st.playSound("ItemSound.quest_itemget")
   elif npcId == 458 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(PENITENTS_MARK_ID) == 1 and st.getQuestItemsCount(ASHEN_BONES_ID)<10 :
          st.giveItems(ASHEN_BONES_ID,1)
          if st.getQuestItemsCount(ASHEN_BONES_ID) == 10 :
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
          else:
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(413,"413_PathToShillienOracle","Path To Shillien Oracle")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7330)

STARTING.addTalkId(7330)

STARTED.addTalkId(7330)
STARTED.addTalkId(7375)
STARTED.addTalkId(7377)

STARTED.addKillId(457)
STARTED.addKillId(458)
STARTED.addKillId(514)
STARTED.addKillId(515)
STARTED.addKillId(776)

STARTED.addQuestDrop(7375,ANDARIEL_BOOK_ID,1)
STARTED.addQuestDrop(7377,GARMIEL_BOOK_ID,1)
STARTED.addQuestDrop(776,BLOODY_RUNE1_ID,1)
STARTED.addQuestDrop(7330,SIDRAS_LETTER1_ID,1)
STARTED.addQuestDrop(514,ASHEN_BONES_ID,1)
STARTED.addQuestDrop(515,ASHEN_BONES_ID,1)
STARTED.addQuestDrop(457,ASHEN_BONES_ID,1)
STARTED.addQuestDrop(458,ASHEN_BONES_ID,1)
STARTED.addQuestDrop(7375,PENITENTS_MARK_ID,1)
STARTED.addQuestDrop(7377,PRAYER_OF_ADON_ID,1)
STARTED.addQuestDrop(7377,BLANK_SHEET1_ID,1)
