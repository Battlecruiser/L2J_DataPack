# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

qn = "418_PathToArtisan"

SILVERYS_RING_ID = 1632
PASS_1ST_ID = 1633
PASS_2ND_ID = 1634
PASS_FINAL_ID = 1635
RATMAN_TOOTH_ID = 1636
BIG_RATMAN_TOOTH_ID = 1637
KLUTOS_LETTER_ID = 1638
FOOTPRINT_ID = 1639
SECRET_BOX1_ID = 1640
SECRET_BOX2_ID = 1641
TOTEM_SPIRIT_CLAW_ID = 1622
TATARUS_LETTER_ID = 1623

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "30527_1" :
          if st.getPlayer().getClassId().getId() != 0x35 :
            if st.getPlayer().getClassId().getId() == 0x38 :
              htmltext = "30527-02a.htm"
            else:
              htmltext = "30527-02.htm"
          else:
            if st.getPlayer().getLevel()<19 :
              htmltext = "30527-03.htm"
            else:
              if st.getQuestItemsCount(PASS_FINAL_ID) != 0 :
                htmltext = "30527-04.htm"
              else:
                htmltext = "30527-05.htm"
                return htmltext
    elif event == "30527_2" :
          htmltext = "30527-11.htm"
          st.takeItems(TOTEM_SPIRIT_CLAW_ID,1)
          st.giveItems(TATARUS_LETTER_ID,1)
    elif event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "30527-06.htm"
        st.giveItems(SILVERYS_RING_ID,1)
    elif event == "30317_1" :
          htmltext = "30317-02.htm"
    elif event == "30317_2" :
          htmltext = "30317-05.htm"
    elif event == "30317_3" :
          htmltext = "30317-03.htm"
    elif event == "30317_4" :
          htmltext = "30317-04.htm"
          st.giveItems(KLUTOS_LETTER_ID,1)
          st.set("cond","4")
    elif event == "30317_5" :
          htmltext = "30317-06.htm"
    elif event == "30317_6" :
          htmltext = "30317-07.htm"
          st.giveItems(KLUTOS_LETTER_ID,1)
          st.set("cond","4")
    elif event == "30317_7" :
        if st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
          htmltext = "30317-10.htm"
          st.takeItems(PASS_1ST_ID,1)
          st.takeItems(PASS_2ND_ID,1)
          st.takeItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_FINAL_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        else :
          htmltext = "30317-08.htm"
    elif event == "30317_8" :
          htmltext = "30317-11.htm"
    elif event == "30317_9" :
        if st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
          htmltext = "30317-12.htm"
          st.takeItems(PASS_1ST_ID,1)
          st.takeItems(PASS_2ND_ID,1)
          st.takeItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_FINAL_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        else :
          htmltext = "30317-08.htm"
    elif event == "30298_1" :
          htmltext = "30298-02.htm"
    elif event == "30298_2" :
          htmltext = "30298-03.htm"
          st.takeItems(KLUTOS_LETTER_ID,1)
          st.giveItems(FOOTPRINT_ID,1)
          st.set("cond","5")
    elif event == "30298_3" :
          htmltext = "30298-06.htm"
          st.takeItems(SECRET_BOX1_ID,1)
          st.takeItems(FOOTPRINT_ID,1)
          st.giveItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_2ND_ID,1)
          st.set("cond","7")
    return htmltext


 def onTalk (self,npc,player):
   htmltext = "<html><head><body>I have nothing to say you</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != 30527 and id != STARTED : return htmltext

   if id == CREATED :
     st.setState(STARTING)
     st.set("cond","0")
     st.set("onlyone","0")
     st.set("id","0")
   if npcId == 30527 and int(st.get("cond"))==0 :
          htmltext = "30527-01.htm"
   elif npcId == 30527 and int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID)==1 and (st.getQuestItemsCount(RATMAN_TOOTH_ID)+st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID))<12 :
        htmltext = "30527-07.htm"
   elif npcId == 30527 and int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID)==1 and st.getQuestItemsCount(RATMAN_TOOTH_ID)>=10 and st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID)>=2 :
        htmltext = "30527-08.htm"
        st.takeItems(SILVERYS_RING_ID,st.getQuestItemsCount(SILVERYS_RING_ID))
        st.takeItems(RATMAN_TOOTH_ID,st.getQuestItemsCount(RATMAN_TOOTH_ID))
        st.takeItems(BIG_RATMAN_TOOTH_ID,st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID))
        st.giveItems(PASS_1ST_ID,1)
        st.set("cond","3")
   elif npcId == 30527 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID)==1 :
        htmltext = "30527-09.htm"
   elif npcId == 30317 and int(st.get("cond")) and st.getQuestItemsCount(KLUTOS_LETTER_ID)==0 and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID)==0 and st.getQuestItemsCount(SECRET_BOX2_ID)==0 :
        htmltext = "30317-01.htm"
   elif npcId == 30317 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and (st.getQuestItemsCount(KLUTOS_LETTER_ID) or st.getQuestItemsCount(FOOTPRINT_ID)) :
        htmltext = "30317-08.htm"
   elif npcId == 30317 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
        htmltext = "30317-09.htm"
   elif npcId == 30298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(KLUTOS_LETTER_ID) :
        htmltext = "30298-01.htm"
   elif npcId == 30298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(FOOTPRINT_ID) and st.getQuestItemsCount(SECRET_BOX1_ID)==0 :
        htmltext = "30298-04.htm"
   elif npcId == 30298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(FOOTPRINT_ID) and st.getQuestItemsCount(SECRET_BOX1_ID) :
        htmltext = "30298-05.htm"
   elif npcId == 30298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
        htmltext = "30298-07.htm"
   return htmltext

 def onKill (self,npc,player):
   st = player.getQuestState(qn)
   if not st : return 
   if st.getState() != STARTED : return
   npcId = npc.getNpcId()
   if npcId == 20389 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID) == 1 and st.getQuestItemsCount(RATMAN_TOOTH_ID)<10 :
          if st.getRandom(10)<7 :
            if st.getQuestItemsCount(RATMAN_TOOTH_ID) == 9 and st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID) == 2 :
              st.giveItems(RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_middle")
              st.set("cond","2")
            else:
              st.giveItems(RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20390 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID) == 1 and st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID)<2 :
          if st.getRandom(10)<5 :
            if st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID) == 1 and st.getQuestItemsCount(RATMAN_TOOTH_ID) == 10 :
              st.giveItems(BIG_RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_middle")
              st.set("cond","2")
            else:
              st.giveItems(BIG_RATMAN_TOOTH_ID,1)
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 20017 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(FOOTPRINT_ID) == 1 and st.getQuestItemsCount(SECRET_BOX1_ID)<1 :
          if st.getRandom(10)<2 :
            st.giveItems(SECRET_BOX1_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
   return

QUEST       = Quest(418,qn,"Path To Artisan")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)
QUEST.addStartNpc(30527)

QUEST.addTalkId(30527)

QUEST.addTalkId(30298)
QUEST.addTalkId(30317)

QUEST.addKillId(20017)
QUEST.addKillId(20389)
QUEST.addKillId(20390)

STARTED.addQuestDrop(30527,SILVERYS_RING_ID,1)
STARTED.addQuestDrop(20389,RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(20390,BIG_RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(30527,PASS_1ST_ID,1)
STARTED.addQuestDrop(30298,PASS_2ND_ID,1)
STARTED.addQuestDrop(30298,SECRET_BOX2_ID,1)
STARTED.addQuestDrop(30527,PASS_1ST_ID,1)
STARTED.addQuestDrop(30298,PASS_2ND_ID,1)
STARTED.addQuestDrop(30298,SECRET_BOX2_ID,1)
STARTED.addQuestDrop(30317,KLUTOS_LETTER_ID,1)
STARTED.addQuestDrop(20017,SECRET_BOX1_ID,1)
STARTED.addQuestDrop(30298,FOOTPRINT_ID,1)

print "importing quests: 418: Path To Artisan"
