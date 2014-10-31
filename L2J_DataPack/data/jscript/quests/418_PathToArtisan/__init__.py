# Maked by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

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
    if event == "7527_1" :
          if st.getPlayer().getClassId().getId() != 0x35 :
            if st.getPlayer().getClassId().getId() == 0x38 :
              htmltext = "7527-02a.htm"
            else:
              htmltext = "7527-02.htm"
          else:
            if st.getPlayer().getLevel()<19 :
              htmltext = "7527-03.htm"
            else:
              if st.getQuestItemsCount(PASS_FINAL_ID) != 0 :
                htmltext = "7527-04.htm"
              else:
                htmltext = "7527-05.htm"
                return htmltext
    elif event == "7527_2" :
          htmltext = "7527-11.htm"
          st.takeItems(TOTEM_SPIRIT_CLAW_ID,1)
          st.giveItems(TATARUS_LETTER_ID,1)
    elif event == "1" :
        st.set("id","0")
        st.set("cond","1")
        st.setState(STARTED)
        st.playSound("ItemSound.quest_accept")
        htmltext = "7527-06.htm"
        st.giveItems(SILVERYS_RING_ID,1)
    elif event == "7317_1" :
          htmltext = "7317-02.htm"
    elif event == "7317_2" :
          htmltext = "7317-05.htm"
    elif event == "7317_3" :
          htmltext = "7317-03.htm"
    elif event == "7317_4" :
          htmltext = "7317-04.htm"
          st.giveItems(KLUTOS_LETTER_ID,1)
          st.set("cond","4")
    elif event == "7317_5" :
          htmltext = "7317-06.htm"
    elif event == "7317_6" :
          htmltext = "7317-07.htm"
          st.giveItems(KLUTOS_LETTER_ID,1)
          st.set("cond","4")
    elif event == "7317_7" :
        if st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
          htmltext = "7317-10.htm"
          st.takeItems(PASS_1ST_ID,1)
          st.takeItems(PASS_2ND_ID,1)
          st.takeItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_FINAL_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        else :
          htmltext = "7317-08.htm"
    elif event == "7317_8" :
          htmltext = "7317-11.htm"
    elif event == "7317_9" :
        if st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
          htmltext = "7317-12.htm"
          st.takeItems(PASS_1ST_ID,1)
          st.takeItems(PASS_2ND_ID,1)
          st.takeItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_FINAL_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
        else :
          htmltext = "7317-08.htm"
    elif event == "7298_1" :
          htmltext = "7298-02.htm"
    elif event == "7298_2" :
          htmltext = "7298-03.htm"
          st.takeItems(KLUTOS_LETTER_ID,1)
          st.giveItems(FOOTPRINT_ID,1)
          st.set("cond","5")
    elif event == "7298_3" :
          htmltext = "7298-06.htm"
          st.takeItems(SECRET_BOX1_ID,1)
          st.takeItems(FOOTPRINT_ID,1)
          st.giveItems(SECRET_BOX2_ID,1)
          st.giveItems(PASS_2ND_ID,1)
          st.set("cond","7")
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
   if npcId == 7527 and int(st.get("cond"))==0 :
          htmltext = "7527-01.htm"
   elif npcId == 7527 and int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID)==1 and (st.getQuestItemsCount(RATMAN_TOOTH_ID)+st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID))<12 :
        htmltext = "7527-07.htm"
   elif npcId == 7527 and int(st.get("cond")) and st.getQuestItemsCount(SILVERYS_RING_ID)==1 and st.getQuestItemsCount(RATMAN_TOOTH_ID)>=10 and st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID)>=2 :
        htmltext = "7527-08.htm"
        st.takeItems(SILVERYS_RING_ID,st.getQuestItemsCount(SILVERYS_RING_ID))
        st.takeItems(RATMAN_TOOTH_ID,st.getQuestItemsCount(RATMAN_TOOTH_ID))
        st.takeItems(BIG_RATMAN_TOOTH_ID,st.getQuestItemsCount(BIG_RATMAN_TOOTH_ID))
        st.giveItems(PASS_1ST_ID,1)
        st.set("cond","3")
   elif npcId == 7527 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID)==1 :
        htmltext = "7527-09.htm"
   elif npcId == 7317 and int(st.get("cond")) and st.getQuestItemsCount(KLUTOS_LETTER_ID)==0 and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID)==0 and st.getQuestItemsCount(SECRET_BOX2_ID)==0 :
        htmltext = "7317-01.htm"
   elif npcId == 7317 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and (st.getQuestItemsCount(KLUTOS_LETTER_ID) or st.getQuestItemsCount(FOOTPRINT_ID)) :
        htmltext = "7317-08.htm"
   elif npcId == 7317 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
        htmltext = "7317-09.htm"
   elif npcId == 7298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(KLUTOS_LETTER_ID) :
        htmltext = "7298-01.htm"
   elif npcId == 7298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(FOOTPRINT_ID) and st.getQuestItemsCount(SECRET_BOX1_ID)==0 :
        htmltext = "7298-04.htm"
   elif npcId == 7298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(FOOTPRINT_ID) and st.getQuestItemsCount(SECRET_BOX1_ID) :
        htmltext = "7298-05.htm"
   elif npcId == 7298 and int(st.get("cond")) and st.getQuestItemsCount(PASS_1ST_ID) and st.getQuestItemsCount(PASS_2ND_ID) and st.getQuestItemsCount(SECRET_BOX2_ID) :
        htmltext = "7298-07.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 389 :
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
   elif npcId == 390 :
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
   elif npcId == 17 :
        st.set("id","0")
        if int(st.get("cond")) and st.getQuestItemsCount(FOOTPRINT_ID) == 1 and st.getQuestItemsCount(SECRET_BOX1_ID)<1 :
          if st.getRandom(10)<2 :
            st.giveItems(SECRET_BOX1_ID,1)
            st.playSound("ItemSound.quest_middle")
            st.set("cond","6")
   return

QUEST       = Quest(418,"418_PathToArtisan","Path To Artisan")
CREATED     = State('Start', QUEST)
STARTING     = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7527)

STARTING.addTalkId(7527)

STARTED.addTalkId(7298)
STARTED.addTalkId(7317)
STARTED.addTalkId(7527)

STARTED.addKillId(17)
STARTED.addKillId(389)
STARTED.addKillId(390)

STARTED.addQuestDrop(7527,SILVERYS_RING_ID,1)
STARTED.addQuestDrop(389,RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(390,BIG_RATMAN_TOOTH_ID,1)
STARTED.addQuestDrop(7527,PASS_1ST_ID,1)
STARTED.addQuestDrop(7298,PASS_2ND_ID,1)
STARTED.addQuestDrop(7298,SECRET_BOX2_ID,1)
STARTED.addQuestDrop(7527,PASS_1ST_ID,1)
STARTED.addQuestDrop(7298,PASS_2ND_ID,1)
STARTED.addQuestDrop(7298,SECRET_BOX2_ID,1)
STARTED.addQuestDrop(7317,KLUTOS_LETTER_ID,1)
STARTED.addQuestDrop(17,SECRET_BOX1_ID,1)
STARTED.addQuestDrop(7298,FOOTPRINT_ID,1)

print "importing quests: 418: Path To Artisan"
