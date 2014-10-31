# Made by Mr. Have fun! Version 0.2
# Fixed by Artful (http://L2PLanet.ru Lineage2 C3 Server)
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "212_TrialOfDuty"

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
      htmltext = "30109-04.htm"
      st.setState(STARTED)
      st.playSound("ItemSound.quest_accept")
      st.set("cond","1")
    elif event == "30116_1" :
          htmltext = "30116-02.htm"
    elif event == "30116_2" :
          htmltext = "30116-03.htm"
    elif event == "30116_3" :
          htmltext = "30116-04.htm"
    elif event == "30116_4" :
          htmltext = "30116-05.htm"
          st.takeItems(TEAR_OF_LOYALTY_ID,1)
          st.set("cond","11")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30109 and id != STARTED : return htmltext
   if id == CREATED :
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30109 and int(st.get("cond"))==0 and int(st.get("onlyone"))==0 :
      if st.getPlayer().getClassId().ordinal() in [ 0x04, 0x13, 0x20] :
         if st.getPlayer().getLevel() >= 35 :
            htmltext = "30109-03.htm"
         else :
            htmltext = "30109-01.htm"
            st.exitQuest(1)
      else:
         htmltext = "30109-02.htm"
         st.exitQuest(1)
   elif npcId == 30109 and int(st.get("cond"))==0 and int(st.get("onlyone"))==1 :
      htmltext = "<html><head><body>This quest have already been completed.</body></html>"
   elif npcId == 30109 and int(st.get("cond"))==14  and st.getQuestItemsCount(LETTER_OF_DUSTIN_ID):
      st.addExpAndSp(79832,3750)
      st.giveItems(7562,8)
      htmltext = "30109-05.htm"
      st.takeItems(LETTER_OF_DUSTIN_ID,1)
      st.giveItems(MARK_OF_DUTY_ID,1)
      st.setState(COMPLETED)
      st.playSound("ItemSound.quest_finish")
      st.set("onlyone","1")
      st.set("cond","0")
      st.getPcSpawn().removeAllSpawn()
   elif npcId == 30109 and int(st.get("cond"))==1 :
      htmltext = "30109-04.htm"
   elif npcId == 30653 and int(st.get("cond"))==1 :
      htmltext = "30653-01.htm"
      if st.getQuestItemsCount(OLD_KNIGHT_SWORD_ID) == 0 :
        st.giveItems(OLD_KNIGHT_SWORD_ID,1)
      st.set("cond","2")
   elif npcId == 30653 and int(st.get("cond"))==2 and st.getQuestItemsCount(KNIGHTS_TEAR_ID)==0 :
      htmltext = "30653-02.htm"
   elif npcId == 30653 and int(st.get("cond"))==3 and st.getQuestItemsCount(KNIGHTS_TEAR_ID) :
      htmltext = "30653-03.htm"
      st.takeItems(KNIGHTS_TEAR_ID,1)
      st.takeItems(OLD_KNIGHT_SWORD_ID,1)
      st.set("cond","4")
   elif npcId == 30653 and int(st.get("cond"))==4 :
      htmltext = "30653-04.htm"
   elif npcId == 30654 and int(st.get("cond"))==4 :
      htmltext = "30654-01.htm"
      st.set("cond","5")
   elif npcId == 30654 and int(st.get("cond"))==5 and st.getQuestItemsCount(TALIANUSS_REPORT_ID)==0 :
      htmltext = "30654-02.htm"
   elif npcId == 30654 and int(st.get("cond"))==5 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) :
      htmltext = "30654-03.htm"
      st.set("cond","6")
      st.giveItems(MIRROR_OF_ORPIC_ID,1)
   elif npcId == 30654 and int(st.get("cond"))==6 :
      htmltext = "30654-04.htm"
   elif npcId == 30654 and int(st.get("cond"))==7 and st.getQuestItemsCount(TEAR_OF_CONFESSION_ID) :
      htmltext = "30654-05.htm"
      st.takeItems(TEAR_OF_CONFESSION_ID,1)
      st.set("cond","8")
   elif npcId == 30654 and int(st.get("cond"))==8 :
      htmltext = "30654-06.htm"
   elif npcId == 30656 and int(st.get("cond"))==6 and st.getQuestItemsCount(MIRROR_OF_ORPIC_ID) :
      htmltext = "30656-01.htm"
      st.takeItems(MIRROR_OF_ORPIC_ID,1)
      st.takeItems(TALIANUSS_REPORT_ID,1)
      st.giveItems(TEAR_OF_CONFESSION_ID,1)
      st.set("cond","7")
   elif npcId == 30655 and int(st.get("cond"))==8 :
      if st.getPlayer().getLevel() >= 36 :
        htmltext = "30655-02.htm"
        st.set("cond","9")
      else:
        htmltext = "30655-01.htm"
   elif npcId == 30655 and int(st.get("cond"))==9 :
      if st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        htmltext = "30655-03.htm"
      else:
        htmltext = "30655-04.htm"
        st.takeItems(MILITAS_ARTICLE_ID,st.getQuestItemsCount(MILITAS_ARTICLE_ID))
        st.giveItems(TEAR_OF_LOYALTY_ID,1)
        st.set("cond","10")
   elif npcId == 30655 and int(st.get("cond"))==10 :
      htmltext = "30655-05.htm"
   elif npcId == 30116 and int(st.get("cond"))==10 and st.getQuestItemsCount(TEAR_OF_LOYALTY_ID) :
      htmltext = "30116-01.htm"
      st.set("cond","9")
   elif npcId == 30116 and int(st.get("cond"))==11 and not (st.getQuestItemsCount(ATEBALTS_SKULL_ID) and st.getQuestItemsCount(ATEBALTS_RIBS_ID) and st.getQuestItemsCount(ATEBALTS_SHIN_ID)) :
      htmltext = "30116-06.htm"
   elif npcId == 30116 and int(st.get("cond"))==50 :
      htmltext = "30116-07.htm"
      st.takeItems(ATEBALTS_SKULL_ID,1)
      st.takeItems(ATEBALTS_RIBS_ID,1)
      st.takeItems(ATEBALTS_SHIN_ID,1)
      st.giveItems(SAINTS_ASHES_URN_ID,1)
      st.set("cond","12")
   elif npcId == 30116 and int(st.get("cond"))==13 and st.getQuestItemsCount(LETTER_OF_WINDAWOOD_ID) :
      htmltext = "30116-08.htm"
      st.takeItems(LETTER_OF_WINDAWOOD_ID,1)
      st.giveItems(LETTER_OF_DUSTIN_ID,1)
      st.set("cond","14")
   elif npcId == 30116 and int(st.get("cond"))==12 :
      htmltext = "30116-09.htm"
   elif npcId == 30116 and int(st.get("cond"))==14 :
      htmltext = "30116-10.htm"
   elif npcId == 30311 and int(st.get("cond"))==12 and st.getQuestItemsCount(SAINTS_ASHES_URN_ID) :
      htmltext = "30311-01.htm"
      st.takeItems(SAINTS_ASHES_URN_ID,1)
      st.giveItems(LETTER_OF_WINDAWOOD_ID,1)
      st.set("cond","13")
   elif npcId == 30311 and int(st.get("cond"))==13 :
      htmltext = "30311-02.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return 

   npcId = npc.getNpcId()
   if npcId in [20190,20191] :
      if int(st.get("cond")) == 2 :
        if st.getRandom(50)<2 :
          st.getPcSpawn().addSpawn(27119)
          st.playSound("Itemsound.quest_before_battle")
   elif npcId == 27119 :
      if int(st.get("cond")) == 2 and st.getQuestItemsCount(OLD_KNIGHT_SWORD_ID) > 0 :
        st.giveItems(KNIGHTS_TEAR_ID,1)
        st.playSound("ItemSound.quest_middle")
        st.set("cond","3")
   elif npcId == 20200 :
      if int(st.get("cond")) == 5 and st.getQuestItemsCount(REPORT_PIECE_ID) < 10 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) == 0 :
        if st.getQuestItemsCount(REPORT_PIECE_ID) == 9 :
          if st.getRandom(2) == 1 :
            st.takeItems(REPORT_PIECE_ID,st.getQuestItemsCount(REPORT_PIECE_ID))
            st.giveItems(TALIANUSS_REPORT_ID,1)
            st.playSound("ItemSound.quest_middle")
        elif st.getRandom(2) == 1 :
          st.giveItems(REPORT_PIECE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20201 :
      if int(st.get("cond")) == 5 and st.getQuestItemsCount(REPORT_PIECE_ID) < 10 and st.getQuestItemsCount(TALIANUSS_REPORT_ID) == 0 :
        if st.getQuestItemsCount(REPORT_PIECE_ID) == 9 :
          if st.getRandom(2) == 1 :
            st.takeItems(REPORT_PIECE_ID,st.getQuestItemsCount(REPORT_PIECE_ID))
            st.giveItems(TALIANUSS_REPORT_ID,1)
            st.playSound("ItemSound.quest_middle")
        elif st.getRandom(2) == 1 :
          st.giveItems(REPORT_PIECE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20144 :
      if int(st.get("cond")) == 6 :
        if st.getRandom(100)<33 :
           st.getPcSpawn().addSpawn(30656,44656,148431,-3703,300000)
           return "Spirit Of Sir Talianus has spawned at X=44656 Y=148431 Z=-3703"
           st.playSound("ItemSound.quest_middle")
   elif npcId == 20577 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20578 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20579 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20580 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20581 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20582 :
      if int(st.get("cond")) == 9 and st.getQuestItemsCount(MILITAS_ARTICLE_ID) < 20 :
        if st.getQuestItemsCount(MILITAS_ARTICLE_ID) == 19 :
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_middle")
        else:
          st.giveItems(MILITAS_ARTICLE_ID,1)
          st.playSound("ItemSound.quest_itemget")
   elif npcId == 20270 :
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

QUEST       = Quest(212,qn,"Trial Of Duty")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30109)

QUEST.addTalkId(30109)

QUEST.addTalkId(30116)
QUEST.addTalkId(30311)
QUEST.addTalkId(30653)
QUEST.addTalkId(30654)
QUEST.addTalkId(30655)
QUEST.addTalkId(30656)

QUEST.addKillId(20144)
QUEST.addKillId(20190)
QUEST.addKillId(20191)
QUEST.addKillId(20200)
QUEST.addKillId(20201)
QUEST.addKillId(20270)
QUEST.addKillId(27119)
QUEST.addKillId(20577)
QUEST.addKillId(20578)
QUEST.addKillId(20579)
QUEST.addKillId(20580)
QUEST.addKillId(20581)
QUEST.addKillId(20582)

STARTED.addQuestDrop(30116,LETTER_OF_DUSTIN_ID,1)
STARTED.addQuestDrop(27119,KNIGHTS_TEAR_ID,1)
STARTED.addQuestDrop(30653,OLD_KNIGHT_SWORD_ID,1)
STARTED.addQuestDrop(30656,TEAR_OF_CONFESSION_ID,1)
STARTED.addQuestDrop(30654,MIRROR_OF_ORPIC_ID,1)
STARTED.addQuestDrop(20200,TALIANUSS_REPORT_ID,1)
STARTED.addQuestDrop(20577,MILITAS_ARTICLE_ID,1)
STARTED.addQuestDrop(20270,ATEBALTS_SKULL_ID,1)
STARTED.addQuestDrop(20270,ATEBALTS_RIBS_ID,1)
STARTED.addQuestDrop(20270,ATEBALTS_SHIN_ID,1)
STARTED.addQuestDrop(30311,LETTER_OF_WINDAWOOD_ID,1)
STARTED.addQuestDrop(30655,TEAR_OF_LOYALTY_ID,1)
STARTED.addQuestDrop(30116,SAINTS_ASHES_URN_ID,1)
STARTED.addQuestDrop(20200,REPORT_PIECE_ID,1)

print "importing quests: 212: Trial Of Duty"