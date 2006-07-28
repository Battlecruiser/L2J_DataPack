# Made by Mr. Have fun! Version 0.2
import sys
from net.sf.l2j.gameserver.model.quest import State
from net.sf.l2j.gameserver.model.quest import QuestState
from net.sf.l2j.gameserver.model.quest.jython import QuestJython as JQuest

RING_OF_RAVEN_ID = 1642
PIPIS_LETTER_ID = 1643
ROUTS_TP_SCROLL_ID = 1644
SUCCUBUS_UNDIES_ID = 1645
MIONS_LETTER_ID = 1646
BRONKS_INGOT_ID = 1647
CHALIS_AXE_ID = 1648
ZIMENFS_POTION_ID = 1649
BRONKS_PAY_ID = 1650
CHALIS_PAY_ID = 1651
ZIMENFS_PAY_ID = 1652
BEAR_PIC_ID = 1653
TARANTULA_PIC_ID = 1654
HONEY_JAR_ID = 1655
BEAD_ID = 1656
BEAD_PARCEL_ID = 1657

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event
    if event == "1" :
          st.set("id","0")
          if st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x35 and st.getQuestItemsCount(RING_OF_RAVEN_ID) == 0 :
            st.set("cond","1")
            st.setState(STARTED)
            st.playSound("ItemSound.quest_accept")
            st.giveItems(PIPIS_LETTER_ID,1)
            htmltext = "7524-05.htm"
          elif st.getPlayer().getClassId().getId() != 0x35 :
                if st.getPlayer().getClassId().getId() == 0x36 :
                  htmltext = "7524-02a.htm"
                else:
                  htmltext = "7524-08.htm"
          elif st.getPlayer().getLevel() < 19 and st.getPlayer().getClassId().getId() == 0x35 :
                htmltext = "7524-02.htm"
          elif st.getPlayer().getLevel() >= 19 and st.getPlayer().getClassId().getId() == 0x35 and st.getQuestItemsCount(RING_OF_RAVEN_ID) == 1 :
                htmltext = "7524-04.htm"
    elif event == "7519_1" :
        if st.getQuestItemsCount(PIPIS_LETTER_ID):
            st.takeItems(PIPIS_LETTER_ID,1)
            st.set("cond","2")
            n = st.getRandom(3)
            if n == 0:
              htmltext = "7519-02.htm"
              st.giveItems(ZIMENFS_POTION_ID,1)
            elif n == 1:
              htmltext = "7519-03.htm"
              st.giveItems(CHALIS_AXE_ID,1)
            elif n == 2:
              htmltext = "7519-04.htm"
              st.giveItems(BRONKS_INGOT_ID,1)
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
    elif event == "7519_2" :
          htmltext = "7519-06.htm"
    elif event == "7519_3" :
          htmltext = "7519-07.htm"
          st.set("id",str(int(st.get("id"))+1))
    elif event == "7519_4" :
            n = st.getRandom(2)
            if n == 0:
              htmltext = "7519-06.htm"
            if n == 1:
              htmltext = "7519-11.htm"
    elif event == "7519_5" :
        if st.getQuestItemsCount(ZIMENFS_POTION_ID) or st.getQuestItemsCount(CHALIS_AXE_ID) or st.getQuestItemsCount(BRONKS_INGOT_ID):
          if int(st.get("id")) / 10 < 2 :
            htmltext = "7519-07.htm"
            st.set("id",str(int(st.get("id"))+1))
          elif int(st.get("id")) / 10 >= 2 and int(st.get("cond")) == 0 :
              htmltext = "7519-09.htm"
              if int(st.get("id")) / 10 < 3 :
                st.set("id",str(int(st.get("id"))+1))
          elif int(st.get("id")) / 10 >= 3 and int(st.get("cond")) > 0 :
              htmltext = "7519-10.htm"
              st.giveItems(MIONS_LETTER_ID,1)
              st.takeItems(CHALIS_AXE_ID,1)
              st.takeItems(ZIMENFS_POTION_ID,1)
              st.takeItems(BRONKS_INGOT_ID,1)
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
    elif event == "7519_6" :
        if st.getQuestItemsCount(ZIMENFS_PAY_ID) or st.getQuestItemsCount(CHALIS_PAY_ID) or st.getQuestItemsCount(BRONKS_PAY_ID):
            n = st.getRandom(3)
            st.takeItems(ZIMENFS_PAY_ID,1)
            st.takeItems(CHALIS_PAY_ID,1)
            st.takeItems(BRONKS_PAY_ID,1)
            if n == 0:
              htmltext = "7519-02.htm"
              st.giveItems(ZIMENFS_POTION_ID,1)
            elif n == 1:
              htmltext = "7519-03.htm"
              st.giveItems(CHALIS_AXE_ID,1)
            elif n == 2:
              htmltext = "7519-04.htm"
              st.giveItems(BRONKS_INGOT_ID,1)
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
    elif event == "7316_1" :
        if st.getQuestItemsCount(BEAD_PARCEL_ID):
          htmltext = "7316-02.htm"
          st.takeItems(BEAD_PARCEL_ID,1)
          st.giveItems(ROUTS_TP_SCROLL_ID,1)
          st.set("cond","10")
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
    elif event == "7316_2" :
        if st.getQuestItemsCount(BEAD_PARCEL_ID):
          htmltext = "7316-03.htm"
          st.takeItems(BEAD_PARCEL_ID,1)
          st.giveItems(ROUTS_TP_SCROLL_ID,1)
          st.set("cond","10")
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
    elif event == "7557_1" :
          htmltext = "7557-02.htm"
    elif event == "7557_2" :
        if st.getQuestItemsCount(ROUTS_TP_SCROLL_ID):
          htmltext = "7557-03.htm"
          st.takeItems(ROUTS_TP_SCROLL_ID,1)
          st.giveItems(SUCCUBUS_UNDIES_ID,1)
          st.set("cond","11")
          st.getPcSpawn().removeAllSpawn()
        else:
            htmltext = "<html><head><body>I have nothing to say you</body></html>"
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
   if npcId == 7524 and int(st.get("cond"))==0 :
     htmltext = "7524-01.htm"
   elif npcId == 7524 and int(st.get("cond")) and st.getQuestItemsCount(PIPIS_LETTER_ID)>0 :
          htmltext = "7524-06.htm"
   elif npcId == 7524 and int(st.get("cond")) and st.getQuestItemsCount(PIPIS_LETTER_ID)==0 and id==STARTING :
          htmltext = "7524-01.htm"
   elif npcId == 7524 and int(st.get("cond")) and st.getQuestItemsCount(PIPIS_LETTER_ID)==0 :
          htmltext = "7524-07.htm"
   elif npcId == 7519 and int(st.get("cond")) and st.getQuestItemsCount(PIPIS_LETTER_ID)>0 :
          htmltext = "7519-01.htm"
   elif npcId == 7519 and int(st.get("cond")) and ((st.getQuestItemsCount(CHALIS_AXE_ID)+st.getQuestItemsCount(BRONKS_INGOT_ID)+st.getQuestItemsCount(ZIMENFS_POTION_ID))==1) and ((int(st.get("id")) / 10)==0) :
          htmltext = "7519-05.htm"
   elif npcId == 7519 and int(st.get("cond")) and ((st.getQuestItemsCount(CHALIS_AXE_ID)+st.getQuestItemsCount(BRONKS_INGOT_ID)+st.getQuestItemsCount(ZIMENFS_POTION_ID))==1) and ((int(st.get("id")) / 10)>0) :
          htmltext = "7519-08.htm"
   elif npcId == 7519 and int(st.get("cond")) and ((st.getQuestItemsCount(CHALIS_PAY_ID)+st.getQuestItemsCount(BRONKS_PAY_ID)+st.getQuestItemsCount(ZIMENFS_PAY_ID))==1) and (int(st.get("id")) < 50) :
          htmltext = "7519-12.htm"
   elif npcId == 7519 and int(st.get("cond")) and ((st.getQuestItemsCount(CHALIS_PAY_ID)+st.getQuestItemsCount(BRONKS_PAY_ID)+st.getQuestItemsCount(ZIMENFS_PAY_ID))==1) and (int(st.get("id")) >= 50) :
          htmltext = "7519-15.htm"
          st.giveItems(MIONS_LETTER_ID,1)
          st.takeItems(CHALIS_PAY_ID,1)
          st.takeItems(ZIMENFS_PAY_ID,1)
          st.takeItems(BRONKS_PAY_ID,1)
          st.set("cond","4")
   elif npcId == 7519 and int(st.get("cond")) and st.getQuestItemsCount(MIONS_LETTER_ID) :
          htmltext = "7519-13.htm"
   elif npcId == 7519 and int(st.get("cond")) and (st.getQuestItemsCount(BEAR_PIC_ID) or st.getQuestItemsCount(TARANTULA_PIC_ID) or st.getQuestItemsCount(BEAD_PARCEL_ID) or st.getQuestItemsCount(ROUTS_TP_SCROLL_ID) or st.getQuestItemsCount(SUCCUBUS_UNDIES_ID)) :
          htmltext = "7519-14.htm"
   elif npcId == 7517 and int(st.get("cond")) and st.getQuestItemsCount(CHALIS_AXE_ID)==1 and int(st.get("id")) < 20 :
          htmltext = "7517-01.htm"
          st.takeItems(CHALIS_AXE_ID,1)
          st.giveItems(CHALIS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7517 and int(st.get("cond")) and st.getQuestItemsCount(CHALIS_AXE_ID)==1 and int(st.get("id")) >= 20 :
          htmltext = "7517-02.htm"
          st.takeItems(CHALIS_AXE_ID,1)
          st.giveItems(CHALIS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7517 and int(st.get("cond")) and st.getQuestItemsCount(CHALIS_PAY_ID)==1 :
          htmltext = "7517-03.htm"
   elif npcId == 7525 and int(st.get("cond")) and st.getQuestItemsCount(BRONKS_INGOT_ID)==1 and int(st.get("id")) < 20 :
          htmltext = "7525-01.htm"
          st.takeItems(BRONKS_INGOT_ID,1)
          st.giveItems(BRONKS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7525 and int(st.get("cond")) and st.getQuestItemsCount(BRONKS_INGOT_ID)==1 and int(st.get("id")) >= 20 :
          htmltext = "7525-02.htm"
          st.takeItems(BRONKS_INGOT_ID,1)
          st.giveItems(BRONKS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")          
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7525 and int(st.get("cond")) and st.getQuestItemsCount(BRONKS_PAY_ID)==1 :
          htmltext = "7525-03.htm"
   elif npcId == 7538 and int(st.get("cond")) and st.getQuestItemsCount(ZIMENFS_POTION_ID)==1 and int(st.get("id")) < 20 :
          htmltext = "7538-01.htm"
          st.takeItems(ZIMENFS_POTION_ID,1)
          st.giveItems(ZIMENFS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7538 and int(st.get("cond")) and st.getQuestItemsCount(ZIMENFS_POTION_ID)==1 and int(st.get("id")) >= 20 :
          htmltext = "7538-02.htm"
          st.takeItems(ZIMENFS_POTION_ID,1)
          st.giveItems(ZIMENFS_PAY_ID,1)
          if int(st.get("id")) >= 50 :
            st.set("cond","3")
          st.set("id",str(int(st.get("id"))+10))
   elif npcId == 7538 and int(st.get("cond")) and st.getQuestItemsCount(ZIMENFS_PAY_ID)==1 :
          htmltext = "7538-03.htm"
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(MIONS_LETTER_ID)==1 :
          htmltext = "7556-01.htm"
          st.takeItems(MIONS_LETTER_ID,1)
          st.giveItems(BEAR_PIC_ID,1)
          st.set("cond","5")
          st.set("id",str(0))
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(BEAR_PIC_ID)==1 and st.getQuestItemsCount(HONEY_JAR_ID)<5 :
          htmltext = "7556-02.htm"
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(BEAR_PIC_ID)==1 and st.getQuestItemsCount(HONEY_JAR_ID)>=5 :
          htmltext = "7556-03.htm"
          st.takeItems(HONEY_JAR_ID,st.getQuestItemsCount(HONEY_JAR_ID))
          st.takeItems(BEAR_PIC_ID,1)
          st.giveItems(TARANTULA_PIC_ID,1)
          st.set("cond","7")
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(TARANTULA_PIC_ID)==1 and st.getQuestItemsCount(BEAD_ID)<20 :
          htmltext = "7556-04.htm"
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(TARANTULA_PIC_ID)==1 and st.getQuestItemsCount(BEAD_ID)>=20 :
          htmltext = "7556-05.htm"
          st.takeItems(BEAD_ID,st.getQuestItemsCount(BEAD_ID))
          st.takeItems(TARANTULA_PIC_ID,1)
          st.giveItems(BEAD_PARCEL_ID,1)
          st.set("cond","9")
   elif npcId == 7556 and int(st.get("cond")) and st.getQuestItemsCount(BEAD_PARCEL_ID)>0 :
          htmltext = "7556-06.htm"
   elif npcId == 7556 and int(st.get("cond")) and (st.getQuestItemsCount(ROUTS_TP_SCROLL_ID)>0 or st.getQuestItemsCount(SUCCUBUS_UNDIES_ID)>0) :
          htmltext = "7556-07.htm"
   elif npcId == 7316 and int(st.get("cond")) and st.getQuestItemsCount(BEAD_PARCEL_ID)==1 :
          htmltext = "7316-01.htm"
   elif npcId == 7316 and int(st.get("cond")) and st.getQuestItemsCount(ROUTS_TP_SCROLL_ID)==1 :
          htmltext = "7316-04.htm"
   elif npcId == 7316 and int(st.get("cond")) and st.getQuestItemsCount(SUCCUBUS_UNDIES_ID)==1 :
          htmltext = "7316-05.htm"
          st.takeItems(SUCCUBUS_UNDIES_ID,1)
          st.giveItems(RING_OF_RAVEN_ID,1)
          st.set("cond","0")
          st.setState(COMPLETED)
          st.playSound("ItemSound.quest_finish")
   elif npcId == 7557 and int(st.get("cond")) and st.getQuestItemsCount(ROUTS_TP_SCROLL_ID)==1 :
          htmltext = "7557-01.htm"
   return htmltext

 def onKill (self,npc,st):
   npcId = npc.getNpcId()
   if npcId == 777 :
        if int(st.get("cond")) and st.getQuestItemsCount(BEAR_PIC_ID) == 1 and st.getQuestItemsCount(HONEY_JAR_ID) < 5 :
          if int(st.get("id") > 20) :
            n = ((int(st.get("id"))-20)*10)
            if st.getRandom(100) <= n :
              st.getPcSpawn().addSpawn(5058)
              st.set("id","0")
            else:
              st.set("id",str(int(st.get("id"))+1))
          else:
            st.set("id",str(int(st.get("id"))+1))
   elif npcId == 5058 :
        if int(st.get("cond")) and st.getQuestItemsCount(BEAR_PIC_ID) == 1 and st.getQuestItemsCount(HONEY_JAR_ID) < 5 :
          if npc.isSpoil() :
            st.giveItems(HONEY_JAR_ID,1)
            if st.getQuestItemsCount(HONEY_JAR_ID) == 5 :
              st.playSound("ItemSound.quest_middle")
              st.set("cond","6")
            else:
              st.playSound("ItemSound.quest_itemget")
   elif npcId == 403 :
        if int(st.get("cond")) and st.getQuestItemsCount(TARANTULA_PIC_ID) == 1 and st.getQuestItemsCount(BEAD_ID) < 20 :
          if npc.isSpoil() :
            if st.getRandom(2) == 0 :
              st.giveItems(BEAD_ID,1)
              if st.getQuestItemsCount(BEAD_ID) == 20 :
                st.playSound("ItemSound.quest_middle")
                st.set("cond","8")
              else:
                st.playSound("ItemSound.quest_itemget")
   elif npcId == 508 :
        if int(st.get("cond")) and st.getQuestItemsCount(TARANTULA_PIC_ID) == 1 and st.getQuestItemsCount(BEAD_ID) < 20 :
          if npc.isSpoil() :
            if st.getRandom(10) < 6 :
              st.giveItems(BEAD_ID,1)
              if st.getQuestItemsCount(BEAD_ID) == 20 :
                st.playSound("ItemSound.quest_middle")
                st.set("cond","8")
              else:
                st.playSound("ItemSound.quest_itemget")
   return

QUEST       = Quest(417,"417_PathToScavenger","Path To Scavenger")
CREATED     = State('Start', QUEST)
STARTING    = State('Starting', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)


QUEST.setInitialState(CREATED)
QUEST.addStartNpc(7524)
CREATED.addTalkId(7524)

STARTING.addTalkId(7524)

STARTED.addTalkId(7316)
STARTED.addTalkId(7517)
STARTED.addTalkId(7519)
STARTED.addTalkId(7524)
STARTED.addTalkId(7525)
STARTED.addTalkId(7538)
STARTED.addTalkId(7556)
STARTED.addTalkId(7557)

STARTED.addKillId(403)
STARTED.addKillId(5058)
STARTED.addKillId(508)
STARTED.addKillId(777)

STARTED.addQuestDrop(7517,CHALIS_PAY_ID,1)
STARTED.addQuestDrop(7538,ZIMENFS_PAY_ID,1)
STARTED.addQuestDrop(7525,BRONKS_PAY_ID,1)
STARTED.addQuestDrop(7524,PIPIS_LETTER_ID,1)
STARTED.addQuestDrop(7519,CHALIS_AXE_ID,1)
STARTED.addQuestDrop(7519,ZIMENFS_POTION_ID,1)
STARTED.addQuestDrop(7519,BRONKS_INGOT_ID,1)
STARTED.addQuestDrop(7519,MIONS_LETTER_ID,1)
STARTED.addQuestDrop(5058,HONEY_JAR_ID,1)
STARTED.addQuestDrop(7556,BEAR_PIC_ID,1)
STARTED.addQuestDrop(7556,BEAD_PARCEL_ID,1)
STARTED.addQuestDrop(403,BEAD_ID,1)
STARTED.addQuestDrop(508,BEAD_ID,1)
STARTED.addQuestDrop(7556,TARANTULA_PIC_ID,1)
STARTED.addQuestDrop(7557,SUCCUBUS_UNDIES_ID,1)
STARTED.addQuestDrop(7556,BEAD_PARCEL_ID,1)
STARTED.addQuestDrop(7316,ROUTS_TP_SCROLL_ID,1)

print "importing quests: 417: Path To Scavenger"
