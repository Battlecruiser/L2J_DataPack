# Made by Mr. Have fun! Version 0.2
# Fixed by Artful (http://L2PLanet.ru Lineage2 C3 Server)

print "importing quests: 212: Trial Of Duty"
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

MARK_OF_DUTY_ID = 2633
LETTER_OF_DUSTIN_ID = 2634
KNIGHTS_TEAR_ID = 2635
MIRROR_OF_ORPIC_ID = 2636
TEAR_OF_CONFESSION_ID = 2637
REPORT_PIECE_ID = 2638
TALIANUSS_REPORT_ID = 2639
TEAR_OF_LOYALTY_ID = 2640
MILITAS_ARTICLE_ID = 2641
SAINTS_ASHES_URN_ID = 2642
ATEBALTS_SKULL_ID = 2643
ATEBALTS_RIBS_ID = 2644
ATEBALTS_SHIN_ID = 2645
LETTER_OF_WINDAWOOD_ID = 2646
OLD_KNIGHT_SWORD_ID = 3027

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
      htmltext = "7109-04.htm"
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
    elif event == "7116_1" :
          htmltext = "7116-02.htm"
    elif event == "7116_2" :
          htmltext = "7116-03.htm"
    elif event == "7116_3" :
          htmltext = "7116-04.htm"
    elif event == "7116_4" :
          htmltext = "7116-05.htm"
          st.takeItems(TEAR_OF_LOYALTY_ID,1)
          st.set("cond","11")
    return htmltext


 def onTalk (Self,npc,st):
   npcId = npc.getNpcId()
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   id = st.getState()
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 7109 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if st.getPlayer().getClassId().ordinal() in [ 0x04, 0x13, 0x20] :
         if st.getPlayer().getLevel() >= 35 :
            htmltext = "7109-03.htm"
         else :
            htmltext = "7109-01.htm"
            st.exitQuest(1)
      else:
         htmltext = "7109-02.htm"
         st.exitQuest(1)
   elif npcId == 7109 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 7109 and int(st.get("cond"))==14  and st.getQuestItemsCount(LETTER_OF_DUSTIN_ID):
      st.addExpAndSp(79832,3750)
      st.giveItems(7562,8)
      htmltext = "7109-05.htm"
      st.takeItems(LETTER_OF_DUSTIN_ID,1)
      st.giveItems(MARK_OF_DUTY_ID,1)
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
      st.set("cond","0")
      st.getPcSpawn().removeAllSpawn()
   elif npcId == 7109 and int(st.get("cond"))==1 :
      htmltext = "7109-04.htm"
   elif npcId == 7653 and int(st.get("cond"))==1 :
      htmltext = "7653-01.htm"
      if st.getQuestItemsCount(OLD_KNIGHT_SWORD_ID) == 0 :
        st.giveItems(OLD_KNIGHT_SWORD_ID,1)
      st.set("cond","2")
   elif npcId == 7653 and int(st.get("cond"))==2 and st.getQuestItemsCount(KNIGHTS_TEAR_ID)==0 :
      htmltext = "7653-02.htm"
   elif npcId == 7653 and int(st.get("cond"))==3 and st.getQuestItemsCount(KNIGHTS_TEAR_ID) :
      htmltext = "7653-03.htm"
      st.takeItems(KNIGHTS_TEAR_ID,1)
      st.takeItems(OLD_KNIGHT_SWORD_ID,1)
      st.set("cond","4")
   elif npcId == 7653 and int(st.get("cond"))==4 :
      htmltext = "7653-04.htm"
   elif npcId == 7654 and int(st.get("cond"))==4 :
      htmltext = "7654-01.htm"
      st.set("cond","5")
   elif npcId == 7654 and int(st.get("cond"))==5 and st.getQuestItemsCount(TALIANUSS_REPORT_ID)==0 :
      htmltext = "7654-02.htm"
   elif npcId == 7654 and int(st.get("cond"))==5 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) :
      htmltext = "7654-03.htm"
      st.set("cond","6")
      st.giveItems(MIRROR_OF_ORPIC_ID,1)
   elif npcId == 7654 and int(st.get("cond"))==6 :
      htmltext = "7654-04.htm"
   elif npcId == 7654 and int(st.get("cond"))==7 and st.getQuestItemsCount(TEAR_OF_CONFESSION_ID) :
      htmltext = "7654-05.htm"
      st.takeItems(TEAR_OF_CONFESSION_ID,1)
      st.set("cond","8")
   elif npcId == 7654 and int(st.get("cond"))==8 :
      htmltext = "7654-06.htm"
   elif npcId == 7656 and int(st.get("cond"))==6 and st.getQuestItemsCount(MIRROR_OF_ORPIC_ID) :
      htmltext = "7656-01.htm"
      st.takeItems(MIRROR_OF_ORPIC_ID,1)
      st.takeItems(TALIANUSS_REPORT_ID,1)
      st.giveItems(TEAR_OF_CONFESSION_ID,1)
      st.set("cond","7")
   elif npcId == 7655 and int(st.get("cond"))==8 :
      if st.getPlayer().getLevel() >= 36 :
        htmltext = "7655-02.htm"
        st.set("cond","9")
      else:
        htmltext = "7655-01.htm"
   elif npcId == 7655 and int(st.get("cond"))==9 :
      if st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        htmltext = "7655-03.htm"
      else:
        htmltext = "7655-04.htm"
        st.takeItems(MILITAS_ARTICLE_ID,st.getQuestItemsCount(MILITAS_ARTICLE_ID))
        st.giveItems(TEAR_OF_LOYALTY_ID,1)
        st.set("cond","10")
   elif npcId == 7655 and int(st.get("cond"))==10 :
      htmltext = "7655-05.htm"
   elif npcId == 7116 and int(st.get("cond"))==10 and st.getQuestItemsCount(TEAR_OF_LOYALTY_ID) :
      htmltext = "7116-01.htm"
      st.set("cond","9")
   elif npcId == 7116 and int(st.get("cond"))==11 and not (st.getQuestItemsCount(ATEBALTS_SKULL_ID) and st.getQuestItemsCount(ATEBALTS_RIBS_ID) and st.getQuestItemsCount(ATEBALTS_SHIN_ID)) :
      htmltext = "7116-06.htm"
   elif npcId == 7116 and int(st.get("cond"))==50 :
      htmltext = "7116-07.htm"
      st.takeItems(ATEBALTS_SKULL_ID,1)
      st.takeItems(ATEBALTS_RIBS_ID,1)
      st.takeItems(ATEBALTS_SHIN_ID,1)
      st.giveItems(SAINTS_ASHES_URN_ID,1)
      st.set("cond","12")
   elif npcId == 7116 and int(st.get("cond"))==13 and st.getQuestItemsCount(LETTER_OF_WINDAWOOD_ID) :
      htmltext = "7116-08.htm"
      st.takeItems(LETTER_OF_WINDAWOOD_ID,1)
      st.giveItems(LETTER_OF_DUSTIN_ID,1)
      st.set("cond","14")
   elif npcId == 7116 and int(st.get("cond"))==12 :
      htmltext = "7116-09.htm"
   elif npcId == 7116 and int(st.get("cond"))==14 :
      htmltext = "7116-10.htm"
   elif npcId == 7311 and int(st.get("cond"))==12 and st.getQuestItemsCount(SAINTS_ASHES_URN_ID) :
      htmltext = "7311-01.htm"
      st.takeItems(SAINTS_ASHES_URN_ID,1)
      st.giveItems(LETTER_OF_WINDAWOOD_ID,1)
      st.set("cond","13")
   elif npcId == 7311 and int(st.get("cond"))==13 :
      htmltext = "7311-02.htm"
   return htmltext

 def onKill (self,npc,st):

   npcId = npc.getNpcId()
   if npcId in [190,191] :
      if int(st.get("cond")) == 2 :
        if st.getRandom(50)<2 :
          st.getPcSpawn().addSpawn(5119)
          st.playSound("Itemsound.quest_before_battle")
   elif npcId == 5119 :
      if int(st.get("cond")) == 2 and st.getQuestItemsCount(OLD_KNIGHT_SWORD_ID) > 0 :
        st.giveItems(KNIGHTS_TEAR_ID,1)
        st.playSound("ItemSound.quest_middle")
        st.set("cond","3")
   elif npcId == 200 :
      if int(st.get("cond")) == 5 and st.getQuestItemsCount(REPORT_PIECE_ID) < 10 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) == 0 :
        if st.getQuestItemsCount(REPORT_PIECE_ID) == 9 :
          if st.getRandom(2) == 1 :
            st.takeItems(REPORT_PIECE_ID,st.getQuestItemsCount(REPORT_PIECE_ID))
            st.giveItems(TALIANUSS_REPORT_ID,1)
            st.playSound("ItemSound.quest_middle")
        elif st.getRandom(2) == 1 :
          st.giveItems(REPORT_PIECE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 201 :
      if int(st.get("cond")) == 5 and st.getQuestItemsCount(REPORT_PIECE_ID) < 10 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) == 0 :
        if st.getQuestItemsCount(REPORT_PIECE_ID) == 9 :
          if st.getRandom(2) == 1 :
            st.takeItems(REPORT_PIECE_ID,st.getQuestItemsCount(REPORT_PIECE_ID))
            st.giveItems(TALIANUSS_REPORT_ID,1)
            st.playSound("ItemSound.quest_middle")
        elif st.getRandom(2) == 1 :
          st.giveItems(REPORT_PIECE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 144 :
      if int(st.get("cond")) == 6 :
        if st.getRandom(75)<2 :
           st.getPcSpawn().addSpawn(7656,44656,148431,-3703,300000)
           return "Spirit Of Sir Talianus has spawned at X=44656 Y=148431 Z=-3703"
           st.playSound("ItemSound.quest_middle")
   elif npcId == 577 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 578 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 579 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 580 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 581 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 582 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 270 :
      if int(st.get("cond")) == 11 :
        if st.getRandom(2) == 1 :
          if st.getQuestItemsCount(ATEBALTS_SKULL_ID) == 0 :
            st.giveItems(ATEBALTS_SKULL_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(ATEBALTS_RIBS_ID) == 0 :
            st.giveItems(ATEBALTS_RIBS_ID,1)
            st.playSound("ItemSound.quest_itemget")
          elif st.getQuestItemsCount(ATEBALTS_SHIN_ID) == 0 :
            st.giveItems(ATEBALTS_SHIN_ID,1)
            st.set("cond","50")
            st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(212,"212_TrialOfDuty","Trial Of Duty")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7109)

CREATED.addTalkId(7109)
COMPLETED.addTalkId(7109)

STARTED.addTalkId(7109)
STARTED.addTalkId(7116)
STARTED.addTalkId(7311)
STARTED.addTalkId(7653)
STARTED.addTalkId(7654)
STARTED.addTalkId(7655)
STARTED.addTalkId(7656)

STARTED.addKillId(144)
STARTED.addKillId(190)
STARTED.addKillId(191)
STARTED.addKillId(200)
STARTED.addKillId(201)
STARTED.addKillId(270)
STARTED.addKillId(5119)
STARTED.addKillId(577)
STARTED.addKillId(578)
STARTED.addKillId(579)
STARTED.addKillId(580)
STARTED.addKillId(581)
STARTED.addKillId(582)

STARTED.addQuestDrop(7116,LETTER_OF_DUSTIN_ID,1)
STARTED.addQuestDrop(5119,KNIGHTS_TEAR_ID,1)
STARTED.addQuestDrop(7653,OLD_KNIGHT_SWORD_ID,1)
STARTED.addQuestDrop(7656,TEAR_OF_CONFESSION_ID,1)
STARTED.addQuestDrop(7654,MIRROR_OF_ORPIC_ID,1)
STARTED.addQuestDrop(200,TALIANUSS_REPORT_ID,1)
STARTED.addQuestDrop(201,TALIANUSS_REPORT_ID,1)
STARTED.addQuestDrop(577,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(578,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(579,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(580,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(581,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(582,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(270,ATEBALTS_SKULL_ID,1)
STARTED.addQuestDrop(270,ATEBALTS_RIBS_ID,1)
STARTED.addQuestDrop(270,ATEBALTS_SHIN_ID,1)
STARTED.addQuestDrop(7311,LETTER_OF_WINDAWOOD_ID,1)
STARTED.addQuestDrop(7655,TEAR_OF_LOYALTY_ID,1)
STARTED.addQuestDrop(7116,SAINTS_ASHES_URN_ID,1)
STARTED.addQuestDrop(200,REPORT_PIECE_ID,1)
STARTED.addQuestDrop(201,REPORT_PIECE_ID,1)
